package com.drools.service.impl;

import com.alibaba.fastjson.JSON;
import com.drools.biz.RuleInterfaceBiz;
import com.drools.biz.RuleInterfaceJavaBiz;
import com.drools.biz.sys.SysSequenceBiz;
import com.drools.common.DroolsConstants;
import com.drools.common.inters.auditapi.AuditReq;
import com.drools.common.inters.auditapi.AuditRes;
import com.drools.entity.children.DfmtGroup;
import com.drools.entity.children.Note;
import com.drools.entity.clm.*;
import com.drools.model.RuleInterface;
import com.drools.model.RuleInterfaceJava;
import com.drools.model.fact.RuleExecutionObject;
import com.drools.model.fact.RuleExecutionResult;
import com.drools.service.CreateJavaServce;
import com.drools.service.DroolsActionService;
import com.drools.service.InvokeAuditService;
import com.drools.service.ThirdApiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/* *
 * 审核接口
 * @author ly
 * @modifyTime 2020/12/3 14:23:00
 */
@Slf4j
@Service
public class InvokeAuditServiceImpl implements InvokeAuditService {
    @Autowired
    DroolsActionService droolsActionService;
    @Autowired
    ThirdApiService thirdApiService;
    @Autowired
    CreateJavaServce createJavaServce;
    @Autowired
    RuleInterfaceBiz ruleInterfaceBiz;
    @Autowired
    RuleInterfaceJavaBiz ruleInterfaceJavaBiz;
    @Autowired
    SysSequenceBiz sysSequenceBiz;

    /* *
     * 审核接口
     * 1.文档审核
     * 2. 遍历保单和险种
     *    2.1获取事故认定日及保单年度
     *    2.2调用前置通用审核规则
     *    2.3是否非医疗险判断
     *    2.4调用后置通用审核规则
     *    2.5根据险种状态修改保单状态
     *    2.6根据保单状态修改赔案审核状态
     * 3.续赔件
     * 4.返回结果
     * @author ly
     * @modifyTime 2020/12/3 11:28:00
     */
    @Override
    public AuditRes invokeAudit(AuditReq auditReq) {
        AuditRes res = new AuditRes();
        res.setHead(new AuditRes.AuditResHead(DroolsConstants.ResultApi.SUCCESS,
            "执行规则成功",
            auditReq.getHead().getTransactionNo()));

        AuditRes.AuditResBody body = new AuditRes.AuditResBody();
        body.setClmnum("");//理赔业务号
        String visitCode = auditReq.getHead().getVisitCode();

        try {
            log.info("审核接口invokeAudit:解析请求对象");
            Claim claim = createClaim(auditReq);//理赔对象

            // *********************2.执行规则*************************************
            log.info("审核接口invokeAudit:创建请求对象");
            RuleExecutionObject object = new RuleExecutionObject();
            object.addFactObject(claim);

            log.info("审核接口invokeAudit:创建返回结果对象");
            RuleExecutionResult result = new RuleExecutionResult();
            object.setGlobal("_result", result);
            RuleExecutionObject resultData = new RuleExecutionObject();

            if (CollectionUtils.isEmpty(claim.getPolicies())) {
                res.setHead(new AuditRes.AuditResHead(DroolsConstants.ResultApi.ERROR,
                    "未找到保单信息",
                    auditReq.getHead().getTransactionNo()));
                res.setBody(new AuditRes.AuditResBody());
                return res;
            }

            log.info("[{}]审核接口invokeAudit: 文档审核BEGIN",claim.getClmnum());
            resultData = documentAudit(claim);
            log.info("[{}]审核接口invokeAudit: 文档审核END返回：[{}]",claim.getClmnum(), JSON.toJSONString(resultData));

            log.info("[{}]审核接口invokeAudit: 循环保单信息",claim.getClmnum());
            for (Policy policy : claim.getPolicies()) {

                //初始化保单状态
                policy.setExamStatus("");
                policy.setReimbursed(0);
                log.info("[{}]审核接口invokeAudit:循环险种信息",claim.getClmnum());
                if (!CollectionUtils.isEmpty(policy.getPlans())) {
                    for (Plan plan : policy.getPlans()) {

                        log.info("[{}]审核接口invokeAudit:初始化险种审核状态EL与赔付金额0",claim.getClmnum());
                        plan.setExamStatus(DroolsConstants.PlanExamStatus.EL);
                        plan.setClmAmt(0);

                        log.info("[{}]审核接口invokeAudit:初始化险种事故认定日为0",claim.getClmnum());
                        plan.setEventDte(0);

                        log.info("[{}]审核接口invokeAudit:获取事故认定日及保单年度",claim.getClmnum());
                        Claim.getEventDate(claim, policy, plan);

                        log.info("[{}]审核接口invokeAudit: 开始前置通用审核规则",claim.getClmnum());
                        resultData = preAudit(claim, policy, plan);

                        log.info("[{}]审核接口invokeAudit: 是否非医疗险",claim.getClmnum());
                        if ("H1".equals(plan.getStatSect())) {
                            //H1->后置规则
                            log.info("[{}]审核接口invokeAudit: StatSect=H1-->后置规则",claim.getClmnum());
                            resultData = afterAudit(claim, policy, plan);
                        } else {
                            //非H1->非医疗险审核判断
                            log.info("[{}]审核接口invokeAudit: 非医疗险审核判断",claim.getClmnum());
                            Claim.getBenefitFormula(claim, policy, plan);
                            //后置规则
                            log.info("[{}]审核接口invokeAudit: 非医疗险审核判断-->后置规则",claim.getClmnum());
                            resultData = afterAudit(claim, policy, plan);
                        }

                        log.info("[{}]审核接口invokeAudit: 根据险种状态修改保单状态",claim.getClmnum());
                        setExamStatusByPlan(policy, plan);
                    }
                }

                log.info("[{}]审核接口invokeAudit:根据保单状态修改赔案审核状态",claim.getClmnum());
                setExamStatusByPolicy(claim, policy);
            }

            log.info("[{}]审核接口invokeAudit:续赔件规则",claim.getClmnum());
            resultData = auditHistory(claim);

            log.info("[{}]审核接口invokeAudit:封装返回结果",claim.getClmnum());
            createResBody(claim,body);

        } catch (Exception e) {
            log.error("执行建档规则异常：{}", e);
            res.setHead(new AuditRes.AuditResHead(DroolsConstants.ResultApi.ERROR,
                "执行规则异常:" + e.getMessage(),
                auditReq.getHead().getTransactionNo()));
            res.setBody(new AuditRes.AuditResBody());
            return res;
        }

        res.setBody(body);
        return res;
    }



    /* *
     * 续赔件
     * 04-00-join-benefit-judge-benefit.xls
     * 1.判断历史赔案续赔件 04-00-00-join-benefit-judge-claim
     *    claim、policy、plan、benefit、historyClaim、historyPlan、historyBenefit
     *
     * 2.手术续赔条件判断 04-00-03-join-benefit-judge-doctor-current-surgery
     *    claim、policy、plan、benefit、historyClaim、historyPlan、historyBenefit、doctor、surgery
     *
     * 3.历史就医续赔件 04-00-01-join-benefit-judge-doctor
     *   claim、policy、plan、benefit、historyClaim、historyPlan、historyBenefit、doctor、surgery、historyDoctor
     *
     * 4.历史就医手术续赔件 04-00-02-join-benefit-judge-doctor-surgery
     *   claim、policy、plan、benefit、historyClaim、historyPlan、historyBenefit、doctor、surgery、historyDoctor、historySurgery
     * @author ly
     * @modifyTime 2020/12/3 13:09:00
     */
    public RuleExecutionObject auditHistory(Claim claim) {
        RuleExecutionObject resultData = new RuleExecutionObject();

        for (Policy policy : claim.getPolicies()) {

            //判断保单状态 不是EL EX CO
            if (StringUtils.isBlank(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.NA.equals(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.PE.equals(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.DC.equals(policy.getExamStatus())
            ) {
                log.info("审核接口invokeAudit: 保单[{}]保单状态[{}]退出续赔件判断",policy.getChdrNum(),policy.getExamStatus());
                continue;
            }

            //判断保单有无历史赔付
            if (policy.getHistoryClaimLength() == 0) {
                log.info("审核接口invokeAudit: 保单[{}]无历史赔付退出续赔件判断",policy.getChdrNum());
                continue;
            }
            if (CollectionUtils.isEmpty(policy.getPlans())) {
                log.info("审核接口invokeAudit: 保单[{}]无险种信息退出续赔件判断",policy.getChdrNum());
                continue;
            }

            log.info("审核接口invokeAudit: 保单[{}] 开始循环险种",policy.getChdrNum());
            for (Plan plan : policy.getPlans()) {

                if (CollectionUtils.isEmpty(plan.getBenefits())) {
                    continue;
                }
                // 循环责任
                for (Benefit benefit : plan.getBenefits()) {

                    if (CollectionUtils.isEmpty(policy.getHistoryClaims())) {
                        continue;
                    }
                    //循环历史赔案
                    for (HistoryClaim historyClaim : policy.getHistoryClaims()) {

                        if (CollectionUtils.isEmpty(historyClaim.getPlans())) {
                            continue;
                        }
                        // 循环历史险种
                        for (HistoryPlan historyPlan : historyClaim.getPlans()) {

                            //判断险种是否一致
                            log.info("审核接口invokeAudit: 保单[{}] 判断险种是否一致",policy.getChdrNum());
                            if (historyPlan.getLife().equals(plan.getLife())
                                && historyPlan.getCoverage().equals(plan.getCoverage())
                                && historyPlan.getRider().equals(plan.getRider())
                                && historyPlan.getCrtable().equals(plan.getCrtable())) {


                                if (historyPlan.getBenefitLength() > 0) {

                                    //循环历史责任
                                    for (HistoryBenefit historyBenefit : historyPlan.getBenefits()) {

                                        log.info("审核接口invokeAudit: 保单[{}] 判断是否相同责任",policy.getChdrNum());
                                        if (benefit.getBenCode().equals(historyBenefit.getBenCode())) {
                                            RuleExecutionObject object = new RuleExecutionObject();
                                            RuleExecutionResult result = new RuleExecutionResult();
                                            object.setGlobal("_result", result);

                                            object.addFactObject(claim);
                                            object.addFactObject(benefit);
                                            object.addFactObject(historyBenefit);
                                            object.addFactObject(plan);
                                            object.addFactObject(historyPlan);
                                            object.addFactObject(policy);
                                            object.addFactObject(historyClaim);
                                            
                                            log.info("审核接口invokeAudit: 保单[{}] 判断历史赔案续赔件",policy.getChdrNum());
                                            resultData = thirdApiService.excute(object, DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_MEDICAL_09);

                                            //循环就医 1.手术续赔条件判断,2历史就医续赔件3历史就医手术续赔件
                                            resultData = auditDoctor(claim,historyClaim,object);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        return resultData;
    }

    /* *
     * 手术续赔条件判断
     * @author ly
     * @modifyTime 2020/12/3 13:54:00
     */
    public RuleExecutionObject auditDoctor(Claim claim,HistoryClaim historyClaim,RuleExecutionObject object){
        RuleExecutionObject resultData = new RuleExecutionObject();
        for (Doctor doctor : claim.getDoctors()) {
            if (doctor.getSurgeryLength() > 0) {

                for (Surgery surgery : doctor.getSurgeries()) {
                    object.addFactObject(doctor);
                    object.addFactObject(surgery);

                    if(historyClaim.getDoctorLength()>0){
                        resultData = auditHistoryDoctor(historyClaim,object);
                    }

                }
            }
        }
        return resultData;
    }
    /* *
     * 历史就医续赔件
     * @author ly
     * @modifyTime 2020/12/3 13:54:00
     */
    public RuleExecutionObject auditHistoryDoctor(HistoryClaim historyClaim,RuleExecutionObject object){
        RuleExecutionObject resultData = new RuleExecutionObject();
        for (HistoryDoctor historyDoctor : historyClaim.getDoctors()) {
            object.addFactObject(historyDoctor);

            //手术续赔条件判断
            log.info("审核接口invokeAudit: 手术续赔条件判断");
            resultData = thirdApiService.excute(object, DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_MEDICAL_10);


            log.info("审核接口invokeAudit: 历史就医续赔件");
            resultData = thirdApiService.excute(object, DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_MEDICAL_11);

            //历史就医手术续赔件
            if(historyDoctor.getSurgeryLength()>0){
                resultData = auditHistorySurgery(historyDoctor,object);
            }
        }
        return resultData;
    }

    /* *
     * 历史就医手术续赔件
     * @author ly
     * @modifyTime 2020/12/3 13:54:00
     */
    public RuleExecutionObject auditHistorySurgery(HistoryDoctor historyDoctor,RuleExecutionObject object){
        RuleExecutionObject resultData = new RuleExecutionObject();
        for (HistorySurgery historySurgery : historyDoctor.getSurgeries()) {
            object.addFactObject(historySurgery);

            log.info("审核接口invokeAudit: 历史就医手术续赔件");
            resultData = thirdApiService.excute(object, DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_MEDICAL_12);
        }
        return resultData;
    }

    /* *
     * 根据保单状态修改赔案审核状态 02-00-04-common-audit-policy-set-claim-examstatus
     * @author ly
     * @modifyTime 2020/12/3 11:11:00
     */
    public void setExamStatusByPolicy(Claim claim, Policy policy) {
        log.info("保单审核状态[{}],赔案审核状态[{}]", policy.getExamStatus(), claim.getExamStatus());
        // pe
        if (DroolsConstants.PolicyExamStatus.PE.equals(policy.getExamStatus())) {
            claim.setExamStatus(DroolsConstants.PolicyExamStatus.PE);
            log.info("修改赔案状态为PE");
        } else if (DroolsConstants.PolicyExamStatus.EL.equals(policy.getExamStatus())) {
            if (StringUtils.isBlank(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.EL.equals(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.NA.equals(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.DC.equals(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.EX.equals(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.CO.equals(policy.getExamStatus())) {
                claim.setExamStatus(DroolsConstants.PolicyExamStatus.EL);
                log.info("修改赔案状态为EL");
            }
        } else if (DroolsConstants.PolicyExamStatus.DC.equals(policy.getExamStatus())) {
            if (StringUtils.isBlank(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.NA.equals(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.DC.equals(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.EX.equals(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.CO.equals(policy.getExamStatus())) {
                claim.setExamStatus(DroolsConstants.PolicyExamStatus.DC);
                log.info("修改赔案状态为DC");
            }
        } else if (DroolsConstants.PolicyExamStatus.NA.equals(policy.getExamStatus())) {
            if (StringUtils.isBlank(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.NA.equals(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.EX.equals(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.CO.equals(policy.getExamStatus())) {
                claim.setExamStatus(DroolsConstants.PolicyExamStatus.NA);
                log.info("修改赔案状态为NA");
            }
        }
    }

    /* *
     * 根据险种状态修改保单审核状态 02-01-05-common-audit-plan-set-policy-examstatus
     * @author ly
     * @modifyTime 2020/12/3 10:06:00
     */
    public void setExamStatusByPlan(Policy policy, Plan plan) {
        log.info("保单审核状态[{}],险种审核状态[{}]", policy.getExamStatus(), plan.getExamStatus());
        // pe
        if (DroolsConstants.PlanExamStatus.PE.equals(plan.getExamStatus())) {
            policy.setExamStatus(DroolsConstants.PolicyExamStatus.PE);
            log.info("修改保单状态为PE");
        } else if (DroolsConstants.PlanExamStatus.EL.equals(plan.getExamStatus())) {
            if (StringUtils.isBlank(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.EL.equals(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.NA.equals(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.DC.equals(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.EX.equals(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.CO.equals(policy.getExamStatus())) {
                policy.setExamStatus(DroolsConstants.PolicyExamStatus.EL);
                log.info("修改保单状态为EL");
            }
        } else if (DroolsConstants.PlanExamStatus.DC.equals(plan.getExamStatus())) {
            if (StringUtils.isBlank(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.NA.equals(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.DC.equals(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.EX.equals(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.CO.equals(policy.getExamStatus())) {
                policy.setExamStatus(DroolsConstants.PolicyExamStatus.DC);
                log.info("修改保单状态为DC");
            }
        } else if (DroolsConstants.PlanExamStatus.NA.equals(plan.getExamStatus())) {
            if (StringUtils.isBlank(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.NA.equals(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.EX.equals(policy.getExamStatus())
                || DroolsConstants.PolicyExamStatus.CO.equals(policy.getExamStatus())) {
                policy.setExamStatus(DroolsConstants.PolicyExamStatus.NA);
                log.info("修改保单状态为NA");
            }
        }
    }

    /* *
     * 文档审核
     * @author ly
     * @modifyTime 2020/12/3 9:25:00
     */
    public RuleExecutionObject documentAudit(Claim claim) {
        RuleExecutionResult result = new RuleExecutionResult();
        RuleExecutionObject resultData = new RuleExecutionObject();

        log.info("审核接口invokeAudit: 文档审核-循环保单信息");
        for (Policy policy : claim.getPolicies()) {

            log.info("审核接口invokeAudit:文档审核-循环险种信息");
            if (!CollectionUtils.isEmpty(policy.getPlans())) {
                for (Plan plan : policy.getPlans()) {
                    //循环文件审核规则
                    log.info("审核接口invokeAudit:文档审核-循环文件信息");
                    if (!CollectionUtils.isEmpty(claim.getDocuments())) {
                        for (Document document : claim.getDocuments()) {
                            RuleExecutionObject object = new RuleExecutionObject();
                            object.addFactObject(claim);
                            object.addFactObject(policy);
                            object.addFactObject(plan);
                            object.addFactObject(document);
                            object.setGlobal("_result", result);

                            //1.赔案文件审核规则
                            resultData = thirdApiService.excute(object, DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_CONFIRM_05);
                            //2.险种文件审核规则
                            resultData = thirdApiService.excute(object, DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_CONFIRM_06);
                        }
                    }
                }
            }
        }
        return resultData;
    }

    /* *
     * 通用前置规则
     * 02-01-common-audit-plan.xls
     * 02-03-common-audit-judge-medical-plan.xls
     * @author ly
     * @modifyTime 2020/12/3 10:26:00
     */
    public RuleExecutionObject preAudit(Claim claim, Policy policy, Plan plan) {
        RuleExecutionResult result = new RuleExecutionResult();
        RuleExecutionObject object = new RuleExecutionObject();
        object.addFactObject(claim);
        object.addFactObject(policy);
        object.addFactObject(plan);
        object.setGlobal("_result", result);

        //jobNo= 99999999
        log.info("通用审核规则(前置)：1.被保人身份判断规则调用");
        RuleExecutionObject resultData = thirdApiService.excute(object, DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_AUDIT_01);
        if (!DroolsConstants.PlanExamStatus.EL.equals(plan.getExamStatus())) {
            log.info("通用审核规则(前置)：1.被保人身份判断规则调用后[{}]", plan.getExamStatus());
            return resultData;
        }

        //险种大类statSect=L1、L2、L3、U1、U2、P1、P2 赔案理赔形态claimType= :P、C、N、S
        log.info("通用审核规则(前置)：2.险种大类规则调用");
        resultData = thirdApiService.excute(object, DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_AUDIT_02);
        if (!DroolsConstants.PlanExamStatus.EL.equals(plan.getExamStatus())) {
            log.info("通用审核规则(前置)：2.险种大类规则调用后[{}]", plan.getExamStatus());
            return resultData;
        }

        //
        log.info("通用审核规则(前置)：3.保障类型规则调用");
        resultData = thirdApiService.excute(object, DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_AUDIT_03);
        if (!DroolsConstants.PlanExamStatus.EL.equals(plan.getExamStatus())) {
            log.info("通用审核规则(前置)：3.保障类型规则调用后[{}]", plan.getExamStatus());
            return resultData;
        }
        log.info("通用审核规则(前置)：4.险种类型规则调用");
        resultData = thirdApiService.excute(object, DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_AUDIT_04);
        if (!DroolsConstants.PlanExamStatus.EL.equals(plan.getExamStatus())) {
            log.info("通用审核规则(前置)：4.险种类型规则调用后[{}]", plan.getExamStatus());
            return resultData;
        }
        log.info("通用审核规则(前置)：5.安心倚医疗险规则调用");
        resultData = thirdApiService.excute(object, DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_AUDIT_05);
        if (!DroolsConstants.PlanExamStatus.EL.equals(plan.getExamStatus())) {
            log.info("通用审核规则(前置)：5.安心倚医疗险规则调用后[{}]", plan.getExamStatus());
            return resultData;
        }

        return resultData;
    }

    /* *
     * 通用后置规则
     * 03-00-non-medical-audit-judge-plan.xls
     * 01-03-admin-audit-judge-exclude.xls
     * @author ly
     * @modifyTime 2020/12/3 10:48:00
     */
    public RuleExecutionObject afterAudit(Claim claim, Policy policy, Plan plan) {
        RuleExecutionResult result = new RuleExecutionResult();
        RuleExecutionObject object = new RuleExecutionObject();
        object.addFactObject(claim);
        object.addFactObject(policy);
        object.addFactObject(plan);
        object.setGlobal("_result", result);

        log.info("通用审核规则(后置)：1.通用审核(后置)-非有效状态的险种审核拒赔 规则调用");
        RuleExecutionObject resultData = thirdApiService.excute(object, DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_MEDICAL_01);
        if (!DroolsConstants.PlanExamStatus.EL.equals(plan.getExamStatus())) {
            log.info("通用审核规则(后置)：1.通用审核(后置)-非有效状态的险种审核拒赔 规则调用后[{}]", plan.getExamStatus());
            return resultData;
        }

        log.info("通用审核规则(后置)：2.通用审核(后置)-意外事故地 规则调用");
        resultData = thirdApiService.excute(object, DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_MEDICAL_02);
        if (!DroolsConstants.PlanExamStatus.EL.equals(plan.getExamStatus())) {
            log.info("通用审核规则(后置)：2.通用审核(后置)-意外事故地 规则调用后[{}]", plan.getExamStatus());
            return resultData;
        }

        log.info("通用审核规则(后置)：3.通用审核(后置)-重疾住院津贴 规则调用");
        resultData = thirdApiService.excute(object, DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_MEDICAL_03);
        if (!DroolsConstants.PlanExamStatus.EL.equals(plan.getExamStatus())) {
            log.info("通用审核规则(后置)：3.通用审核(后置)-重疾住院津贴 规则调用后[{}]", plan.getExamStatus());
            return resultData;
        }

        log.info("通用审核规则(后置)：4.通用审核(后置)-险种有除外责任 规则调用");
        resultData = thirdApiService.excute(object, DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_MEDICAL_04);
        if (!DroolsConstants.PlanExamStatus.EL.equals(plan.getExamStatus())) {
            log.info("通用审核规则(后置)：4.通用审核(后置)-险种有除外责任 规则调用后[{}]", plan.getExamStatus());
            return resultData;
        }

        log.info("通用审核规则(后置)：5.通用审核(后置)-险种有除外事故代码 规则调用");
        resultData = thirdApiService.excute(object, DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_MEDICAL_05);
        if (!DroolsConstants.PlanExamStatus.EL.equals(plan.getExamStatus())) {
            log.info("通用审核规则(后置)：5.通用审核(后置)-险种有除外事故代码 规则调用后[{}]", plan.getExamStatus());
            return resultData;
        }

        log.info("通用审核规则(后置)：6.通用审核(后置)-险种有除外意外事故地代码 规则调用");
        resultData = thirdApiService.excute(object, DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_MEDICAL_06);
        if (!DroolsConstants.PlanExamStatus.EL.equals(plan.getExamStatus())) {
            log.info("通用审核规则(后置)：6.通用审核(后置)-险种有除外意外事故地代码 规则调用后[{}]", plan.getExamStatus());
            return resultData;
        }

        log.info("通用审核规则(后置)：7.通用审核(后置)-险种有除外年龄 规则调用");
        resultData = thirdApiService.excute(object, DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_MEDICAL_07);
        if (!DroolsConstants.PlanExamStatus.EL.equals(plan.getExamStatus())) {
            log.info("通用审核规则(后置)：7.通用审核(后置)-险种有除外年龄 规则调用后[{}]", plan.getExamStatus());
            return resultData;
        }


        for (Doctor doctor : claim.getDoctors()) {
            object.addFactObject(doctor);
            log.info("通用审核规则(后置)：8.通用审核(后置)-险种有除外伤病代码 规则调用");
            resultData = thirdApiService.excute(object, DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_MEDICAL_08);

        }

        return resultData;
    }


    private Claim createClaim(AuditReq auditReq) {
        AuditReq.ConfirmBaseInfo baseInfo = auditReq.getBody().getBaseInfo();
        Claim claim = new Claim();
        BeanUtils.copyProperties(baseInfo, claim);

        List<Policy> policyList = createPolicys(auditReq);//保单对象
        List<Document> documentList = createDocument(auditReq);//文档对象
        List<Doctor> doctorList = createDoctor(auditReq);//就医

        claim.setDocuments(documentList);
        claim.setDocumentLength(documentList.size());
        claim.setPolicies(policyList);
        claim.setPolicyLength(policyList.size());
        claim.setDoctors(doctorList);
        claim.setDoctorLength(doctorList.size());

        return claim;
    }


    //保单对象
    public List<Policy> createPolicys(AuditReq auditReq) {
        List<Policy> policyList = new ArrayList<>();

        List<AuditReq.PolicyInfo> policyInfos = auditReq.getBody().getPolicyInfoList();
        if (!CollectionUtils.isEmpty(policyInfos)) {
            for (AuditReq.PolicyInfo policyInfo : policyInfos) {
                Policy policy = new Policy();
                BeanUtils.copyProperties(policyInfo, policy);

                //险种信息
                List<Plan> planList = createPlan(policyInfo);
                policy.setPlans(planList);
                policy.setPlanLength(planList.size());

//                //保全信息
//                List<Endorse> endorseList = createEndorse(policyInfo);
//                policy.setEndorses(endorseList);
//                policy.setEndorseLength(endorseList.size());
//
//                //保单批注信息
//                List<PolicyNote> policyNoteList = createPolicyNote(policyInfo);
//                policy.setNotepfs(policyNoteList);
//                policy.setNotepfLength(policyNoteList.size());

                //理赔历史赔案信息
                List<HistoryClaim> historyClaimList = createHistoryCliam(policyInfo);
                policy.setHistoryClaims(historyClaimList);
                policy.setHistoryClaimLength(historyClaimList.size());

                policyList.add(policy);
            }
        }

        return policyList;
    }

    /* *
     * 保单批注信息
     * @author ly
     * @modifyTime 2020/12/2 17:12:00
     */
    private List<PolicyNote> createPolicyNote(AuditReq.PolicyInfo policyInfo) {
        List<PolicyNote> policyNoteList = new ArrayList<>();
//        List<AuditReq.Note> ptrns = policyInfo.getNoteList();
//        if(!CollectionUtils.isEmpty(ptrns)){
//            for (AuditReq.Note note : ptrns) {
//                PolicyNote policyNote = new PolicyNote();
//                BeanUtils.copyProperties(note,policyNote);
//                policyNoteList.add(policyNote);
//            }
//        }
        return policyNoteList;
    }

    /* *
     * 保全信息
     * @author ly
     * @modifyTime 2020/12/2 17:11:00
     */
    private List<Endorse> createEndorse(AuditReq.PolicyInfo policyInfo) {
        List<Endorse> endorseList = new ArrayList<>();
//        List<AuditReq.Ptrn> ptrns = policyInfo.getPtrnList();
//        if(!CollectionUtils.isEmpty(ptrns)){
//            for (AuditReq.Ptrn ptrn : ptrns) {
//                Endorse endorse = new Endorse();
//                BeanUtils.copyProperties(ptrn,endorse);
//                endorseList.add(endorse);
//            }
//        }
        return endorseList;
    }

    /* *
     * 赔案险种信息
     *   --除外责任信息
     *   --除外伤病代码信息
     *   --除外手术代码信息
     *   --除外意外事故代码信息
     *   --除外意外事故地信息
     *   --责任信息
     * @author ly
     * @modifyTime 2020/12/2 14:46:00
     */
    public List<Plan> createPlan(AuditReq.PolicyInfo policyInfo) {
        List<Plan> planList = new ArrayList<>();
        List<AuditReq.Plan> plans = policyInfo.getPlanList();
        if (!CollectionUtils.isEmpty(plans)) {
            for (AuditReq.Plan plan : plans) {
                Plan plan1 = new Plan();
                BeanUtils.copyProperties(plan, plan1);

                //除外责任信息
                if (!CollectionUtils.isEmpty(plan.getExcBencodeList())) {
                    List<String> excludeBenefits = plan.getExcBencodeList().stream().map(p -> p.getExcBencode()).collect(Collectors.toList());
                    plan1.setExcludeBenefits(excludeBenefits);
                }

                //除外伤病代码信息
                if (!CollectionUtils.isEmpty(plan.getExcIllcodeList())) {
                    List<String> excIllcodeList = plan.getExcIllcodeList().stream().map(p -> p.getIllcode()).collect(Collectors.toList());
                    plan1.setExcludeIllCodes(excIllcodeList);
                }

                //除外手术代码信息
                if (!CollectionUtils.isEmpty(plan.getExcIllcodeList())) {
                    List<String> excludeSurgeryCodes = plan.getExcSurgeryList().stream().map(p -> p.getSurgeryCode()).collect(Collectors.toList());
                    plan1.setExcludeSurgeryCodes(excludeSurgeryCodes);
                }

                //除外意外事故代码信息
                if (!CollectionUtils.isEmpty(plan.getExcIllcodeList())) {
                    List<String> excludeAcdtCodes = plan.getExcAcdtList().stream().map(p -> p.getAcdtCode()).collect(Collectors.toList());
                    plan1.setExcludeAcdtCodes(excludeAcdtCodes);
                }

                //除外意外事故地信息
                if (!CollectionUtils.isEmpty(plan.getExcIllcodeList())) {
                    List<String> excludeEventPlaces = plan.getExcEventPlaceList().stream().map(p -> p.getEventPlace()).collect(Collectors.toList());
                    plan1.setExcludeEventPlaces(excludeEventPlaces);
                }

                //责任信息
                List<Benefit> benefitList = createBenefit(plan);
                plan1.setBenefits(benefitList);
                plan1.setBenefitLength(benefitList.size());

                planList.add(plan1);
            }
        }
        return planList;
    }

    /* *
     *  新伤残分组信息
     * @author ly
     * @modifyTime 2020/12/2 14:46:00
     */
    public List<DfmtGroup> createDfmtGroup(AuditReq.Benefit benefit) {
        List<DfmtGroup> dfmtGroupList = new ArrayList<>();
        List<AuditReq.DfmtGroup> dfmtGroups = benefit.getDfmtGroupList();
        if (!CollectionUtils.isEmpty(dfmtGroups)) {
            for (AuditReq.DfmtGroup group : dfmtGroups) {
                DfmtGroup dfmtGroup = new DfmtGroup();
                BeanUtils.copyProperties(group, dfmtGroup);

                if (!CollectionUtils.isEmpty(group.getFormulaIllcodeList())) {
                    List<String> benefitIllCodeList = group.getFormulaIllcodeList().stream().map(p -> p.getIllcode()).collect(Collectors.toList());
                    dfmtGroup.setFormulaIllcodes(benefitIllCodeList.toArray(new String[benefitIllCodeList.size()]));
                }

                dfmtGroupList.add(dfmtGroup);
            }
        }
        return dfmtGroupList;
    }

    /* *
     * 责任信息
     *  --新伤残分组信息
     *  --责任伤病代码分组
     * @author ly
     * @modifyTime 2020/12/2 14:46:00
     */
    public List<Benefit> createBenefit(AuditReq.Plan plan) {
        List<Benefit> benefitList = new ArrayList<>();
        List<AuditReq.Benefit> benefits = plan.getBenefitList();
        if (!CollectionUtils.isEmpty(benefits)) {
            for (AuditReq.Benefit benefit : benefits) {
                Benefit benefit1 = new Benefit();
                BeanUtils.copyProperties(benefit, benefit1);
                //新伤残分组信息
                List<DfmtGroup> dfmtGroupList = createDfmtGroup(benefit);
                benefit1.setDeformityGroup(dfmtGroupList);
                benefit1.setGroupLength(dfmtGroupList.size());

                //责任伤病代码分组
                if (!CollectionUtils.isEmpty(benefit.getBenefitIllCodeList())) {
                    List<String> benefitIllCodeList = benefit.getBenefitIllCodeList().stream().map(p -> p.getIllcode()).collect(Collectors.toList());
                    benefit1.setIllCodeArray(benefitIllCodeList.toArray(new String[benefitIllCodeList.size()]));
                }

                benefitList.add(benefit1);
            }
        }
        return benefitList;
    }

    /* *
     * 赔案就医信息
     *   --手术信息
     *   --伤病代码
     *   --收据信息
     * @author ly
     * @modifyTime 2020/12/2 14:46:00
     */
    public List<Doctor> createDoctor(AuditReq auditReq) {
        List<Doctor> doctorList = new ArrayList<>();
        List<AuditReq.Doctor> doctors = auditReq.getBody().getDoctorList();
        if (!CollectionUtils.isEmpty(doctors)) {
            for (AuditReq.Doctor doctor : doctors) {
                Doctor doctor1 = new Doctor();
                BeanUtils.copyProperties(doctor, doctor1);

                //手术信息
                List<Surgery> surgeryList = createSurgery(doctor);
                doctor1.setSurgeries(surgeryList);
                doctor1.setSurgeryLength(surgeryList.size());

                //伤病代码
                List<String> illCodes = createIllCode(doctor);
                doctor1.setIllCodes(illCodes);

                //收据信息
                List<Receipt> receiptList = createReceipt(doctor);
                doctor1.setReceipts(receiptList);
                doctor1.setReceiptLength(receiptList.size());

                doctorList.add(doctor1);
            }
        }
        return doctorList;
    }

    /* *
     * 收据信息
     * @author ly
     * @modifyTime 2020/12/2 14:46:00
     */
    public List<Receipt> createReceipt(AuditReq.Doctor doctor) {
        List<Receipt> receiptList = new ArrayList<>();
        List<AuditReq.Receipt> receipts = doctor.getReceiptList();
        if (!CollectionUtils.isEmpty(receipts)) {
            for (AuditReq.Receipt receipt : receipts) {
                Receipt receipt1 = new Receipt();
                BeanUtils.copyProperties(receipt, receipt1);
                receiptList.add(receipt1);
            }
        }
        return receiptList;
    }

    /* *
     * 赔案手术信息
     * @author ly
     * @modifyTime 2020/12/2 14:46:00
     */
    public List<Surgery> createSurgery(AuditReq.Doctor doctor) {
        List<Surgery> surgeryList = new ArrayList<>();
        List<AuditReq.Surgery> surgeries = doctor.getSurgeryList();
        if (!CollectionUtils.isEmpty(surgeries)) {
            for (AuditReq.Surgery surgery : surgeries) {
                Surgery surgery1 = new Surgery();
                BeanUtils.copyProperties(surgery, surgery1);
                surgeryList.add(surgery1);
            }
        }
        return surgeryList;
    }

    /* *
     * 赔案伤病代码信息
     * @author ly
     * @modifyTime 2020/12/2 14:46:00
     */
    public List<String> createIllCode(AuditReq.Doctor doctor) {
        List<String> illCodeList = new ArrayList<>();
        List<AuditReq.IllCode> illcodes = doctor.getIllcodeList();
        if (!CollectionUtils.isEmpty(illcodes)) {
            for (AuditReq.IllCode illcode : illcodes) {
                illCodeList.add(illcode.getIllCode());
            }
        }
        return illCodeList;
    }

    /* *
     * 理赔历史赔案信息
     *  --历史险种信息
     *    --历史赔案责任信息
     *  --就医信息
     *    --手术信息
     *    --伤病代码
     * @author ly
     * @modifyTime 2020/12/2 14:46:00
     */
    public List<HistoryClaim> createHistoryCliam(AuditReq.PolicyInfo policyInfo) {
        List<HistoryClaim> historyClaimList = new ArrayList<>();
        List<AuditReq.HistoryClm> clms = policyInfo.getHistoryClmList();
        if (!CollectionUtils.isEmpty(clms)) {
            for (AuditReq.HistoryClm clm : clms) {
                HistoryClaim claim = new HistoryClaim();
                BeanUtils.copyProperties(clm, claim);

                //历史险种信息
                List<HistoryPlan> planList = createHistoryPlan(clm);
                claim.setPlans(planList);
                claim.setPlanLength(planList.size());

                //就医信息
                List<HistoryDoctor> doctorList = createHistoryDoctor(clm);
                claim.setDoctors(doctorList);
                claim.setDoctorLength(doctorList.size());

                historyClaimList.add(claim);
            }
        }
        return historyClaimList;
    }

    /* *
     * 历史赔案险种信息
     *   --历史赔案责任信息
     * @author ly
     * @modifyTime 2020/12/2 14:46:00
     */
    public List<HistoryPlan> createHistoryPlan(AuditReq.HistoryClm historyClm) {
        List<HistoryPlan> historyPlans = new ArrayList<>();
        List<AuditReq.HistoryPlan> plans = historyClm.getPlanList();
        if (!CollectionUtils.isEmpty(plans)) {
            for (AuditReq.HistoryPlan plan : plans) {
                HistoryPlan historyPlan = new HistoryPlan();
                BeanUtils.copyProperties(plan, historyPlan);

                //历史赔案责任信息
                List<HistoryBenefit> benefitList = createHistoryPlan(plan);
                historyPlan.setBenefits(benefitList);
                historyPlan.setBenefitLength(benefitList.size());

                historyPlans.add(historyPlan);
            }
        }
        return historyPlans;
    }

    /* *
     * 历史赔案责任信息
     * @author ly
     * @modifyTime 2020/12/2 14:46:00
     */
    public List<HistoryBenefit> createHistoryPlan(AuditReq.HistoryPlan historyPlan) {
        List<HistoryBenefit> benifitList = new ArrayList<>();
        List<AuditReq.HistoryBenefit> benifits = historyPlan.getHistoryBenifitList();
        if (!CollectionUtils.isEmpty(benifits)) {
            for (AuditReq.HistoryBenefit benefit : benifits) {
                HistoryBenefit historyBenefit = new HistoryBenefit();
                BeanUtils.copyProperties(benefit, historyBenefit);
                benifitList.add(historyBenefit);
            }
        }
        return benifitList;
    }

    /* *
     * 历史赔案就医信息
     *   --手术信息
     *   --伤病代码
     * @author ly
     * @modifyTime 2020/12/2 14:46:00
     */
    public List<HistoryDoctor> createHistoryDoctor(AuditReq.HistoryClm historyClm) {
        List<HistoryDoctor> historyDoctors = new ArrayList<>();
        List<AuditReq.HistoryDoctor> doctors = historyClm.getHistoryDoctorList();
        if (!CollectionUtils.isEmpty(doctors)) {
            for (AuditReq.HistoryDoctor doctor : doctors) {
                HistoryDoctor historyDoctor = new HistoryDoctor();
                BeanUtils.copyProperties(doctor, historyDoctor);

                //手术信息
                List<HistorySurgery> surgeryList = createHistorySurgery(doctor);
                historyDoctor.setSurgeries(surgeryList);
                historyDoctor.setSurgeryLength(surgeryList.size());

                //伤病代码
                List<String> illCodes = createHistoryIllCode(doctor);
                historyDoctor.setIllCodes(illCodes);

                historyDoctors.add(historyDoctor);
            }
        }
        return historyDoctors;
    }

    /* *
     * 历史赔案手术信息
     * @author ly
     * @modifyTime 2020/12/2 14:46:00
     */
    public List<HistorySurgery> createHistorySurgery(AuditReq.HistoryDoctor historyDoctor) {
        List<HistorySurgery> historySurgeries = new ArrayList<>();
        List<AuditReq.HistorySurgery> surgeries = historyDoctor.getHistorySurgeryList();
        if (!CollectionUtils.isEmpty(surgeries)) {
            for (AuditReq.HistorySurgery surgery : surgeries) {
                HistorySurgery historySurgery = new HistorySurgery();
                BeanUtils.copyProperties(surgery, historySurgery);
                historySurgeries.add(historySurgery);
            }
        }
        return historySurgeries;
    }

    /* *
     * 历史赔案伤病代码信息
     * @author ly
     * @modifyTime 2020/12/2 14:46:00
     */
    public List<String> createHistoryIllCode(AuditReq.HistoryDoctor historyDoctor) {
        List<String> illCodeList = new ArrayList<>();
        List<AuditReq.HistoryIllcode> illcodes = historyDoctor.getHistoryIllcodeList();
        if (!CollectionUtils.isEmpty(illcodes)) {
            for (AuditReq.HistoryIllcode illcode : illcodes) {
                illCodeList.add(illcode.getIllCode());
            }
        }
        return illCodeList;
    }

    /* *
     * 文件信息
     * @author ly
     * @modifyTime 2020/12/2 14:46:00
     */
    public List<Document> createDocument(AuditReq auditReq) {
        List<Document> documentList = new ArrayList<>();
        List<AuditReq.Document> documents = auditReq.getBody().getDocumentList();
        if (!CollectionUtils.isEmpty(documents)) {
            for (AuditReq.Document document : documents) {
                Document document1 = new Document();
                BeanUtils.copyProperties(document, document1);
                documentList.add(document1);
            }
        }
        return documentList;
    }


    /* *
     * 封装返回结果
     * @author ly
     * @modifyTime 2020/12/3 15:40:00
     */
    public void createResBody(Claim claim,AuditRes.AuditResBody body){
        body.setClmnum(claim.getClmnum());
        body.setExamStatus(claim.getExamStatus());

        List<AuditRes.PolicyInfo> policyInfoList = new ArrayList<>();
        List<AuditRes.HistoryPlan> historyPlanList = new ArrayList<>();
        for (Policy policy : claim.getPolicies()) {
            AuditRes.PolicyInfo policyInfo = new AuditRes.PolicyInfo();
            policyInfo.setChdrNum(policy.getChdrNum());
            policyInfo.setExamStatus(policy.getExamStatus());

            List<AuditRes.Plan> planList = new ArrayList<>();

            //险种
            for (Plan plan : policy.getPlans()) {
                AuditRes.Plan plan1 = new AuditRes.Plan();
                plan1.setCrtable(plan.getCrtable());
                plan1.setExamStatus(plan.getExamStatus());
                plan1.setEventDte(plan.getEventDte());

                //责任
                List<AuditRes.Benifit> benifitList = new ArrayList<>();
                for (Benefit benefit : plan.getBenefits()) {
                    AuditRes.Benifit benifit1 = new AuditRes.Benifit();
                    benifit1.setBenCode(benefit.getBenCode());
                    benifit1.setFormulaStr(benefit.getFormulaStr());
                    benifit1.setCalDesc(benefit.getCalDesc());
                    benifit1.setAgePct(benefit.getAgePct());
                    benifit1.setIllPct(benefit.getIllPct());
                    benifitList.add(benifit1);
                }

                plan1.setBenifitList(benifitList);
                planList.add(plan1);
            }
            policyInfo.setPlanList(planList);
            policyInfoList.add(policyInfo);

            for (HistoryClaim historyClaim : policy.getHistoryClaims()) {
                for (HistoryPlan plan : historyClaim.getPlans()) {
                    AuditRes.HistoryPlan historyPlan = new AuditRes.HistoryPlan();
                    historyPlan.setCrtable(plan.getCrtable());
                    historyPlan.setSysInd(plan.getSysInd());
                    historyPlan.setSaveInd(plan.getSaveInd());

                    List<AuditRes.HistoryBenifit> historyBenifits = new ArrayList<>();
                    for (HistoryBenefit benefit : plan.getBenefits()) {
                        AuditRes.HistoryBenifit historyBenifit = new AuditRes.HistoryBenifit();
                        historyBenifit.setBenCode(benefit.getBenCode());
                        historyBenifit.setJoinAmt(benefit.getJoinAmt());
                        historyBenifit.setJoinYear(benefit.getJoinYear());
                        historyBenifit.setJoinAccident(benefit.getJoinAccident());
                        historyBenifit.setJoinTime(benefit.getJoinTime());
                        historyBenifit.setJoinOther(benefit.getJoinOther());

                        historyBenifits.add(historyBenifit);
                    }

                    historyPlan.setHistoryBenifitList(historyBenifits);
                    historyPlanList.add(historyPlan);
                }
            }
        }

        body.setHistoryPlanList(historyPlanList);
        body.setPolicyInfoList(policyInfoList);
        setBody(claim,body);
    }

    /* *
     * 给返回body 赋值
     * @author ly
     * @modifyTime 2020/11/17 17:20:00
     */
    public void setBody(Claim claim,AuditRes.AuditResBody body) {
        List<AuditRes.Note> noteList = new ArrayList<>();
            if (!claim.getNoteSet().isEmpty()) {
                for (Object o1 : claim.getNoteSet()) {
                    Note note = (Note) o1;
                    AuditRes.Note newNote = new AuditRes.Note();
                    newNote.setPnCode(note.getPnCode());
                    newNote.setTrecTyp(note.trecTyp);
                    newNote.setPndesc("");
                    noteList.add(newNote);
                }

            }
        body.setNoteList(noteList);
    }


    /**
     * 1.文档审核
     * 2.遍历保单
     * 3.初始化保单状态
     * 4.遍历险种
     * 5.初始化险种状态 (获取事故认定日及保单年度)
     * 6.前置通用审核规则
     * 7.后置通用审核规则
     * 8.根据险种状态修改保单状态 （终止险种循环）
     * 9.根据保单状态修改赔案审核状态（终止保单循环）
     * 10.续赔件规则
     *
     * @Author ly
     * @Date  2020/12/12 15:08
     * @Param
     * @return
     **/
    @Override
    public AuditRes invokeAuditByInterfaceId(AuditReq auditReq) {
        AuditRes res = new AuditRes();
        res.setHead(new AuditRes.AuditResHead(DroolsConstants.ResultApi.SUCCESS,
                "执行规则成功",
                auditReq.getHead().getTransactionNo()));

        AuditRes.AuditResBody body = new AuditRes.AuditResBody();
        body.setClmnum("");//理赔业务号
        String visitCode = auditReq.getHead().getVisitCode();

        try {
            log.info("审核接口invokeAudit:解析请求对象");

            Claim claim = createClaim(auditReq);//理赔对象

            RuleInterface ruleInterface = ruleInterfaceBiz.findByIdentify(visitCode);
            if (ruleInterface == null) {
                res.setHead(new AuditRes.AuditResHead(DroolsConstants.ResultApi.SUCCESS,
                        "接口选择不正确",
                        auditReq.getHead().getTransactionNo()));
                return res;
            }

            RuleInterfaceJava ruleInterfaceJava = ruleInterfaceJavaBiz.getJavaByVersion(ruleInterface.getJavaVersion(),ruleInterface.getInterfaceId());
            String javaStr = ruleInterfaceJava.getInterfaceJava();
            if (StringUtils.isBlank(javaStr)) {
                //动态生成JAVA代码
                log.info("审核接口invokeAudit: 根据资料确认接口ID生成JAVA代码");
                javaStr = createJavaServce.createJavaCode(ruleInterface.getInterfaceId());

                ruleInterface.setInterfaceJava(javaStr);
                this.ruleInterfaceBiz.updateInterface(ruleInterface);
            }
            String clmnum = claim.getClmnum();

            RuleExecutionObject resultData  = thirdApiService.excute(claim,javaStr,clmnum,auditReq.getHead().getTransactionNo());
            log.info("[{}]审核接口invokeAudit:封装返回结果",claim.getClmnum());
            createResBody(claim,body);
            log.info("审核接口invokeAudit:封装返回结果");

        } catch (Exception e) {
            log.error("执行建档规则异常：{}", e);
            res.setHead(new AuditRes.AuditResHead(DroolsConstants.ResultApi.ERROR,
                    "执行规则异常:" + e.getMessage(),
                    auditReq.getHead().getTransactionNo()));
            res.setBody(new AuditRes.AuditResBody());
            return res;
        }

        res.setBody(body);
        return res;
    }
}
