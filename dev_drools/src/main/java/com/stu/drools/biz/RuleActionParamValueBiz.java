package com.stu.drools.biz;

import com.stu.drools.mapper.RuleActionParamValueInfoMapper;
import com.stu.drools.model.RuleActionParamValueInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RuleActionParamValueBiz {
    @Resource
    private RuleActionParamValueInfoMapper ruleActionParamValueInfoMapper;

    /**
     * 方法说明: 根据参数id获取参数value
     *
     * @param paramId 参数id
     */
    public RuleActionParamValueInfo findRuleParamValueByActionParamId(Long paramId)  {
        if(null == paramId){
            throw new NullPointerException("参数缺失");
        }
        return this.ruleActionParamValueInfoMapper.findRuleParamValueByActionParamId(paramId);
    }

    public List<RuleActionParamValueInfo> findRuleParamValueByRuleId(Long ruleId){
        return this.ruleActionParamValueInfoMapper.findRuleParamValueByRuleId(ruleId);
    }
    public List<RuleActionParamValueInfo> findRuleParamValueByRelId(Long relId){
        RuleActionParamValueInfo info = new RuleActionParamValueInfo();
        info.setRuleActionRelId(relId);
        return this.ruleActionParamValueInfoMapper.select(info);
    }
    public void delByActionId(Long relId){
        RuleActionParamValueInfo info = new RuleActionParamValueInfo();
        info.setRuleActionRelId(relId);
        this.ruleActionParamValueInfoMapper.delete(info);
    }

    public void saveOrUpdate(RuleActionParamValueInfo ruleActionParamValueInfo) {
        ruleActionParamValueInfo.setCreTime(new Date());
        ruleActionParamValueInfo.setCreUserId(Long.valueOf(1));
        ruleActionParamValueInfo.setIsEffect("1");
        this.ruleActionParamValueInfoMapper.insert(ruleActionParamValueInfo);
    }
}
