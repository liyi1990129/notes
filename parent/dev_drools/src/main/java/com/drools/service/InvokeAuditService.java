package com.drools.service;

import com.drools.common.inters.auditapi.AuditReq;
import com.drools.common.inters.auditapi.AuditRes;
import com.drools.common.inters.confirmapi.ConfirmReq;
import com.drools.common.inters.confirmapi.ConfirmRes;
import com.drools.entity.clm.Claim;
import com.drools.entity.clm.Plan;
import com.drools.entity.clm.Policy;
import com.drools.model.fact.RuleExecutionObject;

public interface InvokeAuditService {
    /**
     * 审核调用规则
     */
    public AuditRes invokeAudit(AuditReq auditReq);
    /**
     * 审核调用规则
     */
    public AuditRes invokeAuditByInterfaceId(AuditReq auditReq);


    /*
     *文档审核
     **/
    public RuleExecutionObject documentAudit(Claim claim);

    /*
     *前置
     **/
    public RuleExecutionObject preAudit(Claim claim, Policy policy, Plan plan);
    /*
     *后置
     **/
    public RuleExecutionObject afterAudit(Claim claim, Policy policy, Plan plan);
    /*
     *根据险种状态修改保单状态
     **/
    public void setExamStatusByPlan(Policy policy, Plan plan);
    /*
     *根据保单状态修改赔案审核状态
     **/
    public void setExamStatusByPolicy(Claim claim, Policy policy);
    /*
     *续赔件规则
     **/
    public RuleExecutionObject auditHistory(Claim claim) ;
}
