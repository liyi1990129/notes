package com.stu.drools.biz;

import com.stu.drools.mapper.RuleActionRuleRelInfoMapper;
import com.stu.drools.model.RuleActionParamValueInfo;
import com.stu.drools.model.RuleActionRuleRelInfo;
import com.stu.drools.vo.RuleActionRuleRelInfoVo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RuleActionRuleRelInfoBiz {
    @Resource
    private RuleActionRuleRelInfoMapper ruleActionRuleRelInfoMapper;
    @Resource
    private RuleActionParamValueBiz ruleActionParamValueBiz;

    public List<RuleActionRuleRelInfo> listByRuleId(Long ruleId){
        RuleActionRuleRelInfo relInfo = new RuleActionRuleRelInfo();
        relInfo.setRuleId(ruleId);
        return this.ruleActionRuleRelInfoMapper.select(relInfo);
    }
    public void delByRuleId(Long ruleId){
        RuleActionRuleRelInfo relInfo = new RuleActionRuleRelInfo();
        relInfo.setRuleId(ruleId);
        List<RuleActionRuleRelInfo> list = this.ruleActionRuleRelInfoMapper.select(relInfo);
        if(!CollectionUtils.isEmpty(list)){
            for (RuleActionRuleRelInfo ruleActionRuleRelInfo : list) {
                this.ruleActionParamValueBiz.delByActionId(ruleActionRuleRelInfo.getRuleActionRelId());
            }
        }
        this.ruleActionRuleRelInfoMapper.delete(relInfo);
    }

    public void saveOrUpdate(RuleActionRuleRelInfoVo vo) {
        RuleActionRuleRelInfo info = new RuleActionRuleRelInfo();
        info.setActionId(vo.getActionId());
        info.setRuleId(vo.getRuleId());
        info.setCreUserId(Long.valueOf(1));
        info.setCreTime(new Date());
        info.setIsEffect("1");
        this.ruleActionRuleRelInfoMapper.add(info);
        Long id = info.getRuleActionRelId();
        if(!CollectionUtils.isEmpty(vo.getRelList())){
            for (RuleActionParamValueInfo ruleActionParamValueInfo : vo.getRelList()) {
                ruleActionParamValueInfo.setRuleActionRelId(id);
                ruleActionParamValueInfo.setCreTime(new Date());
                this.ruleActionParamValueBiz.saveOrUpdate(ruleActionParamValueInfo);
            }
        }

    }
}
