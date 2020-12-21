package com.drools.rest;

import com.alibaba.fastjson.JSON;
import com.drools.biz.RuleInterfaceLogBiz;
import com.drools.common.DroolsConstants;
import com.drools.common.inters.auditapi.AuditReq;
import com.drools.common.inters.auditapi.AuditRes;
import com.drools.common.inters.confirmapi.ConfirmReq;
import com.drools.common.inters.confirmapi.ConfirmRes;
import com.drools.common.inters.createapi.CreateReq;
import com.drools.common.inters.createapi.CreateRes;
import com.drools.service.InvokeAuditService;
import com.drools.service.InvokeConfirmService;
import com.drools.service.InvokeCreateService;
import com.drools.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Date;

/* *
 * 第三方接口
 * @author ly
 * @modifyTime 2020/11/17 13:17:00
 */
@Slf4j
@RestController
@RequestMapping(value = "/third/api")
public class ThirdApiController {

    @Autowired
    InvokeCreateService invokeCreateService;
    @Autowired
    InvokeConfirmService invokeConfirmService;
    @Autowired
    InvokeAuditService invokeAuditService;
    @Autowired
    RuleInterfaceLogBiz ruleInterfaceLogBiz;

    /* *
     * 建档
     * @author ly
     * @modifyTime 2020/11/17 13:17:00
     */
    @PostMapping(value = "/invokeCreate")
    public CreateRes invokeCreate(@RequestBody @Valid CreateReq createReq, BindingResult bindingResult){
        log.info("建档接口invokeCreate：入参[{}]", JSON.toJSONString(createReq));
        CreateRes res = new CreateRes();
        CreateReq.CreateReqHead head = createReq.getHead();

        if (bindingResult.hasErrors()) {
            log.info("建档接口invokeCreate：请求对象校验不通过[{}]",bindingResult.getFieldError().getDefaultMessage());
            res.setHead(new CreateRes.CreateResHead(DroolsConstants.ResultApi.ERROR,
                bindingResult.getFieldError().getDefaultMessage(),
                head.getTransactionNo()));
            res.setBody(new CreateRes.CreateResBody());
            log.info("建档接口invokeCreate：返回[{}]", JSON.toJSONString(res));
            return res;
        }

        if(!ruleInterfaceLogBiz.countByBatchNo(head.getTransactionNo())){
            log.info("建档接口invokeCreate：流水号重复[{}]",head.getTransactionNo());
            res.setHead(new CreateRes.CreateResHead(DroolsConstants.ResultApi.ERROR,
                    "流水号"+head.getTransactionNo()+"重复",
                    head.getTransactionNo()));
            res.setBody(new CreateRes.CreateResBody());
            log.info("建档接口invokeCreate：返回[{}]", JSON.toJSONString(res));
            return res;
        }
        log.info("建档接口invokeCreate：开始执行规则引擎，时间[{}]", DateUtil.get4yMdHms(new Date()));
        res = invokeCreateService.invokeCreateByInterfaceId(createReq);

        log.info("建档接口invokeCreate：返回[{}]", JSON.toJSONString(res));
        return res;
    }
    /* *
     * 资料确认
     * @author ly
     * @modifyTime 2020/11/17 13:17:00
     */
    @PostMapping(value = "/invokeConfirm")
    public ConfirmRes invokeConfirm(@RequestBody @Valid ConfirmReq confirmReq, BindingResult bindingResult){
        log.info("资料确认接口invokeConfirm：入参[{}]", JSON.toJSONString(confirmReq));
        ConfirmRes res = new ConfirmRes();
        ConfirmReq.ConfirmReqHead head = confirmReq.getHead();
        if (bindingResult.hasErrors()) {
            log.info("资料确认接口invokeConfirm：请求对象校验不通过[{}]",bindingResult.getFieldError().getDefaultMessage());
            res.setHead(new ConfirmRes.ConfirmResHead(DroolsConstants.ResultApi.ERROR,
                bindingResult.getFieldError().getDefaultMessage(),
                head.getTransactionNo()));
            res.setBody(new ConfirmRes.ConfirmResBody());
            log.info("资料确认接口invokeConfirm：返回[{}]", JSON.toJSONString(res));
            return res;
        }
        if(!ruleInterfaceLogBiz.countByBatchNo(head.getTransactionNo())){
            log.info("资料确认接口invokeConfirm：流水号重复[{}]",head.getTransactionNo());
            res.setHead(new ConfirmRes.ConfirmResHead(DroolsConstants.ResultApi.ERROR,
                    "流水号"+head.getTransactionNo()+"重复",
                    head.getTransactionNo()));
            res.setBody(new ConfirmRes.ConfirmResBody());
            log.info("资料确认接口invokeConfirm：返回[{}]", JSON.toJSONString(res));
            return res;
        }

        log.info("资料确认接口invokeConfirm：开始执行规则引擎，时间[{}]", DateUtil.get4yMdHms(new Date()));
//        res = invokeConfirmService.invokeConfirm(confirmReq);
        res = invokeConfirmService.invokeConfirmByInterfaceId(confirmReq);
        log.info("资料确认接口invokeConfirm：返回[{}]", JSON.toJSONString(res));

        return res;
    }
    /* *
     * 审核接口
     * @author ly
     * @modifyTime 2020/11/17 13:17:00
     */
    @PostMapping(value = "/invokeAudit")
    public AuditRes invokeAudit(@RequestBody @Valid AuditReq auditReq, BindingResult bindingResult){
        log.info("审核接口invokeAudit：入参[{}]", JSON.toJSONString(auditReq));
        AuditRes res = new AuditRes();
        AuditReq.AuditReqHead head = auditReq.getHead();
        if (bindingResult.hasErrors()) {
            log.info("审核接口invokeAudit：请求对象校验不通过[{}]",bindingResult.getFieldError().getDefaultMessage());
            res.setHead(new AuditRes.AuditResHead(DroolsConstants.ResultApi.ERROR,
                bindingResult.getFieldError().getDefaultMessage(),
                head.getTransactionNo()));
            res.setBody(new AuditRes.AuditResBody());
            log.info("审核接口invokeAudit：返回[{}]", JSON.toJSONString(res));
            return res;
        }

        if(!ruleInterfaceLogBiz.countByBatchNo(head.getTransactionNo())){
            log.info("审核接口invokeAudit：流水号重复[{}]",head.getTransactionNo());
            res.setHead(new AuditRes.AuditResHead(DroolsConstants.ResultApi.ERROR,
                    "流水号"+head.getTransactionNo()+"重复",
                    head.getTransactionNo()));
            res.setBody(new AuditRes.AuditResBody());
            log.info("审核接口invokeAudit：返回[{}]", JSON.toJSONString(res));
            return res;
        }

        log.info("审核接口invokeAudit：开始执行规则引擎，时间[{}]", DateUtil.get4yMdHms(new Date()));
//        res = invokeAuditService.invokeAudit(auditReq);
        res = invokeAuditService.invokeAuditByInterfaceId(auditReq);
        log.info("审核接口invokeAudit：返回[{}]", JSON.toJSONString(res));


        return res;
    }


}
