package com.stu.drools.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述：
 * CLASSPATH: com.stu.drools.model.BaseRuleSceneEntityRelInfoMapper
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/24
 */
@Table(name = "rule_scene_entity_rel")
public class RuleSceneEntityRelInfo extends BaseModel {

    @Id
    @Column(name = "SCENE_ENTITY_REL_ID")
    private Integer sceneEntityRelId;//主键
    private Long sceneId;//场景id
    private Long entityId;//实体

    public Integer getSceneEntityRelId() {
        return sceneEntityRelId;
    }

    public void setSceneEntityRelId(Integer sceneEntityRelId) {
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
