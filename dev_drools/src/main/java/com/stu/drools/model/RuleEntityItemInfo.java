package com.stu.drools.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 描述：
 */
@Data
@Table(name = "RULE_ENTITY_ITEM_INFO")
public class RuleEntityItemInfo extends BaseModel {
    @Id
    @Column(name = "ITEM_ID")
    private Long itemId;//主键
    private Long entityId;//实体id
    private String itemName;//字段名称
    private String itemIdentify;//字段标识
    private String itemDesc;//属性描述

    @Transient
    private String itemValue;
}
