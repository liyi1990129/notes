package com.drools.model.publish;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述：
 */
@Data
@Table(name = "PUBLISH_RULE_PROPERTY_REL")
public class PublishRulePropertyRelInfo {
    @Id
    @Column(name = "RULE_PRO_REL_ID")
    private Long ruleProRelId;//主键
    private String ruleCode;//规则
    private Long rulePropertyId;//规则属性
    private String rulePropertyValue;//属性值
    private Integer publishVersion;//发布版本

}
