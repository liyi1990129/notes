package com.stu.drools.mapper;

import com.stu.drools.model.RuleActionInfo;
import com.stu.drools.model.RuleSceneInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 描述：
 * CLASSPATH: com.stu.drools.mapper.BaseRuleActionInfoMapper
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/24
 */
public interface RuleActionInfoMapper {

    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 获取动作列表
     *
     * @param ruleActionInfo 参数
     */
    List<RuleActionInfo> findBaseRuleActionInfoList(RuleActionInfo ruleActionInfo);

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据场景获取所有的动作信息
     *
     * @param sceneInfo 参数
     */
    List<RuleActionInfo> findRuleActionListByScene(RuleSceneInfo sceneInfo);


    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据规则id获取动作集合
     *
     * @param ruleId 参数
     */
    List<RuleActionInfo> findRuleActionListByRule(@Param("ruleId") Long ruleId);

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 查询是否有实现类的动作
     *
     * @param ruleId 规则id
     */
    Integer findRuleActionCountByRuleIdAndActionType(@Param("ruleId") Long ruleId);

}
