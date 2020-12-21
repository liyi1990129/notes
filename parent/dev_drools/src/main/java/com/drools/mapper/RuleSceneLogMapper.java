package com.drools.mapper;

import com.drools.model.RuleSceneLog;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface RuleSceneLogMapper extends Mapper<RuleSceneLog> {
    List<RuleSceneLog> listByBatch(RuleSceneLog ruleSceneLog);
}
