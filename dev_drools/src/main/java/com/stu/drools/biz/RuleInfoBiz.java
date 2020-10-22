package com.stu.drools.biz;

import com.stu.drools.mapper.RuleInfoMapper;
import com.stu.drools.model.RuleInfo;
import com.stu.drools.model.RulePropertyRelInfo;
import com.stu.drools.model.RuleSceneInfo;
import com.stu.drools.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RuleInfoBiz {

    @Resource
    private RuleInfoMapper ruleInfoMapper;
    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据场景获取对应的规则规则信息
     *
     * @param ruleSceneInfo 参数
     */
    public List<RuleInfo> findBaseRuleListByScene(RuleSceneInfo ruleSceneInfo) throws Exception {
        if (null == ruleSceneInfo || (null == ruleSceneInfo.getSceneId() &&
            StringUtil.strIsNull(ruleSceneInfo.getSceneIdentify()))) {
            throw new NullPointerException("参数缺失！");
        }

        return this.ruleInfoMapper.findBaseRuleListByScene(ruleSceneInfo);
    }

    /**
     * Date 2017/7/25
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据规则获取已经配置的属性信息
     *
     * @param ruleId 参数
     */
    public List<RulePropertyRelInfo> findRulePropertyListByRuleId(final Long ruleId) throws Exception {
        return this.ruleInfoMapper.findRulePropertyListByRuleId(ruleId);
    }
}
