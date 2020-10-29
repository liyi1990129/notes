package com.stu.drools.biz;

import com.stu.drools.mapper.RuleActionParamInfoMapper;
import com.stu.drools.model.RuleActionParamInfo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RuleActionParamBiz {
    @Resource
    private RuleActionParamInfoMapper ruleActionParamInfoMapper;

    /**
     * 方法说明: 根据动作id获取动作有效参数信息
     * @param actionId 参数
     */
    public List<RuleActionParamInfo> findRuleActionParamByActionId(Long actionId) {
        if (null == actionId) {
            throw new NullPointerException("参数缺失");
        }
        RuleActionParamInfo info = new RuleActionParamInfo();
        info.setActionId(actionId);
        info.setIsEffect(1);
        return this.ruleActionParamInfoMapper.select(info);
    }

    public List<RuleActionParamInfo> listByActionId(Long actionId) {
        if (null == actionId) {
            throw new NullPointerException("参数缺失");
        }
        RuleActionParamInfo info = new RuleActionParamInfo();
        info.setActionId(actionId);
        return this.ruleActionParamInfoMapper.select(info);
    }

    public void delByActionId(Long actionId){
        RuleActionParamInfo info = new RuleActionParamInfo();
        info.setActionId(actionId);
        this.ruleActionParamInfoMapper.delete(info);
    }


    public void saveOrUpdate(RuleActionParamInfo info) {
        if(info.getActionParamId()==null){
            info.setCreTime(new Date());
            info.setCreUserId(new Long(1));
            info.setIsEffect(1);
            this.ruleActionParamInfoMapper.insert(info);
        }else{
            Example example = new Example(RuleActionParamInfo.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("actionParamId",info.getActionParamId());
            this.ruleActionParamInfoMapper.updateByExample(info,example);
        }
    }
}
