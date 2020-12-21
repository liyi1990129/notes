package com.drools.rest;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.drools.biz.RuleConditionBiz;
import com.drools.biz.RuleEntityBiz;
import com.drools.biz.RuleEntityItemBiz;
import com.drools.biz.RuleSceneEntityRelBiz;
import com.drools.biz.sys.SysDictDetailBiz;
import com.drools.common.DroolsConstants;
import com.drools.common.ObjectRestResponse;
import com.drools.model.RuleEntityInfo;
import com.drools.model.RuleEntityItemInfo;
import com.drools.model.RuleSceneEntityRelInfo;
import com.drools.model.RuleSceneInfo;
import com.drools.model.sys.SysDictDetail;
import com.drools.util.DroolsUtil;
import com.drools.util.EntityUtil;
import com.drools.util.FiledUtil;
import com.drools.util.ScanningFileUtil;
import com.drools.vo.ClassVo;
import com.drools.vo.PropertyVo;
import com.drools.vo.RuleEntityItemInfoVo;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
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

/* *
 * 实体类--控制器
 * @author ly
 * @modifyTime 2020/11/10 16:44:00
 */
@RestController
@RequestMapping(value = "/entity")
public class EntityController {

    @Autowired
    private RuleEntityBiz ruleEntityBiz;
    @Autowired
    private RuleEntityItemBiz ruleEntityItemBiz;
    @Autowired
    private SysDictDetailBiz sysDictDetailBiz;
    @Autowired
    private RuleSceneEntityRelBiz ruleSceneEntityRelBiz;

    /* *
     * 分页查询
     * @author ly
     * @modifyTime 2020/11/10 16:44:00
     */
    @PostMapping(value = "/page")
    public ObjectRestResponse page(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        PageInfo page = ruleEntityBiz.page(params);
        res.setData(page);
        res.setSuucessMsg("");
        return res;
    }

    /* *
     * 列表查询
     * @author ly
     * @modifyTime 2020/11/10 16:45:00
     */
    @PostMapping(value = "/list")
    public ObjectRestResponse list(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        List<RuleEntityInfo> list = ruleEntityBiz.findBaseRuleEntityInfoList();
        res.setData(list);
        res.setSuucessMsg("查询成功");
        return res;
    }

    /* *
     * 获取实体类属性以及属性的枚举类
     * @param params
     * @author ly
     * @modifyTime 2020/11/9 13:43:00
     */
    @PostMapping(value = "/listItem")
    public ObjectRestResponse listItem(@RequestBody Map<String,Object> params) throws Exception {
        ObjectRestResponse res = new ObjectRestResponse();
        String id = (String) params.get("id");
        if(StringUtils.isBlank(id)){
            res.setErrorMsg("参数缺失");
            return res;
        }

        //根据实体ID 获取有效实体属性
        Map<String,Object> query = new HashMap<>();
        query.put("entityId",Long.valueOf(id));
        query.put("isEffect", DroolsConstants.IsEffect.YES);
        List<RuleEntityItemInfo> list =  ruleEntityItemBiz.listByParams(query);

        // 根据实体属性的 枚举code 查询字典详细属性
        List<RuleEntityItemInfoVo> vos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(list)){
            //获取枚举类
            for (RuleEntityItemInfo itemInfo : list) {
                RuleEntityItemInfoVo vo = new RuleEntityItemInfoVo();
                BeanUtils.copyProperties(itemInfo,vo);
                if(StringUtils.isNotBlank(itemInfo.getEnumName())){
//                    List<ClassVo> classVos = FiledUtil.getAllEnum(itemInfo.getEnumName());
                    List<SysDictDetail> dictDetails = this.sysDictDetailBiz.findByMainCode(itemInfo.getEnumName());
                    vo.setEnumList(dictDetails);
                }
                vos.add(vo);
            }
        }
        res.setData(vos);
        res.setSuucessMsg("查询成功");
        return res;
    }

    /* *
     * 根据ID 获取实体信息
     * @author ly
     * @modifyTime 2020/11/10 16:46:00
     */
    @PostMapping(value = "/get")
    public ObjectRestResponse get(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String id = (String) params.get("id");
        if(StringUtils.isBlank(id)){
            res.setErrorMsg("参数缺失");
            return res;
        }
        //获取实体信息
        RuleEntityInfo info = ruleEntityBiz.findBaseRuleEntityInfoById(Long.valueOf(id));
        Map<String,Object> query = new HashMap<>();
        query.put("entityId",Long.valueOf(id));
        //获取实体类属性信息
        List<RuleEntityItemInfo> list =  ruleEntityItemBiz.listByParams(query);

        //获取实体类 枚举信息 集合类信息
//        List<PropertyVo> proList = sysDictDetailBiz.findByClassName(info.getPkgName());
//        List<PropertyVo> proList = EntityUtil.getProperty(info.getPkgName());
        List<PropertyVo> proList = ruleEntityBiz.getItems(info.getPkgName(),info.getEntityIdentify(),info.getEntityName());


        Map<String,Object> result = new HashMap<>();
        result.put("entity",info);
        result.put("entityItems",list);
        result.put("proList",proList);
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

        RuleSceneEntityRelInfo sceneInfo = new RuleSceneEntityRelInfo();
        sceneInfo.setEntityId(Long.valueOf(id));
        List<RuleSceneEntityRelInfo> sceneEntityRelInfos = ruleSceneEntityRelBiz.getList(sceneInfo);
        if(!CollectionUtils.isEmpty(sceneEntityRelInfos)){
            res.setErrorMsg("有关联的场景，不能删除");
            return res;
        }

        ruleEntityItemBiz.delInfoByEntityId(Long.valueOf(id));
        ruleEntityBiz.delEntityInfoById(Long.valueOf(id));
        res.setSuucessMsg("删除成功");
        return res;
    }

    @PostMapping(value = "/saveOrUpdate")
    public ObjectRestResponse saveOrUpdate(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String info = (String) params.get("entity");
        String entityItems = (String) params.get("entityItems");
        RuleEntityInfo ruleEntityInfo = JSON.parseObject(info, RuleEntityInfo.class);
        ruleEntityInfo.setEntityIdentify(FiledUtil.getEntityIdentify(ruleEntityInfo.getPkgName()));
        Long id = ruleEntityInfo.getEntityId();
//        if(null!= ruleEntityInfo.getEntityId()){
//            ruleEntityItemBiz.delInfoByEntityId(ruleEntityInfo.getEntityId());
//        }

        id = ruleEntityBiz.saveOrUpdate(ruleEntityInfo);

        if(StringUtils.isNotBlank(entityItems)){
            List<RuleEntityItemInfo> list = JSON.parseArray(entityItems, RuleEntityItemInfo.class);
            for(RuleEntityItemInfo item:list){
                item.setEntityId(id);
                ruleEntityItemBiz.saveOrUpdate(item);
            }
        }

        res.setSuucessMsg("");
        return res;
    }


    @PostMapping(value = "/sycnEntityData")
    public ObjectRestResponse sycnEntityData(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        List<ClassVo> list = ScanningFileUtil.addClass();
        if(!CollectionUtils.isEmpty(list)){
            for (ClassVo classVo : list) {
               RuleEntityInfo ruleEntityInfo =  EntityUtil.createEntity(classVo);
               if(ruleEntityInfo==null){
                   res.setErrorMsg("同步失败");
                   return res;
               }
               List<RuleEntityItemInfo> itemList = EntityUtil.createEntityItem(ruleEntityInfo);

               this.ruleEntityBiz.saveEntityAndItem(ruleEntityInfo,itemList);
            }
        }


        res.setSuucessMsg("同步成功");
        return res;
    }

    @PostMapping(value = "/sycnByEntity")
    public ObjectRestResponse sycnByEntity(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String id = (String) params.get("id");
        if(StringUtils.isBlank(id)){
            res.setErrorMsg("参数缺失");
            return res;
        }
        //获取实体信息
        RuleEntityInfo info = ruleEntityBiz.findBaseRuleEntityInfoById(Long.valueOf(id));
        List<RuleEntityItemInfo> itemList = EntityUtil.createEntityItem(info);

        this.ruleEntityBiz.saveEntityAndItem(info,itemList);

        res.setSuucessMsg("同步成功");
        return res;
    }


    /* *
     * 获取实体类
     * @author ly
     * @modifyTime 2020/10/21 8:40:00
     */
    @PostMapping(value = "/getEntitys")
    public ObjectRestResponse getEntitys(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        List<ClassVo> list = ScanningFileUtil.addClass();

        // 加上特殊类MAP
        ClassVo vo = new ClassVo();
        vo.setName("Map");
        vo.setClassName("java.util.Map");
//        list.add(vo);
        res.setData(list);
        res.setSuucessMsg("查询成功");
        return res;
    }

    /* *
     * 获取实体类的属性
     * @author ly
     * @modifyTime 2020/10/21 8:43:00
     */
    @PostMapping(value = "/getEntityProperties")
    public ObjectRestResponse getEntityProperties(@RequestBody Map<String,Object> params){
        String clsName = (String) params.get("className");
        ObjectRestResponse res = new ObjectRestResponse();
        List<PropertyVo> list = sysDictDetailBiz.findByClassName(clsName);
        res.setData(list);
        res.setSuucessMsg("查询成功");
        return res;
    }

    /* *
     * 获取实体类的相关方法
     * @author ly
     * @modifyTime 2020/10/21 8:43:00
     */
    @PostMapping(value = "/getEntityMethod")
    public ObjectRestResponse getEntityMethod(@RequestBody Map<String,Object> params) throws Exception {
        String clsName = (String) params.get("className");
        ObjectRestResponse res = new ObjectRestResponse();
        List<ClassVo> list =  FiledUtil.getMethodName(clsName);
        res.setData(list);
        res.setSuucessMsg("查询成功");
        return res;
    }
}
