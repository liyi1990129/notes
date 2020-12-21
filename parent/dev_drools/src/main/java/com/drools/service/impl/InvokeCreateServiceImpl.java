package com.drools.service.impl;

import com.drools.biz.RuleInterfaceBiz;
import com.drools.biz.RuleInterfaceJavaBiz;
import com.drools.biz.sys.SysSequenceBiz;
import com.drools.common.DroolsConstants;
import com.drools.common.inters.createapi.CreateReq;
import com.drools.common.inters.createapi.CreateRes;
import com.drools.entity.children.Note;
import com.drools.entity.clm.Claim;
import com.drools.entity.clm.Doctor;
import com.drools.entity.clm.Receiver;
import com.drools.entity.clm.Surgery;
import com.drools.model.RuleInterface;
import com.drools.model.RuleInterfaceJava;
import com.drools.model.fact.RuleExecutionObject;
import com.drools.model.fact.RuleExecutionResult;
import com.drools.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/* *
 * 资料建档调用规则 
 * @author ly
 * @modifyTime 2020/11/18 15:00:00
 */
@Slf4j
@Service
public class InvokeCreateServiceImpl  implements InvokeCreateService {

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

    @Override
    public CreateRes invokeCreateByInterfaceId(CreateReq createReq) {
        CreateRes res = new CreateRes();
        res.setHead(new CreateRes.CreateResHead(DroolsConstants.ResultApi.SUCCESS,
            "执行规则成功",
            createReq.getHead().getTransactionNo()));


        String visitCode = createReq.getHead().getVisitCode();
        CreateRes.CreateResBody body = new CreateRes.CreateResBody();
        try {
            log.info("建档接口invokeCreate：解析请求对象");
            Claim claim = createObj(createReq);
            List<Receiver> receivers = createRecevers(createReq);
            claim.setReceivers(receivers);
            claim.setReceiverLength(receivers.size());

            RuleInterface ruleInterface = ruleInterfaceBiz.findByIdentify(visitCode);
            if(ruleInterface==null){
                res.setHead(new CreateRes.CreateResHead(DroolsConstants.ResultApi.ERROR,
                    "接口选择不正确",
                    createReq.getHead().getTransactionNo()));
                return res;
            }
            RuleInterfaceJava ruleInterfaceJava = ruleInterfaceJavaBiz.getJavaByVersion(ruleInterface.getJavaVersion(),ruleInterface.getInterfaceId());
            String javaStr = ruleInterfaceJava.getInterfaceJava();

            if(StringUtils.isBlank(javaStr)){
                //动态生成JAVA代码
                log.info("建档接口invokeCreate：根据建档接口id生成JAVA代码");
                javaStr = createJavaServce.createJavaCode(ruleInterface.getInterfaceId());

                ruleInterface.setInterfaceJava(javaStr);
                this.ruleInterfaceBiz.updateInterface(ruleInterface);
            }

            String clmnum = claim.getClmnum();

            RuleExecutionObject resultData  = thirdApiService.excute(claim,javaStr,clmnum,createReq.getHead().getTransactionNo());

            log.info("建档接口invokeCreate：封装返回结果");
//            RuleExecutionResult  ruleExecutionResult = (RuleExecutionResult) resultData.getGlobalMap().get("_result");
//            if (!ruleExecutionResult.getMap().isEmpty()) {
//                setBody(resultData, body);
//            }
            createResBody(claim,body);

            body.setClmnum(claim.getClmnum());//理赔业务号
        } catch (Exception e) {
            log.error("执行建档规则异常：{}", e);
            res.setHead(new CreateRes.CreateResHead(DroolsConstants.ResultApi.ERROR,
                "执行规则异常:" + e.getMessage(),
                createReq.getHead().getTransactionNo()));
            res.setBody(new CreateRes.CreateResBody());
            return res;
        }

        res.setBody(body);
        return res;
    }

    private void createResBody(Claim claim, CreateRes.CreateResBody body) {
        List<CreateRes.Note> noteList = new ArrayList<>();
        if (!claim.getNoteSet().isEmpty()) {
            for (Object o1 : claim.getNoteSet()) {
                Note note = (Note) o1;
                CreateRes.Note newNote = new CreateRes.Note();
                newNote.setPnCode(note.getPnCode());
                newNote.setTrecTyp(note.trecTyp);
                newNote.setPndesc("");
                noteList.add(newNote);
            }

        }
        body.setNoteList(noteList);
    }

    /**
     * 资料建档调用规则
     * 1.解析报文封装drools对象
     * 2.调用赔案行政规则
     * 3.调用受款人行政规则
     * 4.返回结果
     */
    public CreateRes invokeCreate(CreateReq createReq) {
        CreateRes res = new CreateRes();
        res.setHead(new CreateRes.CreateResHead(DroolsConstants.ResultApi.SUCCESS,
            "执行规则成功",
            createReq.getHead().getTransactionNo()));


        CreateRes.CreateResBody body = new CreateRes.CreateResBody();

        try {
            log.info("建档接口invokeCreate：解析请求对象");
            Claim claim = createObj(createReq);
            List<Receiver> receivers = createRecevers(createReq);
            claim.setReceivers(receivers);
            claim.setReceiverLength(receivers.size());

            log.info("建档接口invokeCreate：创建请求对象");
            RuleExecutionObject object = new RuleExecutionObject();
            object.addFactObject(claim);

            log.info("建档接口invokeCreate：返回结果对象");
            RuleExecutionResult result = new RuleExecutionResult();
            object.setGlobal("_result", result);

            log.info("建档接口invokeCreate：执行赔案行政规则");
            RuleExecutionObject resultData =  thirdApiService.excute(object,DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_CREATE_01);

            log.info("建档接口invokeCreate：循环执行受款人行政规则");

            if(!CollectionUtils.isEmpty(receivers)){
                for (Receiver receiver : receivers) {
                    RuleExecutionObject object1 = new RuleExecutionObject();
                    object1.addFactObject(claim);
                    object1.addFactObject(receiver);
                    object1.setGlobal("_result", result);
                    resultData = thirdApiService.excute(object1,DroolsConstants.SceneIdentities.ADMIN_AUDIT_CLAIM_CREATE_02);;
                }
            }

            log.info("建档接口invokeCreate：封装返回结果");
            RuleExecutionResult  ruleExecutionResult = (RuleExecutionResult) resultData.getGlobalMap().get("_result");
            if (!ruleExecutionResult.getMap().isEmpty()) {
                setBody(resultData, body);
            }
            body.setClmnum(claim.getClmnum());//理赔业务号
        } catch (Exception e) {
            log.error("执行建档规则异常：{}", e);
            res.setHead(new CreateRes.CreateResHead(DroolsConstants.ResultApi.ERROR,
                "执行规则异常:" + e.getMessage(),
                createReq.getHead().getTransactionNo()));
            res.setBody(new CreateRes.CreateResBody());
            return res;
        }

        res.setBody(body);
        return res;
    }



    // 受款人对象
    public List<Receiver> createRecevers(CreateReq createReq) {
        List<CreateReq.Receiver> receiverList =  createReq.getBody().getReceiverList();
        List<Receiver> receivers = new ArrayList<>();

        if(!CollectionUtils.isEmpty(receiverList)){
            for (CreateReq.Receiver receiver : receiverList) {
                Receiver receiver1 = new Receiver();
                BeanUtils.copyProperties(receiver,receiver1);
                receivers.add(receiver1);
            }
        }
        return receivers;
    }


    /* *
     * 给返回body 赋值
     * @author ly
     * @modifyTime 2020/11/17 17:20:00
     */
    public void setBody(RuleExecutionObject resultData, CreateRes.CreateResBody body) {
        List<Object> list = resultData.getFactObjectList();
        List<CreateRes.Note> noteList = new ArrayList<>();
        for (Object o : list) {
            if (o instanceof Claim) {
                Claim claimResult = (Claim) o;
                for (Object o1 : claimResult.getNoteSet()) {
                    Note note = (Note) o1;
                    CreateRes.Note newNote = new CreateRes.Note();
                    newNote.setPnCode(note.getPnCode());
                    newNote.setTrecTyp(note.trecTyp);
                    newNote.setPndesc("");
                    noteList.add(newNote);
                }

            }
        }
        body.setNoteList(noteList);
    }



    /* *
     * 解析报文封装drools对象
     * @author ly
     * @modifyTime 2020/11/17 14:22:00
     */
    public Claim createObj(CreateReq createReq) {

        CreateReq.BaseInfo baseInfo = createReq.getBody().getBaseInfo();//理赔基本信息
        Claim claim = new Claim();
        BeanUtils.copyProperties(baseInfo, claim);

        CreateReq.AgentInfo agentInfo = createReq.getBody().getAgentInfo();//营销员信息
        claim.setAgtToDate(agentInfo.getAgtToDate());
        claim.setAgtFromDate(agentInfo.getAgtFromDate());
        claim.setAgtId(agentInfo.getAgtId());

        List<CreateReq.Doctor> doctors = createReq.getBody().getDoctorList();//就医信息
        List<Doctor> doctorList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(doctors)) {
            for (CreateReq.Doctor doctor : doctors) {
                Doctor doctor1 = new Doctor();
                BeanUtils.copyProperties(doctor, doctor1);
                List<CreateReq.Surgery> surgeries = doctor.getSurgeryList();//手术信息
                List<Surgery> surgeryList = new ArrayList<>();
                if (!CollectionUtils.isEmpty(surgeries)) {
                    for (CreateReq.Surgery surgery : surgeries) {
                        Surgery surgery1 = new Surgery();
                        BeanUtils.copyProperties(surgery, surgery1);
                        surgeryList.add(surgery1);
                    }
                }
                List<CreateReq.IllCode> illCodes = createReq.getBody().getIllcodeList();//伤病信息
                if (!CollectionUtils.isEmpty(illCodes)) {
                    for (CreateReq.IllCode illCode : illCodes) {
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
