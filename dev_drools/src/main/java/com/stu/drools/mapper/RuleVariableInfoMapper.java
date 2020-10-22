package com.stu.drools.mapper;

import com.stu.drools.model.RuleVariableInfo;

import java.util.List;

/**
 * 描述：
 * CLASSPATH: com.stu.drools.mapper.BaseRuleVariableInfoMapper
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/20
 */
public interface RuleVariableInfoMapper {

    /**
     * Date 2017/7/20
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 根据变量类型或数值类型查询变量信息
     * @param ruleVariableInfo 参数
     */
    List<RuleVariableInfo> findBaseRuleVariableInfoList(RuleVariableInfo ruleVariableInfo);
}
