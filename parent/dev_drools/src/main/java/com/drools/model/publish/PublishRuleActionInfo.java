package com.drools.model.publish;

import com.drools.model.BaseModel;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述：
 */
@Data
@Table(name = "PUBLISH_RULE_ACTION_INFO")
public class PublishRuleActionInfo extends BaseModel {
    @Id
    @Column(name = "ACTION_ID")
    private Long actionId;//主键
    private String actionType;//动作类型
    private String actionName;//动作名称
    private String actionDesc;//动作描述
    private String actionClass;//动作实现类
    private String ruleCode;//规则ID
    private String actionExpression;//动作表达式
    private Long leftEntityId;//左实体类ID
    private Long rightEntityId;//右实体类ID
    private String actionCode;//动作code
    private Long leftProperty;//左属性ID
    private Long rightProperty;//右属性ID
    private String sysbol;//比较符
    private String rightValue;//比较符
    private Integer publishVersion;//发布版本
    private String actionMethodType;//方法类型
}
