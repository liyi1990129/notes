package com.drools.service;

import com.drools.model.fact.RuleExecutionObject;

public interface DroolsActionService {
    public String getTemplate(String scene,Integer version);

    public RuleExecutionObject excute(RuleExecutionObject ruleExecutionObject, String scene,Integer version, String droolRuleStr);
}
