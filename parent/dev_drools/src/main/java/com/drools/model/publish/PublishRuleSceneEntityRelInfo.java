package com.drools.model.publish;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述：
 */
@Data
@Table(name = "PUBLISH_RULE_SCENE_ENTITY_REL")
public class PublishRuleSceneEntityRelInfo {

    @Id
    @Column(name = "SCENE_ENTITY_REL_ID")
    private Long sceneEntityRelId;//主键
    private String sceneIdentify;//场景id
    private Long entityId;//实体
    private Integer publishVersion;//发布版本

}
