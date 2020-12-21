package com.drools.mapper;

import com.drools.model.RuleSceneInfo;
import tk.mybatis.mapper.common.Mapper;

/**
 * 描述：
 */
public interface RuleSceneInfoMapper extends Mapper<RuleSceneInfo> {
    void add(RuleSceneInfo info);
}
