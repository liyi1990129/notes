package com.stu.drools.biz;

import com.stu.drools.mapper.RuleActionParamInfoMapper;
import com.stu.drools.model.RuleActionParamInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RuleActionParamBiz {
    @Resource
    private RuleActionParamInfoMapper ruleActionParamInfoMapper;

    /**
     * 方法说明: 根据动作id获取动作参数信息
     *
     * @param actionId 参数
     */
    public List<RuleActionParamInfo> findRuleActionParamByActionId(Long actionId) {
        if (null == actionId) {
            throw new NullPointerException("参数缺失");
        }
        return this.ruleActionParamInfoMapper.findRuleActionParamByActionId(actionId);
    }
}
