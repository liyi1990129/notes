package com.stu.drools.mapper;

import com.stu.drools.model.RuleInfo;
import com.stu.drools.model.RulePropertyInfo;
import com.stu.drools.model.RulePropertyRelInfo;
import com.stu.drools.model.RuleSceneInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：
 * CLASSPATH: com.stu.drools.mapper.BaseRuleInfoMapper
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/25
 */
public interface RuleInfoMapper {

    /**
     * Date 2017/7/25
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 查询规则信息集合
     * @param baseRuleInfo 参数
     */
    List<RuleInfo> findBaseRuleInfoList(RuleInfo baseRuleInfo);

    /**
     * Date 2017/7/25
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 查询规则属性信息
     * @param rulePropertyInfo 参数
     */
    List<RulePropertyInfo> findBaseRulePropertyInfoList(RulePropertyInfo rulePropertyInfo);

    /**
     * Date 2017/7/25
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 根据规则获取已经配置的属性信息
     * @param ruleId 参数
     */
    List<RulePropertyRelInfo> findRulePropertyListByRuleId(@Param("ruleId") Long ruleId);

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 根据场景获取对应的规则规则信息
     * @param ruleSceneInfo 参数
     */
    List<RuleInfo> findBaseRuleListByScene(RuleSceneInfo ruleSceneInfo);
}
