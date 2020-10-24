package com.stu.drools.mapper;

import com.stu.drools.model.RuleActionRuleRelInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 描述：
 */
public interface RuleActionRuleRelInfoMapper extends Mapper<RuleActionRuleRelInfo> {

    /**
     * 方法说明: 获取规则与动作关系集合信息
     * @param ruleActionRuleRelInfo 参数
     */
    List<RuleActionRuleRelInfo> findBaseRuleActionRuleRelInfoList(RuleActionRuleRelInfo ruleActionRuleRelInfo);
}
