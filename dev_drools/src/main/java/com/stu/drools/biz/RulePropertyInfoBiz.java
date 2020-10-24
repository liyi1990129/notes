package com.stu.drools.biz;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stu.drools.mapper.RulePropertyInfoMapper;
import com.stu.drools.model.RulePropertyInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class RulePropertyInfoBiz {
    @Resource
    private RulePropertyInfoMapper rulePropertyInfoMapper;

    public PageInfo page(Map<String, Object> params) {
        Integer pageNumber = (Integer) params.get("pageNumber");
        Integer pageSize = (Integer) params.get("pageSize");
        RulePropertyInfo rulePropertyInfo = new RulePropertyInfo();

        PageHelper.startPage(pageNumber,pageSize);
        List<RulePropertyInfo> list = this.rulePropertyInfoMapper.select(rulePropertyInfo);

        PageInfo pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    public List<RulePropertyInfo> findList(RulePropertyInfo info){
        List<RulePropertyInfo> list = this.rulePropertyInfoMapper.select(info);
        return list;
    }


}
