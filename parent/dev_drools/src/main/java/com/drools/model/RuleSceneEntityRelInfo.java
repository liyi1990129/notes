package com.drools.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述：
 */
@Data
@Table(name = "RULE_SCENE_ENTITY_REL")
public class RuleSceneEntityRelInfo  {

    @Id
    @Column(name = "SCENE_ENTITY_REL_ID")
    private Long sceneEntityRelId;//主键
    private String sceneIdentify;//场景id
    private Long entityId;//实体

}
