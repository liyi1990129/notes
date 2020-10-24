package com.stu.drools.mapper;

import com.stu.drools.model.RuleActionInfo;
import com.stu.drools.model.RuleSceneInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 描述：
 */
public interface RuleActionInfoMapper extends Mapper<RuleActionInfo> {

    /**
     * 方法说明: 获取动作列表
     *
     * @param ruleActionInfo 参数
     */
    List<RuleActionInfo> findBaseRuleActionInfoList(RuleActionInfo ruleActionInfo);

    /**
     * 方法说明: 根据场景获取所有的动作信息
     *
     * @param sceneInfo 参数
     */
    List<RuleActionInfo> findRuleActionListByScene(RuleSceneInfo sceneInfo);


    /**
     * 方法说明: 根据规则id获取动作集合
     *
     * @param ruleId 参数
     */
    List<RuleActionInfo> findRuleActionListByRule(@Param("ruleId") Long ruleId);

    /**
     * 方法说明: 查询是否有实现类的动作
     *
     * @param ruleId 规则id
     */
    Integer findRuleActionCountByRuleIdAndActionType(@Param("ruleId") Long ruleId);

}
