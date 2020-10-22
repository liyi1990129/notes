package com.stu.drools.mapper;

import com.stu.drools.model.RuleEntityInfo;
import com.stu.drools.model.RuleSceneEntityRelInfo;
import com.stu.drools.model.RuleSceneInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 描述：
 */
public interface RuleSceneEntityRelInfoMapper extends Mapper<RuleSceneEntityRelInfo> {

    /**
     * 方法说明: 查询场景与实体关系表信息
     */
    List<RuleSceneEntityRelInfo> findBaseRuleSceneEntityRelInfoList(RuleSceneEntityRelInfo ruleSceneEntityRelInfo);


    /**
     * 方法说明: 根据场景信息获取相关的实体信息
     */
    List<RuleEntityInfo> findBaseRuleEntityListByScene(RuleSceneInfo ruleSceneInfo);
}
