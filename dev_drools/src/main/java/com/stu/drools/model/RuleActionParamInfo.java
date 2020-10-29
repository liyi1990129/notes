package com.stu.drools.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述：
 */
@Table(name = "RULE_ACTION_PARAM_INFO")
public class RuleActionParamInfo extends BaseModel {
    @Id
    @Column(name = "ACTION_PARAM_ID")
    private Long actionParamId;//主键
    private Long actionId;//动作id
    private String actionParamName;//参数名称
    private String actionParamDesc;//参数描述
    private String paramIdentify;//标识

    public Long getActionParamId() {
        return actionParamId;
    }

    public void setActionParamId(Long actionParamId) {
        this.actionParamId = actionParamId;
    }

    public Long getActionId() {
        return actionId;
    }

    public void setActionId(Long actionId) {
        this.actionId = actionId;
    }

    public String getActionParamName() {
        return actionParamName;
    }

    public void setActionParamName(String actionParamName) {
        this.actionParamName = actionParamName;
    }

    public String getActionParamDesc() {
        return actionParamDesc;
    }

    public void setActionParamDesc(String actionParamDesc) {
        this.actionParamDesc = actionParamDesc;
    }

    public String getParamIdentify() {
        return paramIdentify;
    }

    public void setParamIdentify(String paramIdentify) {
        this.paramIdentify = paramIdentify;
    }
}
