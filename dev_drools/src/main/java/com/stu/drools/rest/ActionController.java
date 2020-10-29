package com.stu.drools.rest;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.stu.drools.biz.RuleActionInfoBiz;
import com.stu.drools.biz.RuleActionParamBiz;
import com.stu.drools.biz.RuleEntityBiz;
import com.stu.drools.biz.RuleEntityItemBiz;
import com.stu.drools.common.ObjectRestResponse;
import com.stu.drools.model.RuleActionInfo;
import com.stu.drools.model.RuleActionParamInfo;
import com.stu.drools.model.RuleEntityInfo;
import com.stu.drools.model.RuleEntityItemInfo;
import com.stu.drools.util.ScanningFileUtil;
import com.stu.drools.vo.ClassVo;
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
@RequestMapping(value = "/action")
public class ActionController {

    @Autowired
    private RuleActionInfoBiz ruleActionInfoBiz;
    @Autowired
    private RuleActionParamBiz ruleActionParamBiz;

    @PostMapping(value = "/page")
    public ObjectRestResponse page(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        PageInfo page = ruleActionInfoBiz.page(params);
        res.setData(page);
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
        RuleActionInfo info = ruleActionInfoBiz.findInfoById(Long.valueOf(id));
        List<RuleActionParamInfo> list = ruleActionParamBiz.listByActionId(Long.valueOf(id));

        Map<String,Object> result = new HashMap<>();
        result.put("action",info);
        result.put("actionItems",list);
        res.setData(result);
        res.setSuucessMsg("");
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
        res.setSuucessMsg("删除成功");
        return res;
    }

    @PostMapping(value = "/saveOrUpdate")
    public ObjectRestResponse saveOrUpdate(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String info = (String) params.get("action");
        String actionItems = (String) params.get("actionItems");
        RuleActionInfo ruleActionInfo = JSON.parseObject(info, RuleActionInfo.class);
        Long id = ruleActionInfo.getActionId();
        if(null!= id){
            ruleActionParamBiz.delByActionId(id);
        }

        id = ruleActionInfoBiz.saveOrUpdate(ruleActionInfo);
        if(StringUtils.isNotBlank(actionItems)){
            List<RuleActionParamInfo> list = JSON.parseArray(actionItems, RuleActionParamInfo.class);
            for(RuleActionParamInfo item:list){
                item.setActionId(id);
                ruleActionParamBiz.saveOrUpdate(item);
            }
        }

        res.setSuucessMsg("");
        return res;
    }



}
