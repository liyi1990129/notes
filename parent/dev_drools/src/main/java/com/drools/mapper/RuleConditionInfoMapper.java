package com.drools.mapper;

import com.drools.model.RuleConditionInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 描述：
 */
public interface RuleConditionInfoMapper extends Mapper<RuleConditionInfo> {

    /**
     * 方法说明: 根据规则获取规则条件信息
     * @param ruleConditionInfo 参数
     */
    List<RuleConditionInfo> findBaseRuleConditionInfoList(RuleConditionInfo ruleConditionInfo);

}
