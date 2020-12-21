package com.drools.mapper.publish;

import com.drools.model.publish.PublishRuleActionInfo;
import com.drools.model.publish.PublishRuleSceneInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PublishRuleActionInfoMapper extends Mapper<PublishRuleActionInfo> {

    /**
     * 方法说明: 根据场景获取所有的动作信息
     *
     * @param sceneInfo 参数
     */
    List<PublishRuleActionInfo> findRuleActionListByScene(PublishRuleSceneInfo sceneInfo);
}
