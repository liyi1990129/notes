package com.drools.vo;

import com.drools.model.RuleActionInfo;
import com.drools.model.RuleConditionInfo;
import com.drools.model.RuleInfo;
import com.drools.model.publish.PublishRuleActionInfo;
import com.drools.model.publish.PublishRuleConditionInfo;
import com.drools.model.publish.PublishRuleInfo;
import lombok.Data;

import java.util.List;


@Data
public class PublishSceneRuleInfoVo {
    PublishRuleInfo ruleInfo;//规则基本信息
    List<RulePropertyRelInfoVo> rulePropertyInfos;//规则属性
    List<PublishRuleConditionInfo> ruleConditionInfos;//规则条件
    List<PublishRuleActionInfo> ruleActionInfos;//规则动作
}
