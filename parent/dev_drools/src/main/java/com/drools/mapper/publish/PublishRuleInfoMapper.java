package com.drools.mapper.publish;

import com.drools.model.publish.PublishRuleInfo;
import com.drools.model.publish.PublishRuleSceneInfo;
import com.drools.vo.RulePropertyRelInfoVo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface PublishRuleInfoMapper extends Mapper<PublishRuleInfo> {

    /**
     * 方法说明: 根据场景获取对应的规则规则信息
     * @param ruleSceneInfo 参数
     */
    List<PublishRuleInfo> findBaseRuleListByScene(PublishRuleSceneInfo ruleSceneInfo);

    /**
     * 方法说明: 根据规则获取已经配置的属性信息
     * @param ruleCode 参数
     */
    List<RulePropertyRelInfoVo> findRulePropertyListByRuleCode(@Param("ruleCode") String ruleCode,@Param("publishVersion") Integer publishVersion);
}
