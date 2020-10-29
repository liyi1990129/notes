package com.stu.drools.biz;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stu.drools.mapper.RuleEntityItemInfoMapper;
import com.stu.drools.model.RuleEntityItemInfo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RuleEntityItemBiz {
    @Resource
    private RuleEntityItemInfoMapper ruleEntityItemInfoMapper;

    public PageInfo page(Map<String, Object> params) {
        Integer pageNumber = (Integer) params.get("pageNumber");
        Integer pageSize = (Integer) params.get("pageSize");
        Example example = new Example(RuleEntityItemInfo.class);
        Example.Criteria criteria = example.createCriteria();

        PageHelper.startPage(pageNumber,pageSize);
        List<RuleEntityItemInfo> list = this.ruleEntityItemInfoMapper.selectByExample(example);

        PageInfo pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
    public RuleEntityItemInfo findBaseRuleEntityItemInfoById(Integer id)  {
        return this.ruleEntityItemInfoMapper.findBaseRuleEntityItemInfoById(id);
    }

    public List<RuleEntityItemInfo> listByParams(Map<String,Object> params)  {
        Long entityId = (Long) params.get("entityId");
        Integer isEffect = (Integer) params.get("isEffect");
        Example example = new Example(RuleEntityItemInfo.class);
        Example.Criteria criteria = example.createCriteria();
        if(null!=entityId){
            criteria.andEqualTo("entityId",entityId);
        }
        if(null!=isEffect){
            criteria.andEqualTo("isEffect",isEffect);
        }

        return this.ruleEntityItemInfoMapper.selectByExample(example);
    }
    public void delInfoById(Integer id)  {
        Example example = new Example(RuleEntityItemInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",id);
        this.ruleEntityItemInfoMapper.deleteByExample(example);
    }
    public void delInfoByEntityId(Long entityId)  {
        Example example = new Example(RuleEntityItemInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("entityId",entityId);
        this.ruleEntityItemInfoMapper.deleteByExample(example);
    }


    public void saveOrUpdate(RuleEntityItemInfo info){
        if(null== info.getItemId()){
            info.setCreTime(new Date());
            info.setCreUserId(new Long(1));
            this.ruleEntityItemInfoMapper.insertSelective(info);
        }else {
            Example example = new Example(RuleEntityItemInfo.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("itemId",info.getEntityId());
            this.ruleEntityItemInfoMapper.updateByExample(info,example);
        }
    }
}
