package com.stu.drools.vo;

import lombok.Data;

@Data
public class RulePropertyRelInfoVo {
    private Long ruleProRelId;//主键
    private Long ruleId;//规则
    private Long rulePropertyId;//规则属性
    private String rulePropertyValue;//属性值
    private String rulePropertyIdentify;//标识
    private String rulePropertyName;//名称
    private String rulePropertyDesc;//描述
    private String defaultValue;//默认值
    private Integer isEffect;//是否有效
    private String remark;//备注
}
