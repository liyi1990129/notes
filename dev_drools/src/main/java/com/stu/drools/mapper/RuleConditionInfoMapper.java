package com.stu.drools.mapper;

import com.stu.drools.model.RuleConditionInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：
 * CLASSPATH: com.stu.drools.mapper.BaseRuleConditionInfoMapper
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/24
 */
public interface RuleConditionInfoMapper {

    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 根据规则获取规则条件信息
     * @param ruleConditionInfo 参数
     */
    List<RuleConditionInfo> findBaseRuleConditionInfoList(RuleConditionInfo ruleConditionInfo);

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 根据规则获取对应的条件信息
     * @param ruleId 规则id
     */
    List<RuleConditionInfo> findRuleConditionInfoByRuleId(@Param("ruleId") Long ruleId, @Param("relFlag") Integer relFlag);
}
