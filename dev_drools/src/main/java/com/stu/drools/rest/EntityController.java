package com.stu.drools.rest;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.stu.drools.biz.RuleEntityBiz;
import com.stu.drools.biz.RuleEntityItemBiz;
import com.stu.drools.common.ObjectRestResponse;
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
@RequestMapping(value = "/entity")
public class EntityController {

    @Autowired
    private RuleEntityBiz ruleEntityBiz;
    @Autowired
    private RuleEntityItemBiz ruleEntityItemBiz;

    @PostMapping(value = "/page")
    public ObjectRestResponse page(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        PageInfo page = ruleEntityBiz.page(params);
        res.setData(page);
        res.setSuucessMsg("");
        return res;
    }
    @PostMapping(value = "/list")
    public ObjectRestResponse list(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        List<RuleEntityInfo> list = ruleEntityBiz.findBaseRuleEntityInfoList();
        res.setData(list);
        res.setSuucessMsg("查询成功");
        return res;
    }

    @PostMapping(value = "/get")
    public ObjectRestResponse get(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        Integer id = (Integer) params.get("id");
        if(null==id){
            res.setErrorMsg("参数缺失");
            return res;
        }
        RuleEntityInfo info = ruleEntityBiz.findBaseRuleEntityInfoById(id);
        Map<String,Object> query = new HashMap<>();
        query.put("entityId",id);
        List<RuleEntityItemInfo> list =  ruleEntityItemBiz.listByParams(query);
        List<String> proList = ScanningFileUtil.getEntityFields(info.getEntityClazz());

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
        Integer id = (Integer) params.get("id");
        if(null==id){
            res.setErrorMsg("参数缺失");
            return res;
        }
        ruleEntityItemBiz.delInfoByEntityId(id);
        ruleEntityBiz.delEntityInfoById(id);
        res.setSuucessMsg("删除成功");
        return res;
    }

    @PostMapping(value = "/saveOrUpdate")
    public ObjectRestResponse saveOrUpdate(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String info = (String) params.get("entity");
        String entityItems = (String) params.get("entityItems");
        RuleEntityInfo ruleEntityInfo = JSON.parseObject(info, RuleEntityInfo.class);
        Integer id = ruleEntityInfo.getEntityId();
        if(null!= ruleEntityInfo.getEntityId()){
            ruleEntityItemBiz.delInfoByEntityId(ruleEntityInfo.getEntityId());
        }else{
            id = ruleEntityBiz.saveOrUpdate(ruleEntityInfo);
        }
        if(StringUtils.isNotBlank(entityItems)){
            List<RuleEntityItemInfo> list = JSON.parseArray(entityItems, RuleEntityItemInfo.class);
            for(RuleEntityItemInfo item:list){
                item.setItemId(null);
                item.setEntityId(id);
                ruleEntityItemBiz.saveOrUpdate(item);
            }
        }

        res.setSuucessMsg("");
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
        List<String> list = ScanningFileUtil.getEntityFields(clsName);
        res.setData(list);
        res.setSuucessMsg("查询成功");
        return res;
    }
}
