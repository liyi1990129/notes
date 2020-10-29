package com.stu.drools.rest;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.stu.drools.biz.*;
import com.stu.drools.common.ObjectRestResponse;
import com.stu.drools.model.*;
import com.stu.drools.vo.RuleActionRuleRelInfoVo;
import com.stu.drools.vo.RulePropertyRelInfoVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/rule")
public class RuleController {

    @Autowired
    private RuleInfoBiz ruleInfoBiz;
    @Autowired
    private RuleActionInfoBiz ruleActionInfoBiz;
    @Autowired
    private RuleActionRuleRelInfoBiz ruleActionRuleRelInfoBiz;
    @Autowired
    private RuleActionParamValueBiz ruleActionParamValueBiz;
    @Autowired
    private RuleActionParamBiz ruleActionParamBiz;
    @Autowired
    private RuleSceneBiz ruleSceneBiz;
    @Autowired
    private RuleConditionBiz ruleConditionBiz;

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
        String id = (String) params.get("id");
        if(null==id){
            res.setErrorMsg("参数缺失");
            return res;
        }

        //规则信息
        RuleInfo info = ruleInfoBiz.getInfoById(Long.valueOf(id));

        //规则关联的属性信息
        List<RulePropertyRelInfoVo> relList = ruleInfoBiz.findRulePropertyListByRuleId(Long.valueOf(id));
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
        String id = (String) params.get("id");
        if(StringUtils.isBlank(id)){
            res.setErrorMsg("参数缺失");
            return res;
        }
        ruleInfoBiz.delInfoById(Long.valueOf(id));
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
            ruleInfoBiz.saveOrUpdate(ruleInfo);
        }

        for (RulePropertyRelInfo rulePropertyRelInfo : rulePropertyRelInfos) {
            rulePropertyRelInfo.setRuleId(id);
            this.ruleInfoBiz.saveRel(rulePropertyRelInfo);
        }


        res.setSuucessMsg("");
        return res;
    }

    @PostMapping(value = "/addAction")
    public ObjectRestResponse addAction(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String id = (String) params.get("id");
        if(StringUtils.isBlank(id)){
            res.setErrorMsg("参数缺失");
            return res;
        }

        //规则关联的动作
        List<RuleActionRuleRelInfo> relList = ruleActionRuleRelInfoBiz.listByRuleId(Long.valueOf(id));
        List<RuleActionRuleRelInfoVo> paramValueInfoList = new ArrayList<>();

        if(!CollectionUtils.isEmpty(relList)){
            for (RuleActionRuleRelInfo relInfo : relList) {
                RuleActionRuleRelInfoVo vo = new RuleActionRuleRelInfoVo();
                vo.setActionId(relInfo.getActionId());
                vo.setRuleId(relInfo.getRuleId());

                RuleActionInfo actionInfo = this.ruleActionInfoBiz.findInfoById(relInfo.getActionId());
                vo.setActionName(actionInfo.getActionName());

                List<RuleActionParamValueInfo> list = ruleActionParamValueBiz.findRuleParamValueByRelId(relInfo.getRuleActionRelId());
                vo.setRelList(list);

                List<RuleActionParamInfo> paramInfoList = this.ruleActionParamBiz.listByActionId(relInfo.getActionId());
                vo.setParamInfoList(paramInfoList);
                paramValueInfoList.add(vo);
            }
        }
        //所有有效的动作
        RuleActionInfo info = new RuleActionInfo();
        info.setIsEffect(1);
        List<RuleActionInfo> allList = ruleActionInfoBiz.list(info);

        //规则所属场景的实体
        RuleInfo ruleInfo = this.ruleInfoBiz.getInfoById(Long.valueOf(id));
        RuleSceneInfo sceneInfo = new RuleSceneInfo();
        sceneInfo.setSceneId(ruleInfo.getSceneId());
        List<RuleEntityInfo> entityInfos = ruleSceneBiz.findBaseRuleEntityListByScene(sceneInfo);

        Map<String,Object> result = new HashMap<>();
        result.put("relList",relList);
        result.put("paramValueInfoList",paramValueInfoList);
        result.put("actionList",allList);
        result.put("entityInfos",entityInfos);

        res.setSuucessMsg("删除成功");
        res.setData(result);
        return res;
    }

    //条件
    @PostMapping(value = "/addCondition")
    public ObjectRestResponse addCondition(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String id = (String) params.get("id");
        if(StringUtils.isBlank(id)){
            res.setErrorMsg("参数缺失");
            return res;
        }

        //规则关联的动作
        List<RuleConditionInfo> conditionInfos = ruleConditionBiz.findRuleConditionInfoByRuleId(Long.valueOf(id));

        //规则所属场景的实体
        RuleInfo ruleInfo = this.ruleInfoBiz.getInfoById(Long.valueOf(id));
        RuleSceneInfo sceneInfo = new RuleSceneInfo();
        sceneInfo.setSceneId(ruleInfo.getSceneId());
        List<RuleEntityInfo> entityInfos = ruleSceneBiz.findBaseRuleEntityListByScene(sceneInfo);

        Map<String,Object> result = new HashMap<>();
        result.put("relList",conditionInfos);
        result.put("entityInfos",entityInfos);

        res.setSuucessMsg("查询成功");
        res.setData(result);
        return res;
    }
    @PostMapping(value = "/saveCondition")
    public ObjectRestResponse saveCondition(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String id = (String) params.get("id");
        String relInfo = (String) params.get("relInfo");
        if(StringUtils.isBlank(id)){
            res.setErrorMsg("参数缺失");
            return res;
        }

        this.ruleConditionBiz.delByRuleId(Long.valueOf(id));

        //规则关联的动作
        List<RuleConditionInfo> relList = JSON.parseArray(relInfo,RuleConditionInfo.class);
        if(!CollectionUtils.isEmpty(relList)){
            for (RuleConditionInfo ruleConditionInfo : relList) {
                this.ruleConditionBiz.saveOrUpdate(ruleConditionInfo);
            }
        }
        return res;
    }
    @PostMapping(value = "/saveAction")
    public ObjectRestResponse saveAction(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String id = (String) params.get("id");
        String relInfo = (String) params.get("relInfo");
        if(StringUtils.isBlank(id)){
            res.setErrorMsg("参数缺失");
            return res;
        }

        this.ruleActionRuleRelInfoBiz.delByRuleId(Long.valueOf(id));

        //规则关联的动作
        List<RuleActionRuleRelInfoVo> relList = JSON.parseArray(relInfo,RuleActionRuleRelInfoVo.class);
        if(!CollectionUtils.isEmpty(relList)){
            for (RuleActionRuleRelInfoVo ruleActionRuleRelInfoVo : relList) {
                this.ruleActionRuleRelInfoBiz.saveOrUpdate(ruleActionRuleRelInfoVo);
            }
        }
        return res;
    }
}
