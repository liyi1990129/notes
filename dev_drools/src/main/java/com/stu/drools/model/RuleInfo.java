package com.stu.drools.model;

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
    private Long sceneId;//场景
    private String ruleName;//名称
    private String ruleDesc;//描述
    private String ruleEnabled;//是否启用
}
