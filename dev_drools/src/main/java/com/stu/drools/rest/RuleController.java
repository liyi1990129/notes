package com.stu.drools.rest;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.stu.drools.biz.RuleInfoBiz;
import com.stu.drools.biz.RuleSceneBiz;
import com.stu.drools.common.ObjectRestResponse;
import com.stu.drools.model.RuleEntityInfo;
import com.stu.drools.model.RuleInfo;
import com.stu.drools.model.RulePropertyRelInfo;
import com.stu.drools.model.RuleSceneInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rule")
public class RuleController {

    @Autowired
    private RuleInfoBiz ruleInfoBiz;

    @PostMapping(value = "/page")
    public ObjectRestResponse list(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        PageInfo page = ruleInfoBiz.page(params);
        res.setData(page);
        res.setSuucessMsg("");
        return res;
    }
    @PostMapping(value = "/get")
    public ObjectRestResponse get(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        Long id = (Long) params.get("id");
        if(null==id){
            res.setErrorMsg("参数缺失");
            return res;
        }

        //规则信息
        RuleInfo info = ruleInfoBiz.getInfoById(id);

        //规则关联的属性信息
        List<RulePropertyRelInfo> relList = ruleInfoBiz.findRulePropertyListByRuleId(id);
        Map<String,Object> result = new HashMap<>();
        result.put("info",info);
        result.put("relList",relList);

        res.setSuucessMsg("");
        res.setData(result);
        return res;
    }
    @PostMapping(value = "/del")
    public ObjectRestResponse del(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        Long id = (Long) params.get("id");
        if(null==id){
            res.setErrorMsg("参数缺失");
            return res;
        }
        ruleInfoBiz.delInfoById(id);
        res.setSuucessMsg("删除成功");
        return res;
    }
    @PostMapping(value = "/saveOrUpdate")
    public ObjectRestResponse saveOrUpdate(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String info = (String) params.get("rule");
        String relInfo = (String) params.get("relList");
        RuleInfo ruleInfo = JSON.parseObject(info, RuleInfo.class);

        List<RulePropertyRelInfo> rulePropertyRelInfos = JSON.parseArray(relInfo,RulePropertyRelInfo.class);
        Long id = ruleInfo.getRuleId();

        if(ruleInfo.getRuleId()==null){
            id = ruleInfoBiz.saveOrUpdate(ruleInfo);
        }else{
            this.ruleInfoBiz.delRelByRuleId(id);

        }


        res.setSuucessMsg("");
        return res;
    }
}
