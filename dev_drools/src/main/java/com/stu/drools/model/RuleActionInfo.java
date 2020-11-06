package com.stu.drools.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述：
 */
@Data
@Table(name = "RULE_ACTION_INFO")
public class RuleActionInfo extends BaseModel {
    @Id
    @Column(name = "ACTION_ID")
    private Long actionId;//主键
    private String actionType;//动作类型
    private String actionName;//动作名称
    private String actionDesc;//动作描述
    private String actionClass;//动作实现类


    /**
     * 获取实体标识(例如：com.stu.drools.model.TestRule  最后得到 testRule)
     */
    public String getActionClazzIdentify() {
        int index = actionClass.lastIndexOf(".");
        return actionClass.substring(index + 1).substring(0, 1).toLowerCase() +
                actionClass.substring(index + 1).substring(1);
    }


}
