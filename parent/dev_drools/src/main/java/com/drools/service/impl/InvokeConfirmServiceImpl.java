package com.drools.service.impl;

import com.drools.biz.RuleInterfaceBiz;
import com.drools.biz.RuleInterfaceJavaBiz;
import com.drools.biz.sys.SysSequenceBiz;
import com.drools.common.DroolsConstants;
import com.drools.common.inters.confirmapi.ConfirmReq;
import com.drools.common.inters.confirmapi.ConfirmRes;
import com.drools.entity.children.Note;
import com.drools.entity.clm.*;
import com.drools.model.RuleInterface;
import com.drools.model.RuleInterfaceJava;
import com.drools.model.fact.RuleExecutionObject;
import com.drools.model.fact.RuleExecutionResult;
import com.drools.service.CreateJavaServce;
import com.drools.service.DroolsActionService;
import com.drools.service.InvokeConfirmService;
import com.drools.service.ThirdApiService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/* *
 * 资料确认调用规则
 * @author ly
 * @modifyTime 2020/11/18 15:00:00
 */
@Slf4j
@Service
public class InvokeConfirmServiceImpl implements InvokeConfirmService {
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


    public ConfirmRes invokeConfirmByInterfaceId(ConfirmReq confirmReq){
        ConfirmRes res = new ConfirmRes();
        res.setHead(new ConfirmRes.ConfirmResHead(DroolsConstants.ResultApi.SUCCESS,
            "执行规则成功",
            confirmReq.getHead().getTransactionNo()));

        ConfirmRes.ConfirmResBody body = new ConfirmRes.ConfirmResBody();
        String visitCode = confirmReq.getHead().getVisitCode();

        try{
            log.info("资料确认接口invokeConfirm:解析请求对象");
            List<Policy> policyList =  createPolicys(confirmReq);//保单对象
            List<Receiver> receiverList =  createRecevers(confirmReq);//受款人对象
            List<Document> documentList = createDocument(confirmReq);//文档对象
            Claim claim = createClaim(confirmReq);//理赔对象
            claim.setReceivers(receiverList);
            claim.setReceiverLength(receiverList.size());
            claim.setDocuments(documentList);
            claim.setDocumentLength(documentList.size());
            claim.setPolicies(policyList);
            claim.setPolicyLength(policyList.size());

            RuleInterface ruleInterface = ruleInterfaceBiz.findByIdentify(visitCode);
            if(ruleInterface==null){
                res.setHead(new ConfirmRes.ConfirmResHead(DroolsConstants.ResultApi.SUCCESS,
                    "接口选择不正确",
                    confirmReq.getHead().getTransactionNo()));
                return res;
            }

            RuleInterfaceJava ruleInterfaceJava = ruleInterfaceJavaBiz.getJavaByVersion(ruleInterface.getJavaVersion(),ruleInterface.getInterfaceId());
            String javaStr = ruleInterfaceJava.getInterfaceJava();
            if(StringUtils.isBlank(javaStr)){
                //动态生成JAVA代码
                log.info("资料确认接口invokeConfirm: 根据资料确认接口ID生成JAVA代码");
                javaStr = createJavaServce.createJavaCode(ruleInterface.getInterfaceId());

                ruleInterface.setInterfaceJava(javaStr);
                this.ruleInterfaceBiz.updateInterface(ruleInterface);
            }

            String clmnum = claim.getClmnum();

            RuleExecutionObject resultData  = thirdApiService.excute(claim,javaStr,clmnum,confirmReq.getHead().getTransactionNo());

            log.info("资料确认接口invokeConfirm:封装返回结果");
//            RuleExecutionResult  ruleExecutionResult = (RuleExecutionResult) resultData.getGlobalMap().get("_result");
//            if (!ruleExecutionResult.getMap().isEmpty()) {
//                setBody(resultData, body);
//            }
            createResBody(claim,body);

            body.setClmnum(claim.getClmnum());//理赔业务号
        }catch (Exception e){
            log.error("执行建档规则异常：{}", e);
            res.setHead(new ConfirmRes.ConfirmResHead(DroolsConstants.ResultApi.ERROR,
                "执行规则异常:" + e.getMessage(),
                confirmReq.getHead().getTransactionNo()));
            res.setBody(new ConfirmRes.ConfirmResBody());
            return res;
        }

        res.setBody(body);
        return res;
    }

    private void createResBody(Claim claim, ConfirmRes.ConfirmResBody body) {
        List<ConfirmRes.Note> noteList = new ArrayList<>();
        if (!claim.getNoteSet().isEmpty()) {
            for (Object o1 : claim.getNoteSet()) {
                Note note = (Note) o1;
                ConfirmRes.Note newNote = new ConfirmRes.Note();
                newNote.setPnCode(note.getPnCode());
                newNote.setTrecTyp(note.trecTyp);
                newNote.setPndesc("");
                noteList.add(newNote);
            }

        }
        body.setNoteList(noteList);
    }

    /* *
     * 资料确认调用规则
     * 1.解析报文封装drools对象
     * 2.获取建档场景
     * 3.调用规则引擎
     *  ①执行赔案行政规则
     *  ②遍历保单对象
     *   --> 循环受款人行政规则
     *   --> 遍历对象下的险种信息
     *           执行保单行政规则、
     *           执行险种行政规则
     *       --> 遍历医生信息
     *           循环执行就医行政规则
     *       --> 遍历文档信息
     *           循环执行赔案文件审核规则、险种文件审核规则
     *
     *
     * 4.返回结果
     * @author ly
     * @modifyTime 2020/11/18 15:00:00
     */
    @Override
    public ConfirmRes invokeConfirm(ConfirmReq confirmReq) {
        ConfirmRes res = new ConfirmRes();
        res.setHead(new ConfirmRes.ConfirmResHead(DroolsConstants.ResultApi.SUCCESS,
            "执行规则成功",
            confirmReq.getHead().getTransactionNo()));

        ConfirmRes.ConfirmResBody body = new ConfirmRes.ConfirmResBody();


        try{
            log.info("资料确认接口invokeConfirm:解析请求对象");
            List<Policy> policyList =  createPolicys(confirmReq);//保单对象
            List<Receiver> receiverList =  createRecevers(confirmReq);//受款人对象
            List<Document> documentList = createDocument(confirmReq);//文档对象
            Claim claim = createClaim(confirmReq);//理赔对象
            claim.setReceivers(receiverList);
            claim.setReceiverLength(receiverList.size());
            claim.setDocuments(documentList);
            claim.setDocumentLength(documentList.size());
            claim.setPolicies(policyList);
            claim.setPolicyLength(policyList.size());

            // *********************2.执行规则*************************************
            log.info("资料确认接口invokeConfirm:创建请求对象");
            RuleExecutionObject object = new RuleExecutionObject();
            object.addFactObject(claim);

            log.info("资料确认接口invokeConfirm:返回结果对象");
            RuleExecutionResult result = new RuleExecutionResult();
            object.setGlobal("_result", result);

            log.info("资料确认接口invokeConfirm:执行赔案行政规则");
            RuleExecutionObject resultData =  thirdApiService.excute(object,DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_CREATE_01);


            log.info("资料确认接口invokeConfirm:循环保单信息");
            for (Policy policy : policyList) {

                //循环受款人行政规则
                log.info("资料确认接口invokeConfirm:循环受款人信息");
                if(!CollectionUtils.isEmpty(receiverList)){
                    for (Receiver receiver : receiverList) {
                        RuleExecutionObject object1 = new RuleExecutionObject();
                        object1.addFactObject(claim);
                        object1.addFactObject(receiver);
                        object1.setGlobal("_result", result);
                        resultData = thirdApiService.excute(object1,DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_CONFIRM_02);;
                    }
                }

                log.info("资料确认接口invokeConfirm:循环险种信息");
                if(!CollectionUtils.isEmpty(policy.getPlans())){
                    for (Plan plan : policy.getPlans()) {
                        RuleExecutionObject object2 = new RuleExecutionObject();
                        object2.addFactObject(claim);
                        object2.addFactObject(policy);
                        object2.addFactObject(plan);
                        object2.setGlobal("_result", result);

                        //保单行政规则
                        resultData =  thirdApiService.excute(object2, DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_CONFIRM_01);
                        //险种行政规则
                        resultData =  thirdApiService.excute(object2, DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_CONFIRM_03);

                        //循环就医行政规则
                        log.info("资料确认接口invokeConfirm:循环医生信息");
                        if(!CollectionUtils.isEmpty(claim.getDoctors())){
                            for (Doctor doctor : claim.getDoctors()) {
                                RuleExecutionObject object3 = new RuleExecutionObject();
                                object3.addFactObject(claim);
                                object3.addFactObject(policy);
                                object3.addFactObject(plan);
                                object3.addFactObject(doctor);
                                object3.setGlobal("_result", result);
                                resultData =  thirdApiService.excute(object, DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_CONFIRM_04);
                            }
                        }

                        //循环文件审核规则
                        log.info("资料确认接口invokeConfirm:循环文件信息");
                        if(!CollectionUtils.isEmpty(claim.getDocuments())){
                            for (Document document : claim.getDocuments()) {
                                RuleExecutionObject object3 = new RuleExecutionObject();
                                object3.addFactObject(claim);
                                object3.addFactObject(policy);
                                object3.addFactObject(plan);
                                object3.addFactObject(document);
                                object3.setGlobal("_result", result);

                                //5.赔案文件审核规则
                                resultData =  thirdApiService.excute(object3, DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_CONFIRM_05);
                                //6.险种文件审核规则
                                resultData =  thirdApiService.excute(object3, DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_CONFIRM_06);
                            }
                        }

                    }
                }
            }



            log.info("资料确认接口invokeConfirm:封装返回结果");
            RuleExecutionResult  ruleExecutionResult = (RuleExecutionResult) resultData.getGlobalMap().get("_result");
            if (!ruleExecutionResult.getMap().isEmpty()) {
                setBody(resultData, body);
            }
            body.setClmnum(claim.getClmnum());//理赔业务号

        }catch (Exception e){
            log.error("执行建档规则异常：{}", e);
            res.setHead(new ConfirmRes.ConfirmResHead(DroolsConstants.ResultApi.ERROR,
                "执行规则异常:" + e.getMessage(),
                confirmReq.getHead().getTransactionNo()));
            res.setBody(new ConfirmRes.ConfirmResBody());
            return res;
        }

        res.setBody(body);
        return res;
    }

    /* *
     * 给返回body 赋值
     * @author ly
     * @modifyTime 2020/11/17 17:20:00
     */
    public void setBody(RuleExecutionObject resultData, ConfirmRes.ConfirmResBody body) {
        List<Object> list = resultData.getFactObjectList();
        List<ConfirmRes.Note> noteList = new ArrayList<>();
        for (Object o : list) {
            if (o instanceof Claim) {
                Claim claimResult = (Claim) o;
                for (Object o1 : claimResult.getNoteSet()) {
                    Note note = (Note) o1;
                    ConfirmRes.Note newNote = new ConfirmRes.Note();
                    newNote.setPnCode(note.getPnCode());
                    newNote.setTrecTyp(note.trecTyp);
                    newNote.setPndesc("");
                    noteList.add(newNote);
                }

            }
        }
        body.setNoteList(noteList);
    }

    public List<Document> createDocument(ConfirmReq confirmReq){
        List<Document> documentList = new ArrayList<>();
        List<ConfirmReq.Document> documents = confirmReq.getBody().getDocumentList();
        if(!CollectionUtils.isEmpty(documents)){
            for (ConfirmReq.Document document : documents) {
                Document document1 = new Document();
                BeanUtils.copyProperties(document,document1);
                documentList.add(document1);
            }
        }

        return documentList;
    }


    //保单对象
    public List<Policy> createPolicys(ConfirmReq confirmReq){
        List<Policy> policyList = new ArrayList<>();

        List<ConfirmReq.PolicyInfo> policyInfos = confirmReq.getBody().getPolicyInfoList();
        if(!CollectionUtils.isEmpty(policyInfos)){
            for (ConfirmReq.PolicyInfo policyInfo : policyInfos) {
                Policy policy = new Policy();
                BeanUtils.copyProperties(policyInfo,policy);

                List<ConfirmReq.Plan> plans = policyInfo.getPlanList();
                if(!CollectionUtils.isEmpty(plans)){
                    List<Plan> planList = new ArrayList<>();
                    for (ConfirmReq.Plan plan : plans) {
                        Plan plan1 = new Plan();
                        BeanUtils.copyProperties(plan,plan1);
                        planList.add(plan1);
                    }
                    policy.setPlans(planList);
                    policy.setPlanLength(planList.size());
                }

                policyList.add(policy);
            }
        }

        return policyList;
    }

    // 受款人对象
    public List<Receiver> createRecevers(ConfirmReq confirmReq) {
        List<ConfirmReq.Receiver> receiverList =  confirmReq.getBody().getReceiverList();
        List<Receiver> receivers = new ArrayList<>();

        if(!CollectionUtils.isEmpty(receiverList)){
            for (ConfirmReq.Receiver receiver : receiverList) {
                Receiver receiver1 = new Receiver();
                BeanUtils.copyProperties(receiver,receiver1);
                receivers.add(receiver1);
            }
        }
        return receivers;
    }

    //理赔对象
    public Claim createClaim(ConfirmReq confirmReq){
        ConfirmReq.ConfirmBaseInfo baseInfo = confirmReq.getBody().getBaseInfo();//理赔基本信息
        Claim claim = new Claim();
        BeanUtils.copyProperties(baseInfo, claim);

        ConfirmReq.AgentInfo agentInfo = confirmReq.getBody().getAgentInfo();//营销员信息
        claim.setAgtToDate(agentInfo.getAgtToDate());
        claim.setAgtFromDate(agentInfo.getAgtFromDate());
        claim.setAgtId(agentInfo.getAgtId());

        List<ConfirmReq.Doctor> doctors = confirmReq.getBody().getDoctorList();//就医信息
        List<Doctor> doctorList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(doctors)) {
            for (ConfirmReq.Doctor doctor : doctors) {
                Doctor doctor1 = new Doctor();
                BeanUtils.copyProperties(doctor, doctor1);
                List<ConfirmReq.Surgery> surgeries = doctor.getSurgeryList();//手术信息
                List<Surgery> surgeryList = new ArrayList<>();
                if (!CollectionUtils.isEmpty(surgeries)) {
                    for (ConfirmReq.Surgery surgery : surgeries) {
                        Surgery surgery1 = new Surgery();
                        BeanUtils.copyProperties(surgery, surgery1);
                        surgeryList.add(surgery1);
                    }
                }

                List<ConfirmReq.IllCode> illCodes = doctor.getIllcodeList();//伤病信息
                if (!CollectionUtils.isEmpty(illCodes)) {
                    for (ConfirmReq.IllCode illCode : illCodes) {
                        doctor1.getIllCodes().add(illCode.getIllCode());
                    }
                }
                doctor1.setSurgeryLength(surgeryList.size());
                doctor1.setSurgeries(surgeryList);
                doctorList.add(doctor1);
            }
        }
        claim.setDoctors(doctorList);


        return claim;
    }
}
