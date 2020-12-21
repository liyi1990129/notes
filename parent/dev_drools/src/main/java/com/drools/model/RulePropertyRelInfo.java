package com.drools.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述：
 */
@Data
@Table(name = "RULE_PROPERTY_REL")
public class RulePropertyRelInfo {
    @Id
    @Column(name = "RULE_PRO_REL_ID")
    private Long ruleProRelId;//主键
    private String ruleCode;//规则
    private Long rulePropertyId;//规则属性
    private String rulePropertyValue;//属性值

}
