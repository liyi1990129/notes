package com.stu.drools.rest;

import com.github.pagehelper.PageInfo;
import com.stu.drools.biz.RulePropertyInfoBiz;
import com.stu.drools.common.ObjectRestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

}
