package com.stu.drools.biz;

import com.stu.drools.mapper.BaseRuleActionParamInfoMapper;
import com.stu.drools.model.BaseRuleActionParamInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RuleActionParamBiz {
    @Resource
    private BaseRuleActionParamInfoMapper baseRuleActionParamInfoMapper;

    /**
     * Date 2017/7/24
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据动作id获取动作参数信息
     *
     * @param actionId 参数
     */
    public List<BaseRuleActionParamInfo> findRuleActionParamByActionId(Long actionId) {
        if (null == actionId) {
            throw new NullPointerException("参数缺失");
        }
        return this.baseRuleActionParamInfoMapper.findRuleActionParamByActionId(actionId);
    }
}
