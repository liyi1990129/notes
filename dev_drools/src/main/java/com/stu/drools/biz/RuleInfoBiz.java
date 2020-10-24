package com.stu.drools.biz;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stu.drools.mapper.RuleInfoMapper;
import com.stu.drools.model.RuleInfo;
import com.stu.drools.model.RulePropertyRelInfo;
import com.stu.drools.model.RuleSceneInfo;
import com.stu.drools.util.StringUtil;
import org.springframework.stereotype.Service;
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
    /**
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
     * 方法说明: 根据规则获取已经配置的属性信息
     *
     * @param ruleId 参数
     */
    public List<RulePropertyRelInfo> findRulePropertyListByRuleId(final Long ruleId) {
        return this.ruleInfoMapper.findRulePropertyListByRuleId(ruleId);
    }

    public PageInfo page(Map<String, Object> params) {
        Integer pageNumber = (Integer) params.get("pageNumber");
        Integer pageSize = (Integer) params.get("pageSize");
        PageHelper.startPage(pageNumber,pageSize);
        Example example = new Example(RuleInfo.class);
        Example.Criteria criteria = example.createCriteria();
        String entityName = (String) params.get("entityName");
        String entityIdentify = (String) params.get("entityIdentify");
        if(!StringUtils.isEmpty(entityIdentify)){
            criteria.andEqualTo("entityIdentify",entityIdentify);
        }
        if(!StringUtils.isEmpty(entityName)){
            criteria.andLike("entityName",entityName);
        }

        List<RuleInfo> list = this.ruleInfoMapper.selectByExample(example);

        PageInfo pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public RuleInfo getInfoById(Long id) {
        RuleInfo info = new RuleInfo();
        info.setRuleId(id);
        return this.ruleInfoMapper.selectOne(info);
    }

    public void delInfoById(Long id) {
        RuleInfo info = new RuleInfo();
        info.setRuleId(id);
        this.ruleInfoMapper.delete(info);
    }

    public Long saveOrUpdate(RuleInfo ruleInfo) {
        if(null== ruleInfo.getRuleId()){
            ruleInfo.setCreTime(new Date());
            ruleInfo.setCreUserId(new Long(1));
            ruleInfo.setIsEffect(1);
            this.ruleInfoMapper.insert(ruleInfo);
        }else {
            Example example = new Example(RuleInfo.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("ruleId",ruleInfo.getRuleId());
            this.ruleInfoMapper.updateByExample(ruleInfo,example);

        }
        return ruleInfo.getRuleId();
    }

    public void delRelByRuleId(Long ruleId){
        this.ruleInfoMapper.delRelByRuleId(ruleId);
    }
}
