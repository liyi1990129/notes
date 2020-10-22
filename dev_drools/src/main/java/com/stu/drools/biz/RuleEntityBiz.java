package com.stu.drools.biz;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stu.drools.mapper.RuleEntityInfoMapper;
import com.stu.drools.model.RuleEntityInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class RuleEntityBiz {
    @Resource
    private RuleEntityInfoMapper ruleEntityInfoMapper;

    public PageInfo page(Map<String, Object> params) {
        Integer pageNumber = (Integer) params.get("pageNumber");
        Integer pageSize = (Integer) params.get("pageSize");
        PageHelper.startPage(pageNumber,pageSize);
        Example example = new Example(RuleEntityInfo.class);
        Example.Criteria criteria = example.createCriteria();
        String entityName = (String) params.get("entityName");
        String entityIdentify = (String) params.get("entityIdentify");
        if(!StringUtils.isEmpty(entityIdentify)){
            criteria.andEqualTo("entityIdentify",entityIdentify);
        }
        if(!StringUtils.isEmpty(entityName)){
            criteria.andLike("entityName",entityName);
        }

        List<RuleEntityInfo> list = this.ruleEntityInfoMapper.selectByExample(example);

        PageInfo pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    /* *
     * 查询所有的实体
     * @author ly
     * @modifyTime 2020/10/22 15:30:00
     */
    public List<RuleEntityInfo> findBaseRuleEntityInfoList()  {
        return this.ruleEntityInfoMapper.selectAll();
    }

    /**
     * Date 2017/7/26
     * Author lihao [lihao@sinosoft.com]
     * <p>
     * 方法说明: 根据实体id获取实体信息
     *
     * @param id 实体id
     */
    public RuleEntityInfo findBaseRuleEntityInfoById(Integer id)  {
        return this.ruleEntityInfoMapper.findBaseRuleEntityInfoById(id);
    }
    public void delEntityInfoById(Integer id)  {
        Example example = new Example(RuleEntityInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("entityId",id);
         this.ruleEntityInfoMapper.deleteByExample(example);
    }


    public int saveOrUpdate(RuleEntityInfo info){
        if(null== info.getEntityId()){
            info.setCreTime(new Date());
            info.setCreUserId(new Long(1));
            info.setIsEffect(1);
             this.ruleEntityInfoMapper.add(info);
        }else {
            Example example = new Example(RuleEntityInfo.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("entityId",info.getEntityId());
            this.ruleEntityInfoMapper.updateByExample(info,example);

        }
        return info.getEntityId();
    }
}
