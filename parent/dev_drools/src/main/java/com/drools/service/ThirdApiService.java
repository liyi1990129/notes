package com.drools.service;

import com.drools.entity.clm.Claim;
import com.drools.model.fact.RuleExecutionObject;

public interface ThirdApiService {

    /* *
     * 执行drools规则
     * @author ly
     * @modifyTime 2020/11/18 14:04:00
     */
    public RuleExecutionObject excute(RuleExecutionObject object,String scene);

    /* *
     * 执行drools规则
     * @author ly
     * @modifyTime 2020/11/18 14:04:00
     */
    public RuleExecutionObject excute(RuleExecutionObject object,String scene,String interfaceIdentity,String batchCode,String clmnum);


    /* *
     *
     * @param claim
     * @param javaStr
     * @author ly
     * @modifyTime 2020/11/25 11:04:00
     */
    public RuleExecutionObject excute(Claim claim, String javaStr,String clmnum,String batchNo) throws Exception;
}
