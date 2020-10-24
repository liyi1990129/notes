package com.stu.drools.mapper;

import com.stu.drools.model.RuleConditionInfo;
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

    /**
     * 方法说明: 根据规则获取对应的条件信息
     * @param ruleId 规则id
     */
    List<RuleConditionInfo> findRuleConditionInfoByRuleId(@Param("ruleId") Long ruleId, @Param("relFlag") Integer relFlag);
}
