package com.drools.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述：
 */
@Data
@Table(name = "RULE_CONDITION_INFO")
public class RuleConditionInfo extends BaseModel {
    @Id
    @Column(name = "CONDITION_ID")
    private Long conditionId;//主键
    private String ruleCode;//规则id
    private String conditionName;//条件名称
    private String conditionExpression;//条件表达式
    private String conditionDesc;//条件描述
    private Long leftEntityId;//左实体
    private Long rightEntityId;//右实体
    private String conditionType;//类型
    private Long leftProperty;//左属性
    private Long rightProperty;//右属性
    private String sysbol;//比较符
    private String rightValue;//右边值
    private String conditionCode;//条件编号
    private String conditionMethodType;//方法类型

}
