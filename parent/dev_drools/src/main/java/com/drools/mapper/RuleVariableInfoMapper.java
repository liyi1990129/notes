package com.drools.mapper;

import com.drools.model.RuleVariableInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 描述：
 */
public interface RuleVariableInfoMapper extends Mapper<RuleVariableInfo> {

    /**
     * 方法说明: 根据变量类型或数值类型查询变量信息
     * @param ruleVariableInfo 参数
     */
    List<RuleVariableInfo> findBaseRuleVariableInfoList(RuleVariableInfo ruleVariableInfo);
}
