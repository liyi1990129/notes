package com.drools.mapper;

import com.drools.model.RuleInfo;
import com.drools.model.RulePropertyInfo;
import com.drools.model.RulePropertyRelInfo;
import com.drools.model.RuleSceneInfo;
import com.drools.vo.RuleInfoVo;
import com.drools.vo.RulePropertyRelInfoVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 描述：
 */
public interface RuleInfoMapper extends Mapper<RuleInfo> {

    /**
     * 方法说明: 查询规则属性信息
     * @param rulePropertyInfo 参数
     */
    List<RulePropertyInfo> findBaseRulePropertyInfoList(RulePropertyInfo rulePropertyInfo);

    /**
     * 方法说明: 根据规则获取已经配置的属性信息
     * @param ruleCode 参数
     */
    List<RulePropertyRelInfoVo> findRulePropertyListByRuleId(@Param("ruleCode") String ruleCode);

    /**
     * 方法说明: 根据场景获取对应的规则规则信息
     * @param ruleSceneInfo 参数
     */
    List<RuleInfo> findBaseRuleListByScene(RuleSceneInfo ruleSceneInfo);

    List<RuleInfoVo> list(RuleInfo ruleInfo);

    void add(RuleInfo ruleInfo);
}
