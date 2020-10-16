package com.stu.drools.biz;

import com.stu.drools.mapper.BaseRuleInfoMapper;
import com.stu.drools.model.BaseRuleInfo;
import com.stu.drools.model.BaseRulePropertyRelInfo;
import com.stu.drools.model.BaseRuleSceneInfo;
import com.stu.drools.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RuleInfoBiz {

    @Resource
    private BaseRuleInfoMapper baseRuleInfoMapper;
    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据场景获取对应的规则规则信息
     *
     * @param baseRuleSceneInfo 参数
     */
    public List<BaseRuleInfo> findBaseRuleListByScene(BaseRuleSceneInfo baseRuleSceneInfo) throws Exception {
        if (null == baseRuleSceneInfo || (null == baseRuleSceneInfo.getSceneId() &&
            StringUtil.strIsNull(baseRuleSceneInfo.getSceneIdentify()))) {
            throw new NullPointerException("参数缺失！");
        }

        return this.baseRuleInfoMapper.findBaseRuleListByScene(baseRuleSceneInfo);
    }

    /**
     * Date 2017/7/25
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据规则获取已经配置的属性信息
     *
     * @param ruleId 参数
     */
    public List<BaseRulePropertyRelInfo> findRulePropertyListByRuleId(final Long ruleId) throws Exception {
        return this.baseRuleInfoMapper.findRulePropertyListByRuleId(ruleId);
    }
}
