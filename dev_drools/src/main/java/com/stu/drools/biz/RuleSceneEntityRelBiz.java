package com.stu.drools.biz;

import com.stu.drools.mapper.RuleSceneEntityRelInfoMapper;
import com.stu.drools.model.RuleEntityInfo;
import com.stu.drools.model.RuleSceneEntityRelInfo;
import com.stu.drools.model.RuleSceneInfo;
import com.stu.drools.util.StringUtil;
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

    public void saveInfo(RuleSceneEntityRelInfo ruleSceneEntityRelInfo) {
        this.ruleSceneEntityRelInfoMapper.insert(ruleSceneEntityRelInfo);
    }
}
