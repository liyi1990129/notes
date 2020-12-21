package com.drools.biz;

import com.drools.model.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.drools.common.DroolsConstants;
import com.drools.mapper.RuleInfoMapper;
import com.drools.mapper.RulePropertyRelInfoMapper;
import com.drools.util.StringUtil;
import com.drools.vo.RuleInfoVo;
import com.drools.vo.RulePropertyRelInfoVo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RuleInfoBiz {

    @Resource
    private RuleInfoMapper ruleInfoMapper;
    @Resource
    private RulePropertyRelInfoMapper rulePropertyRelInfoMapper;
    @Resource
    private RuleConditionBiz ruleConditionBiz;
    @Resource
    private RuleActionInfoBiz ruleActionInfoBiz;
    /**
     * 方法说明: 根据场景获取对应的规则规则信息
     *
     * @param ruleSceneInfo 参数
     */
    public List<RuleInfo> findBaseRuleListByScene(RuleSceneInfo ruleSceneInfo) {
        if (null == ruleSceneInfo || (null == ruleSceneInfo.getSceneId() &&
            StringUtil.strIsNull(ruleSceneInfo.getSceneIdentify()))) {
            throw new NullPointerException("参数缺失！");
        }

        return this.ruleInfoMapper.findBaseRuleListByScene(ruleSceneInfo);
    }

    /**
     * 方法说明: 根据规则获取已经配置的属性信息
     */
    public List<RulePropertyRelInfoVo> findRulePropertyListByRuleCode(final String ruleCode) {
        return this.ruleInfoMapper.findRulePropertyListByRuleId(ruleCode);
    }

    /* *
     * 分页查询 
     * @author ly
     * @modifyTime 2020/11/20 14:03:00
     */
    public PageInfo page(Map<String,Object> params) {
        Integer pageNumber = (Integer) params.get("pageNumber");
        Integer pageSize = (Integer) params.get("pageSize");
        String ruleName = (String) params.get("ruleName");
        String sceneIdentify = (String) params.get("sceneIdentify");

        RuleInfo info = new RuleInfo();

        if(!StringUtils.isEmpty(sceneIdentify)){
            info.setSceneIdentify(sceneIdentify);
        }
        if(!StringUtils.isEmpty(ruleName)){
            info.setRuleName(ruleName);
        }

        Page page = PageHelper.startPage(pageNumber, pageSize);
        List<RuleInfoVo> list = this.ruleInfoMapper.list(info);
        if(!CollectionUtils.isEmpty(list)){
            for (RuleInfoVo ruleInfoVo : list) {
                List<RuleConditionInfo> conditionInfos = ruleConditionBiz.findRuleConditionInfoByRuleCode(ruleInfoVo.getRuleCode());
                ruleInfoVo.setConditionInfoList(conditionInfos);

                List<RuleActionInfo> actionInfos = ruleActionInfoBiz.findRuleActionListByRuleCode(ruleInfoVo.getRuleCode());
                ruleInfoVo.setActionInfoList(actionInfos);
            }
        }


        PageInfo pageInfo = new PageInfo<>(list);
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }

    /* *
     * 根据ID 获取规则信息 
     * @author ly
     * @modifyTime 2020/11/20 14:03:00
     */
    public RuleInfo getInfoById(Long id) {
        RuleInfo info = new RuleInfo();
        info.setRuleId(id);
        return this.ruleInfoMapper.selectOne(info);
    }

    /* *
     * 根据规则id删除规则 
     * @author ly
     * @modifyTime 2020/11/20 14:03:00
     */
    public void delInfoById(Long id) {
        RuleInfo info = new RuleInfo();
        info.setRuleId(id);
        this.ruleInfoMapper.delete(info);
    }

    /* *
     * 保存 
     * @author ly
     * @modifyTime 2020/11/20 14:04:00
     */
    public Long saveOrUpdate(RuleInfo ruleInfo) {
        if(null== ruleInfo.getRuleId()){
            ruleInfo.setCreTime(new Date());
            ruleInfo.setCreUserId(new Long(1));
            ruleInfo.setIsEffect(DroolsConstants.IsEffect.YES);
            this.ruleInfoMapper.add(ruleInfo);
        }else {
            Example example = new Example(RuleInfo.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("ruleId",ruleInfo.getRuleId());
            this.ruleInfoMapper.updateByExample(ruleInfo,example);

        }
        return ruleInfo.getRuleId();
    }

    /* *
     * 根据规则编码删除规则属性 
     * @author ly
     * @modifyTime 2020/11/20 14:04:00
     */
    public void delRelByRuleCode(String ruleCode){
        RulePropertyRelInfo info = new RulePropertyRelInfo();
        info.setRuleCode(ruleCode);
        rulePropertyRelInfoMapper.delete(info);
    }
    /* *
     * 保存规则属性 
     * @author ly
     * @modifyTime 2020/11/20 14:25:00
     */
    public void saveRel(RulePropertyRelInfo info){
        this.rulePropertyRelInfoMapper.insert(info);
    }
}
