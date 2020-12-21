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
@Table(name = "PUBLISH_RULE_SCENE_INFO")
public class PublishRuleSceneInfo extends BaseModel {
    @Id
    @Column(name = "SCENE_ID")
    private Long sceneId;//主键
    private String sceneIdentify;//标识
    private String sceneType;//类型
    private String sceneName;//名称
    private String sceneDesc;//描述
    private String publishStatus;//发布状态
    private Integer publishVersion;//发布版本
    private String sceneDrl;//场景drl
}
