package com.drools.biz.publish;

import com.drools.mapper.publish.PublishRuleInfoMapper;
import com.drools.model.publish.PublishRuleInfo;
import com.drools.model.publish.PublishRuleSceneInfo;
import com.drools.util.StringUtil;
import com.drools.vo.RulePropertyRelInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class PublishRuleInfoBiz {
    @Resource
    PublishRuleInfoMapper publishRuleInfoMapper;

    /**
     * 方法说明: 根据场景获取对应的规则规则信息
     *
     * @param ruleSceneInfo 参数
     */
    public List<PublishRuleInfo> findBaseRuleListByScene(PublishRuleSceneInfo ruleSceneInfo) {
        if (null == ruleSceneInfo || (null == ruleSceneInfo.getPublishVersion() &&
            StringUtil.strIsNull(ruleSceneInfo.getSceneIdentify()))) {
            throw new NullPointerException("参数缺失！");
        }

        return this.publishRuleInfoMapper.findBaseRuleListByScene(ruleSceneInfo);
    }

    /**
     * 方法说明: 根据规则获取已经配置的属性信息
     */
    public List<RulePropertyRelInfoVo> findRulePropertyListByRuleCode(String ruleCode,Integer publishVersion) {
        return this.publishRuleInfoMapper.findRulePropertyListByRuleCode(ruleCode,publishVersion);
    }
}
