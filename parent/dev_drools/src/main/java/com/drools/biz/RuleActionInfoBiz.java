package com.drools.biz;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.drools.biz.sys.SysSequenceBiz;
import com.drools.common.DroolsConstants;
import com.drools.mapper.RuleActionInfoMapper;
import com.drools.model.RuleActionInfo;
import com.drools.model.RuleInfo;
import com.drools.model.RuleSceneInfo;
import com.drools.util.StringUtil;
import com.drools.vo.RuleInfoVo;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RuleActionInfoBiz {
    @Resource
    private RuleActionInfoMapper ruleActionInfoMapper;
    @Resource
    private SysSequenceBiz sysSequenceBiz;

    /* *
     * 分页查询
     * @author ly
     * @modifyTime 2020/10/26 8:24:00
     */
    public PageInfo page(Map<String,Object> params) {
        Integer pageNumber = (Integer) params.get("pageNumber");
        Integer pageSize = (Integer) params.get("pageSize");
        String actionName = (String) params.get("actionName");
        Integer actionType = (Integer) params.get("actionType");

        Example example = new Example(RuleActionInfo.class);
        Example.Criteria criteria = example.createCriteria();
        if(!StringUtils.isEmpty(actionName)){
            criteria.andEqualTo("actionName",actionName);
        }
        if(actionType!=null){
            criteria.andEqualTo("actionType",actionType);
        }

        Page page = PageHelper.startPage(pageNumber, pageSize);
        List<RuleActionInfo> list = this.ruleActionInfoMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo<>(list);
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }

    /**
     * 方法说明: 根据场景获取所有的动作信息
     *
     * @param sceneInfo 参数
     */
    public List<RuleActionInfo> findRuleActionListByScene(RuleSceneInfo sceneInfo)  {
        if (null == sceneInfo || (null == sceneInfo.getSceneId() &&
            StringUtil.strIsNull(sceneInfo.getSceneIdentify()))) {
            throw new NullPointerException("参数缺失！");
        }
        return this.ruleActionInfoMapper.findRuleActionListByScene(sceneInfo);
    }

    /**
     * 方法说明: 查询是否有实现类的动作
     */
    public Integer findRuleActionCountByRuleIdAndActionType(String ruleCode) {
        if (StringUtils.isEmpty(ruleCode)) {
            throw new NullPointerException("参数缺失");
        }
        RuleActionInfo info = new RuleActionInfo();
        info.setRuleCode(ruleCode);
        info.setActionType(DroolsConstants.ActionType.TYPE_1);
        List<RuleActionInfo> list = this.ruleActionInfoMapper.select(info);
        if(CollectionUtils.isEmpty(list)){
            return 0;
        }
        return list.size();
    }

    /**
     * 方法说明: 根据规则code获取动作集合
     */
    public List<RuleActionInfo> findRuleActionListByRuleCode(final String ruleCode)  {
        if (StringUtils.isEmpty(ruleCode)) {
            throw new NullPointerException("参数缺失");
        }
        RuleActionInfo info = new RuleActionInfo();
        info.setRuleCode(ruleCode);
        return this.ruleActionInfoMapper.select(info);
    }

    /* *
     * 根据动作id获取动作 
     * @author ly
     * @modifyTime 2020/11/20 14:02:00
     */
    public RuleActionInfo findInfoById(Long actionId){
        RuleActionInfo info = new RuleActionInfo();
        info.setActionId(actionId);
        return this.ruleActionInfoMapper.selectOne(info);
    }
    
    /* *
     * 获取动作列表 
     * @author ly
     * @modifyTime 2020/11/20 14:02:00
     */
    public List<RuleActionInfo> list(RuleActionInfo info){
        return this.ruleActionInfoMapper.select(info);
    }

    /* *
     * 保存 
     * @author ly
     * @modifyTime 2020/11/20 14:15:00
     */
    public Long saveOrUpdate(RuleActionInfo ruleActionInfo){
        if(null == ruleActionInfo.getActionId()){
            ruleActionInfo.setCreTime(new Date());
            ruleActionInfo.setCreUserId(new Long(1));
            ruleActionInfo.setIsEffect(DroolsConstants.IsEffect.YES);
            //生成编号
            String code = sysSequenceBiz.gainNoByType(DroolsConstants.SeqName.ACTION_CODE);
            ruleActionInfo.setActionCode(code);
            this.ruleActionInfoMapper.insertSelective(ruleActionInfo);
        }else{
            Example example = new Example(RuleActionInfo.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("actionId",ruleActionInfo.getActionId());
            this.ruleActionInfoMapper.updateByExample(ruleActionInfo,example);
        }
        return ruleActionInfo.getActionId();
    }

    /* *
     * 根据ID删除动作
     * @author ly
     * @modifyTime 2020/11/11 13:41:00
     */
    public void delInfo(Long id) {
        RuleActionInfo info = new RuleActionInfo();
        info.setActionId(id);
        this.ruleActionInfoMapper.delete(info);
    }
}
