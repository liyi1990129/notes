package com.drools.biz;

import com.drools.mapper.RuleSceneEntityRelInfoMapper;
import com.drools.model.RuleEntityInfo;
import com.drools.model.RuleSceneEntityRelInfo;
import com.drools.model.RuleSceneInfo;
import com.drools.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class RuleSceneEntityRelBiz {
    @Resource
    private RuleSceneEntityRelInfoMapper ruleSceneEntityRelInfoMapper;

    /**
     * 方法说明: 根据场景信息获取相关的实体信息
     *
     * @param ruleSceneInfo 参数
     */
    public List<RuleEntityInfo> findBaseRuleEntityListByScene(RuleSceneInfo ruleSceneInfo)  {
        //判断参数
        if (null == ruleSceneInfo || (StringUtil.strIsNull(ruleSceneInfo.getSceneIdentify()) &&
            null == ruleSceneInfo.getSceneId())) {
            throw new NullPointerException("参数缺失");
        }
        //查询数据
        return this.ruleSceneEntityRelInfoMapper.findBaseRuleEntityListByScene(ruleSceneInfo);
    }

    /* *
     * 保存场景关联实体 
     * @author ly
     * @modifyTime 2020/11/20 14:05:00
     */
    public void saveInfo(RuleSceneEntityRelInfo ruleSceneEntityRelInfo) {
        this.ruleSceneEntityRelInfoMapper.insert(ruleSceneEntityRelInfo);
    }

    /* *
     * 获取场景关联的实体 
     * @author ly
     * @modifyTime 2020/11/20 14:25:00
     */
    public List<RuleSceneEntityRelInfo> getList(RuleSceneEntityRelInfo ruleSceneEntityRelInfo){
        return this.ruleSceneEntityRelInfoMapper.select(ruleSceneEntityRelInfo);
    }
}
