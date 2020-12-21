package com.drools.rest;

import com.github.pagehelper.PageInfo;
import com.drools.biz.RulePropertyInfoBiz;
import com.drools.common.ObjectRestResponse;
import com.drools.model.RulePropertyInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/* *
 * drools规则属性--控制器 
 * @author ly
 * @modifyTime 2020/11/10 17:39:00
 */
@RestController
@RequestMapping(value = "/property")
public class PropertyController {

    @Autowired
    private RulePropertyInfoBiz rulePropertyInfoBiz;

    @PostMapping(value = "/page")
    public ObjectRestResponse page(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        PageInfo page = rulePropertyInfoBiz.page(params);
        res.setData(page);
        res.setSuucessMsg("");
        return res;
    }

    @PostMapping(value = "/list")
    public ObjectRestResponse list(@RequestBody RulePropertyInfo rulePropertyInfo){
        ObjectRestResponse res = new ObjectRestResponse();
        List<RulePropertyInfo>  list = rulePropertyInfoBiz.findList(rulePropertyInfo);
        res.setData(list);
        res.setSuucessMsg("");
        return res;
    }

}
