package com.drools.rest;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.drools.biz.*;
import com.drools.biz.sys.SysDictDetailBiz;
import com.drools.common.DroolsConstants;
import com.drools.common.ObjectRestResponse;
import com.drools.model.*;
import com.drools.model.fact.RuleExecutionObject;
import com.drools.model.fact.RuleExecutionResult;
import com.drools.model.sys.SysDictDetail;
import com.drools.util.DateUtil;
import com.drools.util.FiledUtil;
import com.drools.vo.ClassVo;
import com.drools.vo.RuleEntityInfoVo;
import com.drools.vo.RuleEntityItemInfoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.*;

/* *
 * 场景-控制器 
 * @author ly
 * @modifyTime 2020/11/10 16:52:00
 */
@Slf4j
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
    @Autowired
    private SysDictDetailBiz sysDictDetailBiz;
    @Autowired
    private PublishBiz publishBiz;
    @Autowired
    private RuleInfoBiz ruleInfoBiz;
    /* *
     * 分页查询 
     * @author ly
     * @modifyTime 2020/11/10 16:52:00
     */
    @PostMapping(value = "/page")
    public ObjectRestResponse page(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        PageInfo page = ruleSceneBiz.page(params);
        res.setData(page);
        res.setSuucessMsg("");
        return res;
    }

    /* *
     * 列表查询 
     * @author ly
     * @modifyTime 2020/11/10 16:52:00
     */
    @PostMapping(value = "/list")
    public ObjectRestResponse list(@RequestBody RuleSceneInfo ruleSceneInfo){
        ObjectRestResponse res = new ObjectRestResponse();
        List<RuleSceneInfo> list = ruleSceneBiz.findList(ruleSceneInfo);
        res.setData(list);
        res.setSuucessMsg("");
        return res;
    }
    
    /* *
     * 获取场景信息 
     * @author ly
     * @modifyTime 2020/11/10 16:52:00
     */
    @PostMapping(value = "/get")
    public ObjectRestResponse get(@RequestBody Map<String,Object> params) throws Exception {
        ObjectRestResponse res = new ObjectRestResponse();
        String id = (String) params.get("id");
        if(StringUtils.isBlank(id)){
            res.setErrorMsg("参数缺失");
            return res;
        }

        //场景
        RuleSceneInfo info = ruleSceneBiz.getInfoById(Long.valueOf(id));
        //场景关联的实体类
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
//                String jsonStr = FiledUtil.getJsonStr(vo.getPkgName());
//                vo.setRemark(jsonStr);

                Map<String,Object> query = new HashMap<>();
                query.put("entityId",ruleEntityInfo.getEntityId());
                query.put("isEffect", DroolsConstants.IsEffect.YES);
                List<RuleEntityItemInfo> itemInfos =  ruleEntityItemBiz.listByParams(query);

                List<RuleEntityItemInfoVo> itemVos = new ArrayList<>();
                if(!CollectionUtils.isEmpty(itemInfos)){
                    //获取枚举类
                    for (RuleEntityItemInfo itemInfo : itemInfos) {
                        RuleEntityItemInfoVo itemVo = new RuleEntityItemInfoVo();
                        BeanUtils.copyProperties(itemInfo,itemVo);
                        if(StringUtils.isNotBlank(itemInfo.getEnumName())){
                            List<SysDictDetail> dictDetails = this.sysDictDetailBiz.findByMainCode(itemInfo.getEnumName());
                            itemVo.setEnumList(dictDetails);
                        }
                        if("List".equals(itemVo.getItemType())){
//                            String jsonStr = FiledUtil.getJsonStr(itemVo.getItemCls());
//                            itemVo.setRemark(jsonStr);
                        }
                        itemVos.add(itemVo);
                    }
                }
                vo.setItemInfos(itemVos);

                vos.add(vo);
            }
        }
        RuleSceneInfo ruleSceneInfo = new RuleSceneInfo();
        ruleSceneInfo.setSceneId(Long.parseLong(id));
        List<RuleInfo> ruleInfos = ruleInfoBiz.findBaseRuleListByScene(ruleSceneInfo);
        Map<String,Object> result = new HashMap<>();
        result.put("info",info);
        result.put("list",list);
        result.put("entitys",vos);
        result.put("ruleInfos",ruleInfos);
        res.setSuucessMsg("");
        res.setData(result);
        return res;
    }
    
    /* *
     * 删除 
     * @author ly
     * @modifyTime 2020/11/10 16:53:00
     */
    @PostMapping(value = "/del")
    public ObjectRestResponse del(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String id = (String) params.get("id");
        if(StringUtils.isBlank(id)){
            res.setErrorMsg("参数缺失");
            return res;
        }
        RuleSceneInfo sceneInfo = ruleSceneBiz.getInfoById(Long.valueOf(id));
        ruleSceneBiz.delRelInfoBySceneIdentify(sceneInfo.getSceneIdentify());
        ruleSceneBiz.delInfoById(Long.valueOf(id));
        res.setSuucessMsg("删除成功");
        return res;
    }
    
    /* *
     * 保存 
     * @author ly
     * @modifyTime 2020/11/10 16:53:00
     */
    @PostMapping(value = "/saveOrUpdate")
    public ObjectRestResponse saveOrUpdate(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String info = (String) params.get("scene");
        String entitys = (String) params.get("entitys");
        RuleSceneInfo ruleSceneInfo = JSON.parseObject(info, RuleSceneInfo.class);
        Long id = ruleSceneInfo.getSceneId();


        if(ruleSceneInfo.getSceneId()!=null){
            ruleSceneBiz.delRelInfoBySceneIdentify(ruleSceneInfo.getSceneIdentify());
        }else{
            List<RuleSceneInfo> list = ruleSceneBiz.list(ruleSceneInfo.getSceneIdentify());
            if(list.size()>0){
                res.setErrorMsg("场景标识已存在");
                return res;
            }
        }
        id = ruleSceneBiz.saveOrUpdate(ruleSceneInfo);

        List<RuleSceneEntityRelInfo> infos = JSON.parseArray(entitys,RuleSceneEntityRelInfo.class);
        if(!CollectionUtils.isEmpty(infos)){
            for (RuleSceneEntityRelInfo ruleSceneEntityRelInfo : infos) {
                ruleSceneEntityRelInfo.setSceneIdentify(ruleSceneInfo.getSceneIdentify());
                ruleSceneEntityRelBiz.saveInfo(ruleSceneEntityRelInfo);
            }
        }

        res.setSuucessMsg("");
        return res;
    }
    
    /* *
     * 预览 
     * @author ly
     * @modifyTime 2020/11/10 16:53:00
     */
    @PostMapping(value = "/showTemplate")
    public ObjectRestResponse showTemplate(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String info = (String) params.get("scene");
        String result = droolsBiz.getTemplate(info);

        res.setData(result);
        res.setSuucessMsg("");
        return res;
    }

    /* *
     * 预览
     * @author ly
     * @modifyTime 2020/11/10 16:53:00
     */
    @PostMapping(value = "/showScript")
    public ObjectRestResponse showScript(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String info = (String) params.get("scene");
        String result = droolsBiz.getTemplate(info);

        res.setData(result);
        res.setSuucessMsg("");
        return res;
    }

    /* *
     * 发布
     * @author ly
     * @modifyTime 2020/11/10 16:53:00
     */
    @PostMapping(value = "/publishScene")
    public ObjectRestResponse publishScene(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String id = (String) params.get("id");
        try{
            publishBiz.publishScene(Long.valueOf(id));
            res.setSuucessMsg("发布成功");
        }catch (Exception e){
            log.error("发布异常{}",e);
            res.setErrorMsg("发布异常"+e.getMessage());
        }

        return res;
    }

    /* *
     * 测试接口 
     * @author ly
     * @modifyTime 2020/11/10 17:54:00
     */
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
                if(vo.getEntityIdentify().equals("Map")){
                    Map map = new HashMap();
                    for (RuleEntityItemInfoVo itemInfo : vo.getItemInfos()) {
                        map.put(itemInfo.getItemIdentify(),itemInfo.getItemValue());
                    }
                    object.addFactObject(map);
                }else{
                    String pkgName = vo.getPkgName();
                    Class<?> stuClass = Class.forName(pkgName);
                    //获取一个对象
                    Object obj = stuClass.getConstructor().newInstance();

                    for (RuleEntityItemInfoVo itemInfo : vo.getItemInfos()) {
                        Field f = stuClass.getDeclaredField(itemInfo.getItemIdentify());
                        if(f!=null && StringUtils.isNotBlank(itemInfo.getItemValue())){
                            f.setAccessible(true);//暴力反射，解除私有限定
                            if("Integer".equals(f.getType().getSimpleName()) || "int".equals(f.getType().getSimpleName())){
                                f.set(obj, Integer.parseInt(itemInfo.getItemValue()));
                            }else if("List".equals(f.getType().getSimpleName())){

                            }else if("Date".equals(f.getType().getSimpleName())){
                                Date date = DateUtil.parse4yMd(itemInfo.getItemValue());
                                f.set(obj, date);
                            }else{
                                f.set(obj, itemInfo.getItemValue());
                            }
                        }
                    }
                    object.addFactObject(obj);
                }

//                for (Object key : paramMap.keySet()) {
//                    Field f = stuClass.getDeclaredField((String) key);
//                    if(f!=null){
//                        Object v = paramMap.get(key);
//                        f.setAccessible(true);//暴力反射，解除私有限定
//                        f.set(obj, v);
//                    }
//                }


            }
        }




        try {
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
        }catch (Exception e){
            log.error("异常:{}",e);
            res.setErrorMsg(e.getMessage());
        }

        return res;
    }

    /* *
     * 保存
     * @author ly
     * @modifyTime 2020/11/10 16:53:00
     */
    @PostMapping(value = "/updateFlow")
    public ObjectRestResponse updateFlow(@RequestBody Map<String, Object> params) {
        ObjectRestResponse res = new ObjectRestResponse();
        String id = (String) params.get("id");
        Object flow =  params.get("flow");
        RuleSceneInfo ruleSceneInfo = new RuleSceneInfo();
        ruleSceneInfo.setSceneId(Long.parseLong(id));
        //ruleSceneInfo.setSceneFlow(JSON.toJSONString(flow));
        ruleSceneBiz.saveOrUpdate(ruleSceneInfo);
        res.setSuucessMsg("");
        return res;
    }
}
