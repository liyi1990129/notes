package com.stu.drools.mapper;

import com.stu.drools.model.RuleActionParamInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 描述：
 */
public interface RuleActionParamInfoMapper extends Mapper<RuleActionParamInfo> {

    /**
     * 方法说明: 获取动作参数信息
     * @param ruleActionParamInfo 参数
     */
    List<RuleActionParamInfo> findBaseRuleActionParamInfoList(RuleActionParamInfo ruleActionParamInfo);

    /**
     * 方法说明: 根据动作id获取动作参数信息
     * @param actionId 参数
     */
    List<RuleActionParamInfo> findRuleActionParamByActionId(@Param("actionId") Long actionId);
}
