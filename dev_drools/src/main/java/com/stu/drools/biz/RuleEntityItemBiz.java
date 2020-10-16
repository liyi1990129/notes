package com.stu.drools.biz;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stu.drools.mapper.BaseRuleEntityItemInfoMapper;
import com.stu.drools.model.BaseRuleEntityItemInfo;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RuleEntityItemBiz {
    @Resource
    private BaseRuleEntityItemInfoMapper baseRuleEntityItemInfoMapper;

    public PageInfo page(Map<String, Object> params) {
        Integer pageNumber = (Integer) params.get("pageNumber");
        Integer pageSize = (Integer) params.get("pageSize");
        Example example = new Example(BaseRuleEntityItemInfo.class);
        Example.Criteria criteria = example.createCriteria();

        PageHelper.startPage(pageNumber,pageSize);
        List<BaseRuleEntityItemInfo> list = this.baseRuleEntityItemInfoMapper.selectByExample(example);

        PageInfo pageInfo = new PageInfo<>(list);
        return pageInfo;
    }
    public BaseRuleEntityItemInfo findBaseRuleEntityItemInfoById(Integer id)  {
        return this.baseRuleEntityItemInfoMapper.findBaseRuleEntityItemInfoById(id);
    }

    public List<BaseRuleEntityItemInfo> listByParams(Map<String,Object> params)  {
        Integer entityId = (Integer) params.get("entityId");
        Integer isEffect = (Integer) params.get("isEffect");
        Example example = new Example(BaseRuleEntityItemInfo.class);
        Example.Criteria criteria = example.createCriteria();
        if(null!=entityId){
            criteria.andEqualTo("entityId",entityId);
        }
        if(null!=isEffect){
            criteria.andEqualTo("isEffect",isEffect);
        }

        return this.baseRuleEntityItemInfoMapper.selectByExample(example);
    }
    public void delInfoById(Integer id)  {
        Example example = new Example(BaseRuleEntityItemInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("itemId",id);
        this.baseRuleEntityItemInfoMapper.deleteByExample(example);
    }
    public void delInfoByEntityId(Integer entityId)  {
        Example example = new Example(BaseRuleEntityItemInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("entityId",entityId);
        this.baseRuleEntityItemInfoMapper.deleteByExample(example);
    }


    public void saveOrUpdate(BaseRuleEntityItemInfo info){
        if(null== info.getItemId()){
            info.setCreTime(new Date());
            info.setCreUserId(new Long(1));
            this.baseRuleEntityItemInfoMapper.insertSelective(info);
        }else {
            Example example = new Example(BaseRuleEntityItemInfo.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("itemId",info.getEntityId());
            this.baseRuleEntityItemInfoMapper.updateByExample(info,example);
        }
    }
}
