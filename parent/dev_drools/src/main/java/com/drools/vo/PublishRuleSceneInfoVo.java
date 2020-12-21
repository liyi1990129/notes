package com.drools.vo;

import com.drools.model.publish.PublishRuleSceneInfo;
import lombok.Data;

import java.util.List;

/* *
 * 场景规则VO
 * @author ly
 * @modifyTime 2020/11/12 13:24:00
 */
@Data
public class PublishRuleSceneInfoVo {
    PublishRuleSceneInfo ruleSceneInfo;//场景信息
    List<PublishSceneRuleInfoVo> ruleInfoList;//规则信息
}

