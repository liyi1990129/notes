package com.stu.drools.mapper;

import com.stu.drools.model.BaseRuleInfo;
import com.stu.drools.model.BaseRulePropertyInfo;
import com.stu.drools.model.BaseRulePropertyRelInfo;
import com.stu.drools.model.BaseRuleSceneInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：
 * CLASSPATH: com.stu.drools.mapper.BaseRuleInfoMapper
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/25
 */
public interface BaseRuleInfoMapper {

    /**
     * Date 2017/7/25
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 查询规则信息集合
     * @param baseRuleInfo 参数
     */
    List<BaseRuleInfo> findBaseRuleInfoList(BaseRuleInfo baseRuleInfo);

    /**
     * Date 2017/7/25
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 查询规则属性信息
     * @param baseRulePropertyInfo 参数
     */
    List<BaseRulePropertyInfo> findBaseRulePropertyInfoList(BaseRulePropertyInfo baseRulePropertyInfo);

    /**
     * Date 2017/7/25
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 根据规则获取已经配置的属性信息
     * @param ruleId 参数
     */
    List<BaseRulePropertyRelInfo> findRulePropertyListByRuleId(@Param("ruleId") Long ruleId);

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 根据场景获取对应的规则规则信息
     * @param baseRuleSceneInfo 参数
     */
    List<BaseRuleInfo> findBaseRuleListByScene(BaseRuleSceneInfo baseRuleSceneInfo);
}
