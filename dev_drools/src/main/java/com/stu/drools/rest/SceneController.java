package com.stu.drools.rest;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.stu.drools.biz.RuleSceneBiz;
import com.stu.drools.common.ObjectRestResponse;
import com.stu.drools.model.RuleEntityInfo;
import com.stu.drools.model.RuleSceneInfo;
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
@RequestMapping(value = "/scene")
public class SceneController {

    @Autowired
    private RuleSceneBiz ruleSceneBiz;

    @PostMapping(value = "/page")
    public ObjectRestResponse page(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        PageInfo page = ruleSceneBiz.page(params);
        res.setData(page);
        res.setSuucessMsg("");
        return res;
    }

    @PostMapping(value = "/list")
    public ObjectRestResponse list(@RequestBody RuleSceneInfo ruleSceneInfo){
        ObjectRestResponse res = new ObjectRestResponse();
        List<RuleSceneInfo> list = ruleSceneBiz.findList(ruleSceneInfo);
        res.setData(list);
        res.setSuucessMsg("");
        return res;
    }
    @PostMapping(value = "/get")
    public ObjectRestResponse get(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String id = (String) params.get("id");
        if(StringUtils.isBlank(id)){
            res.setErrorMsg("参数缺失");
            return res;
        }

        RuleSceneInfo info = ruleSceneBiz.getInfoById(Long.valueOf(id));
        List<RuleEntityInfo> list = ruleSceneBiz.findBaseRuleEntityListByScene(info);
        Map<String,Object> result = new HashMap<>();
        result.put("info",info);
        result.put("list",list);
        res.setSuucessMsg("");
        res.setData(result);
        return res;
    }
    @PostMapping(value = "/del")
    public ObjectRestResponse del(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String id = (String) params.get("id");
        if(StringUtils.isBlank(id)){
            res.setErrorMsg("参数缺失");
            return res;
        }
        ruleSceneBiz.delInfoById(Long.valueOf(id));
        res.setSuucessMsg("删除成功");
        return res;
    }
    @PostMapping(value = "/saveOrUpdate")
    public ObjectRestResponse saveOrUpdate(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String info = (String) params.get("scene");
        String entitys = (String) params.get("entitys");
        RuleSceneInfo ruleSceneInfo = JSON.parseObject(info, RuleSceneInfo.class);
        Long id = ruleSceneInfo.getSceneId();
        if(ruleSceneInfo.getSceneId()==null){
            id = ruleSceneBiz.saveOrUpdate(ruleSceneInfo);
        }else{
            ruleSceneBiz.delRelInfoById(id);
        }


        res.setSuucessMsg("");
        return res;
    }
}
