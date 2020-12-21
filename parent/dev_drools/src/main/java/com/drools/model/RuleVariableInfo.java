package com.drools.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述：
 */
@Data
@Table(name = "RULE_VARIABLE")
public class RuleVariableInfo extends BaseModel {
    @Id
    @Column(name = "VARIABLE_ID")
    private Long variableId;//主键
    private String variableName;//变量名称
    private String variableType;//变量类型
    private String defaultValue;//默认值
    private String valueType;//数值类型
    private String variableValue;//变量值

}
