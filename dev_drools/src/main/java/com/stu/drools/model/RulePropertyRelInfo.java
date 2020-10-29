package com.stu.drools.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述：
 */
@Table(name = "RULE_PROPERTY_REL")
public class RulePropertyRelInfo extends RulePropertyInfo {
    @Id
    @Column(name = "RULE_PRO_REL_ID")
    private Long ruleProRelId;//主键
    private Long ruleId;//规则
    private Long rulePropertyId;//规则属性
    private String rulePropertyValue;//属性值

    public Long getRuleProRelId() {
        return ruleProRelId;
    }

    public void setRuleProRelId(Long ruleProRelId) {
        this.ruleProRelId = ruleProRelId;
    }

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    @Override
    public Long getRulePropertyId() {
        return rulePropertyId;
    }

    @Override
    public void setRulePropertyId(Long rulePropertyId) {
        this.rulePropertyId = rulePropertyId;
    }

    public String getRulePropertyValue() {
        return rulePropertyValue;
    }

    public void setRulePropertyValue(String rulePropertyValue) {
        this.rulePropertyValue = rulePropertyValue;
    }
}
