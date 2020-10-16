package com.stu.drools.biz;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.stu.drools.mapper.BaseRulePropertyInfoMapper;
import com.stu.drools.model.BaseRulePropertyInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class RulePropertyInfoBiz {
    @Resource
    private BaseRulePropertyInfoMapper baseRulePropertyInfoMapper;

    public PageInfo page(Map<String, Object> params) {
        Integer pageNumber = (Integer) params.get("pageNumber");
        Integer pageSize = (Integer) params.get("pageSize");
        BaseRulePropertyInfo baseRulePropertyInfo = new BaseRulePropertyInfo();

        PageHelper.startPage(pageNumber,pageSize);
        List<BaseRulePropertyInfo> list = this.baseRulePropertyInfoMapper.select(baseRulePropertyInfo);

        PageInfo pageInfo = new PageInfo<>(list);
        return pageInfo;
    }


}
