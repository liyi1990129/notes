package com.stu.drools.biz;

import com.stu.drools.mapper.RuleConditionInfoMapper;
import com.stu.drools.model.RuleConditionInfo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RuleConditionBiz {
    @Resource
    private RuleConditionInfoMapper ruleConditionInfoMapper;

    /**
     * 方法说明: 根据规则id获取规则条件信息
     *
     * @param ruleId 规则id
     */
    public List<RuleConditionInfo> findRuleConditionInfoByRuleId(Long ruleId)  {
        if(null == ruleId){
            throw new NullPointerException("参数缺失");
        }
        return this.ruleConditionInfoMapper.findRuleConditionInfoByRuleId(ruleId,null);
    }

    public void delByRuleId(Long ruleId) {
        RuleConditionInfo ruleConditionInfo = new RuleConditionInfo();
        ruleConditionInfo.setRuleId(ruleId);
        this.ruleConditionInfoMapper.delete(ruleConditionInfo);
    }

    public void saveOrUpdate(RuleConditionInfo ruleConditionInfo) {
        if(ruleConditionInfo.getConditionId()==null){
            ruleConditionInfo.setCreUserId(Long.valueOf(1));
            ruleConditionInfo.setCreTime(new Date());
            this.ruleConditionInfoMapper.insertSelective(ruleConditionInfo);
        }else{
            Example example = new Example(RuleConditionInfo.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("conditionId",ruleConditionInfo.getConditionId());
            this.ruleConditionInfoMapper.updateByExample(ruleConditionInfo,example);
        }

    }
}
