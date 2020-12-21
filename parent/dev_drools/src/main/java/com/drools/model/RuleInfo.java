package com.drools.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述：
 */
@Data
@Table(name = "RULE_INFO")
public class RuleInfo extends BaseModel {
    @Id
    @Column(name = "RULE_ID")
    private Long ruleId;//主键
    private String sceneIdentify;//场景id
    private String ruleName;//名称
    private String ruleDesc;//描述
    private String ruleEnabled;//是否启用
    private String ruleCode;//规则编号
}
