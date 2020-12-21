package com.drools.rest;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.drools.biz.*;
import com.drools.common.ObjectRestResponse;
import com.drools.model.RuleActionInfo;
import com.drools.util.FiledUtil;
import com.drools.vo.ClassVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* *
 * 动作--控制器
 * @author ly
 * @modifyTime 2020/11/10 16:42:00
 */
@RestController
@RequestMapping(value = "/action")
public class ActionController {

    @Autowired
    private RuleActionInfoBiz ruleActionInfoBiz;

    /* *
     * 分页查询
     * @author ly
     * @modifyTime 2020/11/10 16:42:00
     */
    @PostMapping(value = "/page")
    public ObjectRestResponse page(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        PageInfo page = ruleActionInfoBiz.page(params);
        res.setData(page);
        res.setSuucessMsg("");
        return res;
    }


    /* *
     * 根据ID获取动作信息
     * @author ly
     * @modifyTime 2020/11/10 16:42:00
     */
    @PostMapping(value = "/get")
    public ObjectRestResponse get(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String id = (String) params.get("id");
        if(StringUtils.isBlank(id)){
            res.setErrorMsg("参数缺失");
            return res;
        }
        //动作信息
        RuleActionInfo info = ruleActionInfoBiz.findInfoById(Long.valueOf(id));

        Map<String,Object> result = new HashMap<>();
        result.put("action",info);
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

        // 删除动作
        ruleActionInfoBiz.delInfo(Long.valueOf(id));

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

        id = ruleActionInfoBiz.saveOrUpdate(ruleActionInfo);

        res.setSuucessMsg("");
        return res;
    }

    /* *
     * 获取实体类的动作方法 
     * @author ly
     * @modifyTime 2020/11/9 15:40:00
     */
    @PostMapping(value = "/getActionMethodName")
    public ObjectRestResponse getActionMethodName(@RequestBody Map<String,Object> params) throws Exception {
        String clsName = (String) params.get("className");
        ObjectRestResponse res = new ObjectRestResponse();
        List<ClassVo> list =  FiledUtil.getActionMethodName(clsName);
        res.setData(list);
        res.setSuucessMsg("查询成功");
        return res;
    }

}
