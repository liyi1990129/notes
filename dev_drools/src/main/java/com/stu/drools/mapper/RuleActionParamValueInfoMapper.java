package com.stu.drools.mapper;

import com.stu.drools.model.RuleActionParamValueInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 描述：
 */
public interface RuleActionParamValueInfoMapper extends Mapper<RuleActionParamValueInfo> {

    /**
     * 方法说明: 根据动作参数或动作与规则关系id获取对应的参数信息
     *
     * @param ruleActionParamValueInfo 参数
     */
    List<RuleActionParamValueInfo> findBaseRuleActionParamValueInfoList(RuleActionParamValueInfo ruleActionParamValueInfo);


    /**
     * 方法说明: 根据参数id获取参数value
     *
     * @param paramId 参数id
     */
    RuleActionParamValueInfo findRuleParamValueByActionParamId(@Param("paramId") Long paramId);
}
