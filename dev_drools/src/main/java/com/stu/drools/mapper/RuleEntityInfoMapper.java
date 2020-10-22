package com.stu.drools.mapper;

import com.stu.drools.model.RuleEntityInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * 描述：
 * CLASSPATH: com.stu.drools.mapper.BaseRuleEntityInfoMapper
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/20
 */
public interface RuleEntityInfoMapper extends Mapper<RuleEntityInfo> {


    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据实体id获取实体信息
     *
     * @param id 实体id
     */
    RuleEntityInfo findBaseRuleEntityInfoById(@Param("id") Integer id);
    RuleEntityInfo deleteBaseRuleEntityInfoById(@Param("id") Integer id);
    void add(RuleEntityInfo info);
}
