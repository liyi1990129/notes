package com.drools.vo;

import com.drools.model.RuleInfo;
import lombok.Data;

import java.util.List;

@Data
public class RuleSceneInfoVo {
    private Long sceneId;//主键
    private String sceneIdentify;//标识
    private String sceneType;//类型
    private String sceneName;//名称
    private String sceneDesc;//描述
    private String publishStatus;//发布状态
    private Integer publishVersion;//发布版本
    //是否有效
    private String isEffect;
    private List<RuleInfo> ruleInfoList;
}
