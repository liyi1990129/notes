package com.stu.drools.mapper;

import com.stu.drools.model.BaseRuleActionRuleRelInfo;

import java.util.List;

/**
 * 描述：
 * CLASSPATH: com.stu.drools.mapper.BaseRuleActionRuleRelInfoMapper
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/24
 */
public interface BaseRuleActionRuleRelInfoMapper {

    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     *
     * 方法说明: 获取规则与动作关系集合信息
     * @param baseRuleActionRuleRelInfo 参数
     */
    List<BaseRuleActionRuleRelInfo> findBaseRuleActionRuleRelInfoList(BaseRuleActionRuleRelInfo baseRuleActionRuleRelInfo);
}
