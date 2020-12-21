package com.drools.mapper.publish;

import com.drools.model.RuleEntityInfo;
import com.drools.model.publish.PublishRuleSceneEntityRelInfo;
import com.drools.model.publish.PublishRuleSceneInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PublishRuleSceneEntityRelInfoMapper extends Mapper<PublishRuleSceneEntityRelInfo> {
    /* *
     * 场景关联的实体
     * @author ly
     * @modifyTime 2020/11/12 9:09:00
     */
    List<RuleEntityInfo> findBaseRuleEntityListByScene(PublishRuleSceneInfo publishRuleSceneInfo);
}
