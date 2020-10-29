package com.stu.drools.vo;

import com.stu.drools.model.RuleActionParamInfo;
import com.stu.drools.model.RuleActionParamValueInfo;
import lombok.Data;

import java.util.List;

@Data
public class RuleActionRuleRelInfoVo {
    private Long actionId;//动作
    private String actionName;//动作
    private Long ruleId;//规则

    List<RuleActionParamValueInfo> relList;
    List<RuleActionParamInfo> paramInfoList;
}
