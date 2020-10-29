package com.stu.drools.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述：
 */
@Table(name = "RULE_SCENE_ENTITY_REL")
public class RuleSceneEntityRelInfo extends BaseModel {

    @Id
    @Column(name = "SCENE_ENTITY_REL_ID")
    private Long sceneEntityRelId;//主键
    private Long sceneId;//场景id
    private Long entityId;//实体

    public Long getSceneEntityRelId() {
        return sceneEntityRelId;
    }

    public void setSceneEntityRelId(Long sceneEntityRelId) {
        this.sceneEntityRelId = sceneEntityRelId;
    }

    public Long getSceneId() {
        return sceneId;
    }

    public void setSceneId(Long sceneId) {
        this.sceneId = sceneId;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }
}
