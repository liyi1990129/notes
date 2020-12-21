package com.drools.biz.publish;

import com.drools.mapper.publish.PublishRuleConditionInfoMapper;
import com.drools.model.publish.PublishRuleConditionInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class PublishRuleConditionBiz {
    @Resource
    PublishRuleConditionInfoMapper publishRuleConditionInfoMapper;

    /**
     * 方法说明: 根据规则id获取规则条件信息
     */
    public List<PublishRuleConditionInfo> findRuleConditionInfoByRuleCode(String ruleCode,Integer publishVersion)  {
        if(StringUtils.isBlank(ruleCode) || publishVersion==null){
            throw new NullPointerException("参数缺失");
        }
        PublishRuleConditionInfo info = new PublishRuleConditionInfo();
        info.setRuleCode(ruleCode);
        info.setPublishVersion(publishVersion);
        return this.publishRuleConditionInfoMapper.select(info);
    }
}
