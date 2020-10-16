package com.stu.drools.rest;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.stu.drools.biz.RuleEntityBiz;
import com.stu.drools.biz.RuleEntityItemBiz;
import com.stu.drools.common.ObjectRestResponse;
import com.stu.drools.model.BaseRuleEntityInfo;
import com.stu.drools.model.BaseRuleEntityItemInfo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/entity")
public class EntityController {

    @Autowired
    private RuleEntityBiz ruleEntityBiz;
    @Autowired
    private RuleEntityItemBiz ruleEntityItemBiz;

    @PostMapping(value = "/page")
    public ObjectRestResponse list(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        PageInfo page = ruleEntityBiz.page(params);
        res.setData(page);
        res.setSuucessMsg("");
        return res;
    }
    @PostMapping(value = "/get")
    public ObjectRestResponse get(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        Integer id = (Integer) params.get("id");
        if(null==id){
            res.setErrorMsg("参数缺失");
            return res;
        }
        BaseRuleEntityInfo info = ruleEntityBiz.findBaseRuleEntityInfoById(id);
        Map<String,Object> query = new HashMap<>();
        query.put("entityId",id);
        List<BaseRuleEntityItemInfo> list =  ruleEntityItemBiz.listByParams(query);

        Map<String,Object> result = new HashMap<>();
        result.put("entity",info);
        result.put("entityItems",list);
        res.setData(result);
        res.setSuucessMsg("");
        return res;
    }
    @PostMapping(value = "/del")
    public ObjectRestResponse del(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        Integer id = (Integer) params.get("id");
        if(null==id){
            res.setErrorMsg("参数缺失");
            return res;
        }
        ruleEntityItemBiz.delInfoByEntityId(id);
        ruleEntityBiz.delEntityInfoById(id);
        res.setSuucessMsg("删除成功");
        return res;
    }
    @PostMapping(value = "/saveOrUpdate")
    public ObjectRestResponse saveOrUpdate(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String info = (String) params.get("entity");
        String entityItems = (String) params.get("entityItems");
        BaseRuleEntityInfo baseRuleEntityInfo = JSON.parseObject(info,BaseRuleEntityInfo.class);
        Integer id = baseRuleEntityInfo.getEntityId();
        if(null!= baseRuleEntityInfo.getEntityId()){
            ruleEntityItemBiz.delInfoByEntityId(baseRuleEntityInfo.getEntityId());
        }else{
            id = ruleEntityBiz.saveOrUpdate(baseRuleEntityInfo);
        }
        if(StringUtils.isNotBlank(entityItems)){
            List<BaseRuleEntityItemInfo> list = JSON.parseArray(entityItems,BaseRuleEntityItemInfo.class);
            for(BaseRuleEntityItemInfo item:list){
                item.setItemId(null);
                item.setEntityId(id);
                ruleEntityItemBiz.saveOrUpdate(item);
            }
        }

        res.setSuucessMsg("");
        return res;
    }
}
