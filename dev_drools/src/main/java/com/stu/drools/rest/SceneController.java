package com.stu.drools.rest;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.stu.drools.biz.DroolsBiz;
import com.stu.drools.biz.RuleEntityItemBiz;
import com.stu.drools.biz.RuleSceneBiz;
import com.stu.drools.biz.RuleSceneEntityRelBiz;
import com.stu.drools.common.ObjectRestResponse;
import com.stu.drools.model.RuleEntityInfo;
import com.stu.drools.model.RuleEntityItemInfo;
import com.stu.drools.model.RuleSceneEntityRelInfo;
import com.stu.drools.model.RuleSceneInfo;
import com.stu.drools.model.fact.RuleExecutionObject;
import com.stu.drools.model.fact.RuleExecutionResult;
import com.stu.drools.vo.RuleEntityInfoVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/scene")
public class SceneController {

    @Autowired
    private RuleSceneBiz ruleSceneBiz;
    @Autowired
    private DroolsBiz droolsBiz;
    @Autowired
    private RuleSceneEntityRelBiz ruleSceneEntityRelBiz;
    @Autowired
    private RuleEntityItemBiz ruleEntityItemBiz;

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

        List<RuleEntityInfoVo> vos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)){
            for (RuleEntityInfo ruleEntityInfo : list) {
                RuleEntityInfoVo vo = new RuleEntityInfoVo();
                vo.setEntityId(ruleEntityInfo.getEntityId());
                vo.setEntityName(ruleEntityInfo.getEntityName());
                vo.setEntityDesc(ruleEntityInfo.getEntityDesc());
                vo.setEntityIdentify(ruleEntityInfo.getEntityIdentify());
                vo.setPkgName(ruleEntityInfo.getPkgName());

                Map<String,Object> query = new HashMap<>();
                query.put("entityId",ruleEntityInfo.getEntityId());
                query.put("isEffect",1);
                List<RuleEntityItemInfo> itemInfos =  ruleEntityItemBiz.listByParams(query);
                vo.setItemInfos(itemInfos);

                vos.add(vo);
            }
        }

        Map<String,Object> result = new HashMap<>();
        result.put("info",info);
        result.put("list",list);
        result.put("entitys",vos);
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
        ruleSceneBiz.delRelInfoById(Long.valueOf(id));
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
        if(ruleSceneInfo.getSceneId()!=null){
            ruleSceneBiz.delRelInfoById(id);
        }
        id = ruleSceneBiz.saveOrUpdate(ruleSceneInfo);

        List<RuleSceneEntityRelInfo> infos = JSON.parseArray(entitys,RuleSceneEntityRelInfo.class);
        if(!CollectionUtils.isEmpty(infos)){
            for (RuleSceneEntityRelInfo ruleSceneEntityRelInfo : infos) {
                ruleSceneEntityRelInfo.setSceneId(id);
                ruleSceneEntityRelBiz.saveInfo(ruleSceneEntityRelInfo);
            }
        }

        res.setSuucessMsg("");
        return res;
    }
    @PostMapping(value = "/showTemplate")
    public ObjectRestResponse showTemplate(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String info = (String) params.get("scene");
        String result = droolsBiz.getTemplate(info);

        res.setData(result);
        res.setSuucessMsg("");
        return res;
    }

    //条件
    @PostMapping(value = "/testRule")
    public ObjectRestResponse testRule(@RequestBody Map<String,Object> params) throws Exception {
        ObjectRestResponse res = new ObjectRestResponse();
        String id = (String) params.get("id");
        String list = (String) params.get("entitys");
        if(StringUtils.isBlank(id)){
            res.setErrorMsg("场景参数缺失");
            return res;
        }
        RuleSceneInfo info = ruleSceneBiz.getInfoById(Long.valueOf(id));

        RuleExecutionObject object = new RuleExecutionObject();
        List<RuleEntityInfoVo> vos = JSON.parseArray(list,RuleEntityInfoVo.class);
        if(!CollectionUtils.isEmpty(vos)){
            for (RuleEntityInfoVo vo : vos) {
                String pkgName = vo.getPkgName();
                Class<?> stuClass = Class.forName(pkgName);
                //获取一个对象
                Object obj = stuClass.getConstructor().newInstance();

                for (RuleEntityItemInfo itemInfo : vo.getItemInfos()) {
                    Field f = stuClass.getDeclaredField(itemInfo.getItemIdentify());
                    if(f!=null && StringUtils.isNotBlank(itemInfo.getItemValue())){
                        f.setAccessible(true);//暴力反射，解除私有限定
                       if("Integer".equals(f.getType().getSimpleName())){
                           f.set(obj, Integer.parseInt(itemInfo.getItemValue()));
                       }
                    }
                }
//                for (Object key : paramMap.keySet()) {
//                    Field f = stuClass.getDeclaredField((String) key);
//                    if(f!=null){
//                        Object v = paramMap.get(key);
//                        f.setAccessible(true);//暴力反射，解除私有限定
//                        f.set(obj, v);
//                    }
//                }

                object.addFactObject(obj);
            }
        }




        //全局对象
        RuleExecutionResult result = new RuleExecutionResult();
        object.setGlobal("_result",result);
        String template = droolsBiz.getTemplate(info.getSceneIdentify());
        RuleExecutionObject resultData = droolsBiz.excute(object, info.getSceneIdentify(),template);
        res.setSuucessMsg("resultData");
        Map<String,Object> map = new HashMap<>();
        map.put("result",resultData);
        map.put("template",template);
        res.setData(map);
        return res;
    }
}
