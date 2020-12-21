package com.drools.mapper;

import com.drools.model.RuleEntityItemInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 描述：
 */
public interface RuleEntityItemInfoMapper extends Mapper<RuleEntityItemInfo> {
    /**
     * 方法说明: 根据实体id获取规则引擎实体属性信息
     * @param ruleEntityItemInfo 参数
     */
    List<RuleEntityItemInfo> findBaseRuleEntityItemInfoList(RuleEntityItemInfo ruleEntityItemInfo);

}
