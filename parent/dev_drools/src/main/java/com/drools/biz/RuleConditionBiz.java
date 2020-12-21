package com.drools.biz;

import com.drools.biz.sys.SysSequenceBiz;
import com.drools.common.DroolsConstants;
import com.drools.mapper.RuleConditionInfoMapper;
import com.drools.model.RuleConditionInfo;
import com.drools.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RuleConditionBiz {
    @Resource
    private RuleConditionInfoMapper ruleConditionInfoMapper;
    @Resource
    private SysSequenceBiz sysSequenceBiz;

    /**
     * 方法说明: 根据规则id获取规则条件信息
     */
    public List<RuleConditionInfo> findRuleConditionInfoByRuleCode(String ruleCode)  {
        if(StringUtils.isBlank(ruleCode)){
            throw new NullPointerException("参数缺失");
        }
        RuleConditionInfo info = new RuleConditionInfo();
        info.setRuleCode(ruleCode);
        return this.ruleConditionInfoMapper.select(info);
    }

    /* *
     * 根据规则删除条件
     * @author ly
     * @modifyTime 2020/11/11 10:37:00
     */
    public void delByRuleCode(String ruleCode) {
        RuleConditionInfo info = new RuleConditionInfo();
        info.setRuleCode(ruleCode);
        this.ruleConditionInfoMapper.delete(info);
    }
    /* *
     * 根据ID删除条件
     * @author ly
     * @modifyTime 2020/11/11 10:37:00
     */
    public void delById(Long id) {
        RuleConditionInfo ruleConditionInfo = new RuleConditionInfo();
        ruleConditionInfo.setConditionId(id);
        this.ruleConditionInfoMapper.delete(ruleConditionInfo);
    }

    /* *
     * 保存条件
     * @author ly
     * @modifyTime 2020/11/11 10:37:00
     */
    public void saveOrUpdate(RuleConditionInfo ruleConditionInfo) {
        if(ruleConditionInfo.getConditionId()==null){
            ruleConditionInfo.setCreUserId(Long.valueOf(1));
            ruleConditionInfo.setCreTime(new Date());
            //生成编号
            String code = sysSequenceBiz.gainNoByType(DroolsConstants.SeqName.CONDITION_CODE);
            ruleConditionInfo.setConditionCode(code);
            this.ruleConditionInfoMapper.insertSelective(ruleConditionInfo);
        }else{
            Example example = new Example(RuleConditionInfo.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("conditionId",ruleConditionInfo.getConditionId());
            this.ruleConditionInfoMapper.updateByExample(ruleConditionInfo,example);
        }

    }
}
