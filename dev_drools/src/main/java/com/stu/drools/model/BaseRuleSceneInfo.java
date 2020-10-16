package com.stu.drools.model;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述：
 * CLASSPATH: com.stu.drools.model.BaseRuleSceneInfo
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/20
 */
@Table(name = "rule_scene_info")
public class BaseRuleSceneInfo extends BaseModel {
    @Id
    @Column(name = "SCENE_ID")
    private Integer sceneId;//主键
    private String sceneIdentify;//标识
    private Integer sceneType;//类型
    private String sceneName;//名称
    private String sceneDesc;//描述

    public Integer getSceneId() {
        return sceneId;
    }

    public void setSceneId(Integer sceneId) {
        this.sceneId = sceneId;
    }

    public String getSceneIdentify() {
        return sceneIdentify;
    }

    public void setSceneIdentify(String sceneIdentify) {
        this.sceneIdentify = sceneIdentify;
    }

    public Integer getSceneType() {
        return sceneType;
    }

    public void setSceneType(Integer sceneType) {
        this.sceneType = sceneType;
    }

    public String getSceneName() {
        return sceneName;
    }

    public void setSceneName(String sceneName) {
        this.sceneName = sceneName;
    }

    public String getSceneDesc() {
        return sceneDesc;
    }

    public void setSceneDesc(String sceneDesc) {
        this.sceneDesc = sceneDesc;
    }
}
