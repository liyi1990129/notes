package com.stu.drools.biz;

import com.stu.drools.mapper.BaseRuleSceneEntityRelInfoMapper;
import com.stu.drools.model.BaseRuleEntityInfo;
import com.stu.drools.model.BaseRuleSceneInfo;
import com.stu.drools.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RuleSceneEntityRelBiz {
    @Resource
    private BaseRuleSceneEntityRelInfoMapper baseRuleSceneEntityRelInfoMapper;

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据场景信息获取相关的实体信息
     *
     * @param baseRuleSceneInfo 参数
     */
    public List<BaseRuleEntityInfo> findBaseRuleEntityListByScene(BaseRuleSceneInfo baseRuleSceneInfo) throws Exception {
        //判断参数
        if (null == baseRuleSceneInfo || (StringUtil.strIsNull(baseRuleSceneInfo.getSceneIdentify()) &&
            null == baseRuleSceneInfo.getSceneId())) {
            throw new NullPointerException("参数缺失");
        }
        //查询数据
        return this.baseRuleSceneEntityRelInfoMapper.findBaseRuleEntityListByScene(baseRuleSceneInfo);
    }
}
