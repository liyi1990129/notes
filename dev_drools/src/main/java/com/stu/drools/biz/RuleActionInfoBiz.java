package com.stu.drools.biz;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stu.drools.mapper.RuleActionInfoMapper;
import com.stu.drools.model.RuleActionInfo;
import com.stu.drools.model.RuleInfo;
import com.stu.drools.model.RuleSceneInfo;
import com.stu.drools.util.StringUtil;
import com.stu.drools.vo.RuleInfoVo;
import org.springframework.stereotype.Service;
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

        PageHelper.startPage(pageNumber, pageSize);
        List<RuleActionInfo> list = this.ruleActionInfoMapper.selectByExample(example);
        PageInfo pageInfo = new PageInfo<>(list);
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
     *
     * @param ruleId 规则id
     */
    public Integer findRuleActionCountByRuleIdAndActionType(Long ruleId) {
        if (null == ruleId) {
            throw new NullPointerException("参数缺失");
        }
        return this.ruleActionInfoMapper.findRuleActionCountByRuleIdAndActionType(ruleId);
    }

    /**
     * 方法说明: 根据规则id获取动作集合
     *
     * @param ruleId 参数
     */
    public List<RuleActionInfo> findRuleActionListByRule(final Long ruleId)  {
        if (null == ruleId) {
            throw new NullPointerException("参数缺失");
        }

        return this.ruleActionInfoMapper.findRuleActionListByRule(ruleId);
    }

    public RuleActionInfo findInfoById(Long actionId){
        RuleActionInfo info = new RuleActionInfo();
        info.setActionId(actionId);
        return this.ruleActionInfoMapper.selectOne(info);
    }
    public List<RuleActionInfo> list(RuleActionInfo info){
        return this.ruleActionInfoMapper.select(info);
    }

    public Long saveOrUpdate(RuleActionInfo ruleActionInfo){
        if(null == ruleActionInfo.getActionId()){
            ruleActionInfo.setCreTime(new Date());
            ruleActionInfo.setCreUserId(new Long(1));
            ruleActionInfo.setIsEffect("1");
            this.ruleActionInfoMapper.add(ruleActionInfo);
        }else{
            Example example = new Example(RuleActionInfo.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("actionId",ruleActionInfo.getActionId());
            this.ruleActionInfoMapper.updateByExample(ruleActionInfo,example);
        }
        return ruleActionInfo.getActionId();
    }

    public void delInfo(Long id) {
        RuleActionInfo info = new RuleActionInfo();
        info.setActionId(id);
        this.ruleActionInfoMapper.delete(info);
    }
}
