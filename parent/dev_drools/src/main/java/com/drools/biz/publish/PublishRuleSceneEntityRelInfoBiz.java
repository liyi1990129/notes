package com.drools.biz.publish;

import com.drools.mapper.publish.PublishRuleSceneEntityRelInfoMapper;
import com.drools.model.RuleEntityInfo;
import com.drools.model.publish.PublishRuleSceneInfo;
import com.drools.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class PublishRuleSceneEntityRelInfoBiz {
    @Resource
    PublishRuleSceneEntityRelInfoMapper publishRuleSceneEntityRelInfoMapper;

    /**
     * 方法说明: 根据场景信息获取相关的实体信息
     */
    public List<RuleEntityInfo> findBaseRuleEntityListByScene(PublishRuleSceneInfo publishRuleSceneInfo)  {
        //判断参数
        if (null == publishRuleSceneInfo || (StringUtil.strIsNull(publishRuleSceneInfo.getSceneIdentify()) &&
            null == publishRuleSceneInfo.getPublishVersion())) {
            throw new NullPointerException("参数缺失");
        }
        //查询数据
        return this.publishRuleSceneEntityRelInfoMapper.findBaseRuleEntityListByScene(publishRuleSceneInfo);
    }
}
