package com.stu.drools.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述：
 */
@Table(name = "RULE_INFO")
public class RuleInfo extends BaseModel {
    @Id
    @Column(name = "RULE_ID")
    private Long ruleId;//主键
    private Long sceneId;//场景
    private String ruleName;//名称
    private String ruleDesc;//描述
    private Integer ruleEnabled;//是否启用

    public Long getRuleId() {
        return ruleId;
    }

    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }

    public Long getSceneId() {
        return sceneId;
    }

    public void setSceneId(Long sceneId) {
        this.sceneId = sceneId;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleDesc() {
        return ruleDesc;
    }

    public void setRuleDesc(String ruleDesc) {
        this.ruleDesc = ruleDesc;
    }

    public Integer getRuleEnabled() {
        return ruleEnabled;
    }

    public void setRuleEnabled(Integer ruleEnabled) {
        this.ruleEnabled = ruleEnabled;
    }
}
