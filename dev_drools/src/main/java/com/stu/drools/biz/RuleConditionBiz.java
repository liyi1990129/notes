package com.stu.drools.biz;

import com.stu.drools.mapper.BaseRuleConditionInfoMapper;
import com.stu.drools.model.BaseRuleConditionInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RuleConditionBiz {
    @Resource
    private BaseRuleConditionInfoMapper baseRuleConditionInfoMapper;

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据规则id获取规则条件信息
     *
     * @param ruleId 规则id
     */
    public List<BaseRuleConditionInfo> findRuleConditionInfoByRuleId(Long ruleId) throws Exception {
        if(null == ruleId){
            throw new NullPointerException("参数缺失");
        }
        return this.baseRuleConditionInfoMapper.findRuleConditionInfoByRuleId(ruleId,null);
    }
}
