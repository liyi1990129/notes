package com.stu.drools.mapper;

import com.stu.drools.model.RuleActionParamInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：
 * CLASSPATH: com.stu.drools.mapper.BaseRuleActionParamInfoMapper
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/24
 */
public interface RuleActionParamInfoMapper {

    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 获取动作参数信息
     * @param ruleActionParamInfo 参数
     */
    List<RuleActionParamInfo> findBaseRuleActionParamInfoList(RuleActionParamInfo ruleActionParamInfo);

    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 根据动作id获取动作参数信息
     * @param actionId 参数
     */
    List<RuleActionParamInfo> findRuleActionParamByActionId(@Param("actionId") Long actionId);
}
