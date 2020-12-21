package com.drools.biz.publish;

import com.drools.common.DroolsConstants;
import com.drools.mapper.publish.PublishRuleActionInfoMapper;
import com.drools.model.RuleActionInfo;
import com.drools.model.publish.PublishRuleActionInfo;
import com.drools.model.publish.PublishRuleSceneInfo;
import com.drools.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class PublishRuleActionInfoBiz {
    @Resource
    PublishRuleActionInfoMapper publishRuleActionInfoMapper;
    /**
     * 方法说明: 根据场景获取所有的动作信息
     * @param sceneInfo 参数
     */
    public List<PublishRuleActionInfo> findRuleActionListByScene(PublishRuleSceneInfo sceneInfo)  {
        if (null == sceneInfo || (null == sceneInfo.getPublishVersion() &&
            StringUtil.strIsNull(sceneInfo.getSceneIdentify()))) {
            throw new NullPointerException("参数缺失！");
        }
        return this.publishRuleActionInfoMapper.findRuleActionListByScene(sceneInfo);
    }

    /* *
     * 根据规则和版本获取动作数目
     * @author ly
     * @modifyTime 2020/11/20 13:59:00
     */
    public Integer findRuleActionCountByRuleCodeAndActionType(String ruleCode, Integer publishVersion) {
        if (StringUtils.isEmpty(ruleCode)) {
            throw new NullPointerException("参数缺失");
        }
        PublishRuleActionInfo info = new PublishRuleActionInfo();
        info.setRuleCode(ruleCode);
        info.setPublishVersion(publishVersion);
        info.setActionType(DroolsConstants.ActionType.TYPE_1);
        List<PublishRuleActionInfo> list = this.publishRuleActionInfoMapper.select(info);
        if(CollectionUtils.isEmpty(list)){
            return 0;
        }
        return list.size();
    }

    /* *
     * 获取动作信息
     * @author ly
     * @modifyTime 2020/11/20 14:00:00
     */
    public List<PublishRuleActionInfo> list(PublishRuleActionInfo info){
        return this.publishRuleActionInfoMapper.select(info);
    }
}
