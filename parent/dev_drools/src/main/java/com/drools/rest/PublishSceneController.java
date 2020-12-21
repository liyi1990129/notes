package com.drools.rest;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.drools.biz.*;
import com.drools.biz.publish.PublishRuleSceneBiz;
import com.drools.biz.sys.SysDictDetailBiz;
import com.drools.common.ObjectRestResponse;
import com.drools.model.RuleEntityInfo;
import com.drools.model.RuleEntityItemInfo;
import com.drools.model.RuleSceneEntityRelInfo;
import com.drools.model.RuleSceneInfo;
import com.drools.model.fact.RuleExecutionObject;
import com.drools.model.fact.RuleExecutionResult;
import com.drools.model.publish.PublishRuleSceneInfo;
import com.drools.model.sys.SysDictDetail;
import com.drools.service.DroolsActionService;
import com.drools.vo.PublishRuleSceneInfoVo;
import com.drools.vo.RuleEntityInfoVo;
import com.drools.vo.RuleEntityItemInfoVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
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

/* *
 * 已发布场景-控制器
 * @author ly
 * @modifyTime 2020/11/10 16:52:00
 */
@RestController
@RequestMapping(value = "/publish")
public class PublishSceneController {

    @Autowired
    private PublishRuleSceneBiz publishRuleSceneBiz;
    @Autowired
    private DroolsActionService droolsActionService;
    @Autowired
    private PublishBiz publishBiz;
    @Autowired
    private CompareSceneBiz compareSceneBiz;

    /* *
     * 分页查询 
     * @author ly
     * @modifyTime 2020/11/10 16:52:00
     */
    @PostMapping(value = "/page")
    public ObjectRestResponse page(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        PageInfo page = publishRuleSceneBiz.page(params);
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
    public ObjectRestResponse list(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String secne = (String) params.get("secne");
        if(StringUtils.isBlank(secne)){
            res.setErrorMsg("参数缺失");
            return res;
        }
        List<PublishRuleSceneInfo> list = publishRuleSceneBiz.getListInfoBySecne(secne);
        res.setData(list);
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
        Integer version = (Integer) params.get("version");
        String result = droolsActionService.getTemplate(info,version);

        res.setData(result);
        res.setSuucessMsg("");
        return res;
    }

    /* *
     * 与未发布场景比较
     * @author ly
     * @modifyTime 2020/11/10 16:53:00
     */
    @PostMapping(value = "/compareNotPublishSecne")
    public ObjectRestResponse compareNotPublishSecne(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String scene = (String) params.get("scene");
        Integer version = (Integer) params.get("version");
        res = publishBiz.comepareNotPublishScene(scene,version);
        return res;
    }

    /* *
     * 已发布场景比较
     * @author ly
     * @modifyTime 2020/11/10 16:53:00
     */
    @PostMapping(value = "/comparePublishSecne")
    public ObjectRestResponse comparePublishSecne(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String scene = (String) params.get("scene");
        Integer version1 = (Integer) params.get("version1");
        Integer version2 = (Integer) params.get("version2");
        PublishRuleSceneInfoVo vo1 = publishBiz.getPublishSceneVo(scene,version1);
        PublishRuleSceneInfoVo vo2 = publishBiz.getPublishSceneVo(scene,version2);
        Map<String,Object> result = new HashMap<>();
        result.put("vo1",vo1);
        result.put("vo2",vo2);

        String resultStr = compareSceneBiz.compare(result);
        result.put("conclusion",resultStr);

        res.setSuucessMsg("查询成功");
        res.setData(result);
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
        Integer version  = (Integer) params.get("version");
        String list = (String) params.get("entitys");
        if(StringUtils.isBlank(id)){
            res.setErrorMsg("场景参数缺失");
            return res;
        }
        PublishRuleSceneInfo info = publishRuleSceneBiz.getInfoById(Long.valueOf(id),version);

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
                            if("Integer".equals(f.getType().getSimpleName())){
                                f.set(obj, Integer.parseInt(itemInfo.getItemValue()));
                            }else{
                                f.set(obj, itemInfo.getItemValue());
                            }
                        }
                    }
                    object.addFactObject(obj);
                }
            }
        }




        //全局对象
        RuleExecutionResult result = new RuleExecutionResult();
        object.setGlobal("_result",result);
        String template = droolsActionService.getTemplate(info.getSceneIdentify(),info.getPublishVersion());
        RuleExecutionObject resultData = droolsActionService.excute(object, info.getSceneIdentify(),info.getPublishVersion(),template);
        res.setSuucessMsg("resultData");
        Map<String,Object> map = new HashMap<>();
        map.put("result",resultData);
        map.put("template",template);
        res.setData(map);
        return res;
    }
}
