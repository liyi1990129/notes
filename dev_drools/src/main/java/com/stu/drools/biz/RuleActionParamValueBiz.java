package com.stu.drools.biz;

import com.stu.drools.mapper.BaseRuleActionParamValueInfoMapper;
import com.stu.drools.model.BaseRuleActionParamValueInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RuleActionParamValueBiz {
    @Resource
    private BaseRuleActionParamValueInfoMapper baseRuleActionParamValueInfoMapper;

    /**
     * Date 2017/7/27
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据参数id获取参数value
     *
     * @param paramId 参数id
     */
    public BaseRuleActionParamValueInfo findRuleParamValueByActionParamId(Long paramId) throws Exception {
        if(null == paramId){
            throw new NullPointerException("参数缺失");
        }
        return this.baseRuleActionParamValueInfoMapper.findRuleParamValueByActionParamId(paramId);
    }
}
