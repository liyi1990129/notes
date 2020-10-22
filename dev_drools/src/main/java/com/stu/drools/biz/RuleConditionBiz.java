package com.stu.drools.biz;

import com.stu.drools.mapper.RuleConditionInfoMapper;
import com.stu.drools.model.RuleConditionInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RuleConditionBiz {
    @Resource
    private RuleConditionInfoMapper ruleConditionInfoMapper;

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据规则id获取规则条件信息
     *
     * @param ruleId 规则id
     */
    public List<RuleConditionInfo> findRuleConditionInfoByRuleId(Long ruleId) throws Exception {
        if(null == ruleId){
            throw new NullPointerException("参数缺失");
        }
        return this.ruleConditionInfoMapper.findRuleConditionInfoByRuleId(ruleId,null);
    }
}
