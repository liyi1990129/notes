package com.drools.rest;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.drools.biz.*;
import com.drools.biz.sys.SysSequenceBiz;
import com.drools.common.DroolsConstants;
import com.drools.common.ObjectRestResponse;
import com.drools.model.*;
import com.drools.vo.RulePropertyRelInfoVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/* *
 * 规则--控制器 
 * @author ly
 * @modifyTime 2020/11/10 16:49:00
 */
@RestController
@RequestMapping(value = "/rule")
public class RuleController {

    @Autowired
    private RuleInfoBiz ruleInfoBiz;
    @Autowired
    private RuleActionInfoBiz ruleActionInfoBiz;
    @Autowired
    private RuleSceneBiz ruleSceneBiz;
    @Autowired
    private RuleConditionBiz ruleConditionBiz;
    @Autowired
    private SysSequenceBiz sysSequenceBiz;

    /* *
     * 分页查询 
     * @author ly
     * @modifyTime 2020/11/10 16:49:00
     */
    @PostMapping(value = "/page")
    public ObjectRestResponse list(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        PageInfo page = ruleInfoBiz.page(params);
        res.setData(page);
        res.setSuucessMsg("");
        return res;
    }
    
    /* *
     * 根据ID获取规则信息 
     * @author ly
     * @modifyTime 2020/11/10 16:49:00
     */
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
        List<RulePropertyRelInfoVo> relList = ruleInfoBiz.findRulePropertyListByRuleCode(info.getRuleCode());
        Map<String,Object> result = new HashMap<>();
        result.put("info",info);
        result.put("relList",relList);

        res.setSuucessMsg("");
        res.setData(result);
        return res;
    }
    
    /* *
     * 删除规则信息 
     * @author ly
     * @modifyTime 2020/11/10 16:49:00
     */
    @PostMapping(value = "/del")
    public ObjectRestResponse del(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String id = (String) params.get("id");
        if(StringUtils.isBlank(id)){
            res.setErrorMsg("参数缺失");
            return res;
        }
        RuleInfo ruleInfo = this.ruleInfoBiz.getInfoById(Long.valueOf(id));
        ruleConditionBiz.delByRuleCode(ruleInfo.getRuleCode());
        this.ruleInfoBiz.delRelByRuleCode(ruleInfo.getRuleCode());
        ruleInfoBiz.delInfoById(Long.valueOf(id));
        res.setSuucessMsg("删除成功");
        return res;
    }
    
    /* *
     * 保存 
     * @author ly
     * @modifyTime 2020/11/10 16:49:00
     */
    @PostMapping(value = "/saveOrUpdate")
    public ObjectRestResponse saveOrUpdate(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String info = (String) params.get("rule");
        String relInfo = (String) params.get("relList");
        RuleInfo ruleInfo = JSON.parseObject(info, RuleInfo.class);

        Long id = ruleInfo.getRuleId();

        if(ruleInfo.getRuleId()!=null){
            this.ruleInfoBiz.delRelByRuleCode(ruleInfo.getRuleCode());
        }else{
            String code = sysSequenceBiz.gainNoByType(DroolsConstants.SeqName.RULE_CODE);
            ruleInfo.setRuleCode(code);
        }
        id = ruleInfoBiz.saveOrUpdate(ruleInfo);

        List<RulePropertyRelInfo> list = JSON.parseArray(relInfo,RulePropertyRelInfo.class);
        if(!CollectionUtils.isEmpty(list)){
            for (RulePropertyRelInfo rulePropertyRelInfo : list) {
                rulePropertyRelInfo.setRuleCode(ruleInfo.getRuleCode());
                this.ruleInfoBiz.saveRel(rulePropertyRelInfo);
            }
        }
        res.setSuucessMsg("");
        return res;
    }

    /* *
     * 规则动作 
     * @author ly
     * @modifyTime 2020/11/10 16:50:00
     */
    @PostMapping(value = "/addAction")
    public ObjectRestResponse addAction(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String id = (String) params.get("id");
        if(StringUtils.isBlank(id)){
            res.setErrorMsg("参数缺失");
            return res;
        }
        RuleInfo ruleInfo = this.ruleInfoBiz.getInfoById(Long.valueOf(id));

        //所有有效的动作
        RuleActionInfo info = new RuleActionInfo();
        info.setRuleCode(ruleInfo.getRuleCode());
        List<RuleActionInfo> allList = ruleActionInfoBiz.list(info);

        //规则所属场景的实体

        RuleSceneInfo sceneInfo = new RuleSceneInfo();
        sceneInfo.setSceneIdentify(ruleInfo.getSceneIdentify());
        List<RuleEntityInfo> entityInfos = ruleSceneBiz.findBaseRuleEntityListByScene(sceneInfo);

        Map<String,Object> result = new HashMap<>();
        result.put("actionList",allList);
        result.put("ruleInfo",ruleInfo);
        result.put("entityInfos",entityInfos);

        res.setSuucessMsg("查询成功");
        res.setData(result);
        return res;
    }

    /* *
     * 规则条件 
     * @author ly
     * @modifyTime 2020/11/10 16:51:00
     */
    @PostMapping(value = "/addCondition")
    public ObjectRestResponse addCondition(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String id = (String) params.get("id");
        if(StringUtils.isBlank(id)){
            res.setErrorMsg("参数缺失");
            return res;
        }

        RuleInfo ruleInfo = this.ruleInfoBiz.getInfoById(Long.valueOf(id));

        //规则关联的动作
        List<RuleConditionInfo> conditionInfos = ruleConditionBiz.findRuleConditionInfoByRuleCode(ruleInfo.getRuleCode());

        //规则所属场景的实体
        RuleSceneInfo sceneInfo = new RuleSceneInfo();
        sceneInfo.setSceneIdentify(ruleInfo.getSceneIdentify());
        List<RuleEntityInfo> entityInfos = ruleSceneBiz.findBaseRuleEntityListByScene(sceneInfo);

        Map<String,Object> result = new HashMap<>();
        result.put("ruleInfo",ruleInfo);
        result.put("relList",conditionInfos);
        result.put("entityInfos",entityInfos);

        res.setSuucessMsg("查询成功");
        res.setData(result);
        return res;
    }
    
    /* *
     * 保存规则条件 
     * @author ly
     * @modifyTime 2020/11/10 16:51:00
     */
    @PostMapping(value = "/saveCondition")
    public ObjectRestResponse saveCondition(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String id = (String) params.get("id");
        String relInfo = (String) params.get("relInfo");
        String delInfo = (String) params.get("delInfo");

        if(StringUtils.isBlank(id)){
            res.setErrorMsg("参数缺失");
            return res;
        }

        if(StringUtils.isNotBlank(delInfo)){
            //被删除的规则关联的条件
            List<RuleConditionInfo> delList = JSON.parseArray(delInfo,RuleConditionInfo.class);
            if(!CollectionUtils.isEmpty(delList)){
                for (RuleConditionInfo ruleConditionInfo : delList) {
                    //删除规则条件
                    this.ruleConditionBiz.delById(ruleConditionInfo.getConditionId());
                }
            }
        }

        //规则关联的条件
        List<RuleConditionInfo> relList = JSON.parseArray(relInfo,RuleConditionInfo.class);
        if(!CollectionUtils.isEmpty(relList)){
            for (RuleConditionInfo ruleConditionInfo : relList) {
                this.ruleConditionBiz.saveOrUpdate(ruleConditionInfo);
            }
        }
        return res;
    }
    
    /* *
     * 保存规则动作 
     * @author ly
     * @modifyTime 2020/11/10 17:39:00
     */
    @PostMapping(value = "/saveAction")
    public ObjectRestResponse saveAction(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String id = (String) params.get("id");
        String relInfo = (String) params.get("relInfo");
        String delInfo = (String) params.get("delInfo");

        if(StringUtils.isBlank(id)){
            res.setErrorMsg("参数缺失");
            return res;
        }

        if(StringUtils.isNotBlank(delInfo)){
            //被删除的规则关联的动作
            List<RuleActionInfo> delList = JSON.parseArray(delInfo,RuleActionInfo.class);
            if(!CollectionUtils.isEmpty(delList)){
                for (RuleActionInfo ruleActionInfo : delList) {
                    //删除规则动作
                    this.ruleActionInfoBiz.delInfo(ruleActionInfo.getActionId());
                }
            }
        }


        //规则关联的动作
        List<RuleActionInfo> relList = JSON.parseArray(relInfo,RuleActionInfo.class);
        if(!CollectionUtils.isEmpty(relList)){
            for (RuleActionInfo ruleActionInfo : relList) {
                this.ruleActionInfoBiz.saveOrUpdate(ruleActionInfo);
            }
        }
        return res;
    }


}
