package com.stu.drools.mapper;

import com.stu.drools.model.RuleActionRuleRelInfo;

import java.util.List;

/**
 * 描述：
 * CLASSPATH: com.stu.drools.mapper.BaseRuleActionRuleRelInfoMapper
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/24
 */
public interface RuleActionRuleRelInfoMapper {

    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 获取规则与动作关系集合信息
     * @param ruleActionRuleRelInfo 参数
     */
    List<RuleActionRuleRelInfo> findBaseRuleActionRuleRelInfoList(RuleActionRuleRelInfo ruleActionRuleRelInfo);
}
