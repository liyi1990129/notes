package com.stu.drools.mapper;

import com.stu.drools.model.RuleEntityInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * 描述：
 */
public interface RuleEntityInfoMapper extends Mapper<RuleEntityInfo> {


    /**
     * 方法说明: 根据实体id获取实体信息
     * @param id 实体id
     */
    RuleEntityInfo findBaseRuleEntityInfoById(@Param("id") Long id);
    RuleEntityInfo deleteBaseRuleEntityInfoById(@Param("id") Long id);
    void add(RuleEntityInfo info);
}
