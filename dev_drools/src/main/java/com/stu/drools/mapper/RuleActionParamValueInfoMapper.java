package com.stu.drools.mapper;

import com.stu.drools.model.RuleActionParamValueInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：
 * CLASSPATH: com.stu.drools.mapper.BaseRuleActionParamValueInfoMapper
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/24
 */
public interface RuleActionParamValueInfoMapper {

    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据动作参数或动作与规则关系id获取对应的参数信息
     *
     * @param ruleActionParamValueInfo 参数
     */
    List<RuleActionParamValueInfo> findBaseRuleActionParamValueInfoList(RuleActionParamValueInfo ruleActionParamValueInfo);


    /**
     * Date 2017/7/27
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据参数id获取参数value
     *
     * @param paramId 参数id
     */
    RuleActionParamValueInfo findRuleParamValueByActionParamId(@Param("paramId") Long paramId);
}
