package com.stu.drools.biz;

import com.stu.drools.mapper.BaseRuleActionInfoMapper;
import com.stu.drools.model.BaseRuleActionInfo;
import com.stu.drools.model.BaseRuleSceneInfo;
import com.stu.drools.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RuleActionInfoBiz {
    @Resource
    private BaseRuleActionInfoMapper baseRuleActionInfoMapper;
    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据场景获取所有的动作信息
     *
     * @param sceneInfo 参数
     */
    public List<BaseRuleActionInfo> findRuleActionListByScene(BaseRuleSceneInfo sceneInfo) throws Exception {
        if (null == sceneInfo || (null == sceneInfo.getSceneId() &&
            StringUtil.strIsNull(sceneInfo.getSceneIdentify()))) {
            throw new NullPointerException("参数缺失！");
        }
        return this.baseRuleActionInfoMapper.findRuleActionListByScene(sceneInfo);
    }

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 查询是否有实现类的动作
     *
     * @param ruleId 规则id
     */
    public Integer findRuleActionCountByRuleIdAndActionType(Long ruleId) {
        if (null == ruleId) {
            throw new NullPointerException("参数缺失");
        }
        return this.baseRuleActionInfoMapper.findRuleActionCountByRuleIdAndActionType(ruleId);
    }

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据规则id获取动作集合
     *
     * @param ruleId 参数
     */
    public List<BaseRuleActionInfo> findRuleActionListByRule(final Long ruleId) throws Exception {
        if (null == ruleId) {
            throw new NullPointerException("参数缺失");
        }

        return this.baseRuleActionInfoMapper.findRuleActionListByRule(ruleId);
    }
}
