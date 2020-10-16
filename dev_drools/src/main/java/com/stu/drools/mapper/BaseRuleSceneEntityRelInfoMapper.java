package com.stu.drools.mapper;

import com.stu.drools.model.BaseRuleEntityInfo;
import com.stu.drools.model.BaseRuleSceneEntityRelInfo;
import com.stu.drools.model.BaseRuleSceneInfo;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 描述：
 * CLASSPATH: com.stu.drools.mapper.BaseRuleSceneEntityRelInfoMapper
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/24
 */
public interface BaseRuleSceneEntityRelInfoMapper extends Mapper<BaseRuleSceneEntityRelInfo> {

    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 查询场景与实体关系表信息
     * @param baseRuleSceneEntityRelInfo 参数
     */
    List<BaseRuleSceneEntityRelInfo> findBaseRuleSceneEntityRelInfoList(BaseRuleSceneEntityRelInfo baseRuleSceneEntityRelInfo);


    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 根据场景信息获取相关的实体信息
     * @param baseRuleSceneInfo 参数
     */
    List<BaseRuleEntityInfo> findBaseRuleEntityListByScene(BaseRuleSceneInfo baseRuleSceneInfo);
}
