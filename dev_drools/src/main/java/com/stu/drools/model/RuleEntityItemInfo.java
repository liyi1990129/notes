package com.stu.drools.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述：
 * CLASSPATH: com.stu.drools.model.BaseRuleEntityItemInfo
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/20
 */
@Table(name = "rule_entity_item_info")
public class RuleEntityItemInfo extends BaseModel {
    @Id
    @Column(name = "ITEM_ID")
    private Integer itemId;//主键
    private Integer entityId;//实体id
    private String itemName;//字段名称
    private String itemIdentify;//字段标识
    private String itemDesc;//属性描述

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemIdentify() {
        return itemIdentify;
    }

    public void setItemIdentify(String itemIdentify) {
        this.itemIdentify = itemIdentify;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }
}
