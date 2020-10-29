package com.stu.drools.biz;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stu.drools.mapper.RuleInfoMapper;
import com.stu.drools.mapper.RulePropertyRelInfoMapper;
import com.stu.drools.model.RuleInfo;
import com.stu.drools.model.RulePropertyRelInfo;
import com.stu.drools.model.RuleSceneInfo;
import com.stu.drools.util.StringUtil;
import com.stu.drools.vo.RuleInfoVo;
import com.stu.drools.vo.RulePropertyRelInfoVo;
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
    @Resource
    private RulePropertyRelInfoMapper rulePropertyRelInfoMapper;
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
    public List<RulePropertyRelInfoVo> findRulePropertyListByRuleId(final Long ruleId) {
        return this.ruleInfoMapper.findRulePropertyListByRuleId(ruleId);
    }

    public PageInfo page(Map<String,Object> params) {
        Integer pageNumber = (Integer) params.get("pageNumber");
        Integer pageSize = (Integer) params.get("pageSize");
        String ruleName = (String) params.get("ruleName");
        String sceneId = (String) params.get("sceneId");

        RuleInfo info = new RuleInfo();
        info.setRuleName(ruleName);
        if(!StringUtils.isEmpty(sceneId)){
            info.setSceneId(Long.valueOf(sceneId));
        }

        PageHelper.startPage(pageNumber, pageSize);
        List<RuleInfoVo> list = this.ruleInfoMapper.list(info);
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
        RulePropertyRelInfo info = new RulePropertyRelInfo();
        info.setRuleId(ruleId);
        rulePropertyRelInfoMapper.delete(info);
    }
    public void saveRel(RulePropertyRelInfo info){
        this.rulePropertyRelInfoMapper.insert(info);
    }
}
