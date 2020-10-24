package com.stu.drools.model;

import javax.persistence.Id;

/**
 * 描述：
 */
public class RuleActionRuleRelInfo extends BaseModel {
    @Id
    private Long ruleActionRelId;//主键
    private Long actionId;//动作
    private Long ruleId;//规则

    public Long getRuleActionRelId() {
        return ruleActionRelId;
    }

    public void setRuleActionRelId(Long ruleActionRelId) {
        this.ruleActionRelId = ruleActionRelId;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }
}
