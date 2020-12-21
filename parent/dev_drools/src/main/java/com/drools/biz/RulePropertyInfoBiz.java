package com.drools.biz;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.drools.mapper.RulePropertyInfoMapper;
import com.drools.model.RulePropertyInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class RulePropertyInfoBiz {
    @Resource
    private RulePropertyInfoMapper rulePropertyInfoMapper;

    /* *
     * 分页查询 
     * @author ly
     * @modifyTime 2020/11/20 14:04:00
     */
    public PageInfo page(Map<String, Object> params) {
        Integer pageNumber = (Integer) params.get("pageNumber");
        Integer pageSize = (Integer) params.get("pageSize");
        RulePropertyInfo rulePropertyInfo = new RulePropertyInfo();

        Page page = PageHelper.startPage(pageNumber,pageSize);
        List<RulePropertyInfo> list = this.rulePropertyInfoMapper.select(rulePropertyInfo);

        PageInfo pageInfo = new PageInfo<>(list);
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }

    /* *
     * 列表查询 
     * @author ly
     * @modifyTime 2020/11/20 14:25:00
     */
    public List<RulePropertyInfo> findList(RulePropertyInfo info){
        List<RulePropertyInfo> list = this.rulePropertyInfoMapper.select(info);
        return list;
    }


}
