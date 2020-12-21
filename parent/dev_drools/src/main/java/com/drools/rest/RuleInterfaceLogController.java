package com.drools.rest;

import com.drools.biz.RuleSceneLogBiz;
import com.drools.model.RuleSceneLog;
import com.github.pagehelper.PageInfo;
import com.drools.biz.RuleInterfaceLogBiz;
import com.drools.common.ObjectRestResponse;
import com.drools.model.RuleInterfaceLog;
import com.drools.vo.RuleInterfaceLogVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 *
 * @Author ly
 * @Date  2020/12/14 16:25
 * @Param 
 * @return 
 **/
@Slf4j
@RestController
@RequestMapping(value = "/log")
public class RuleInterfaceLogController {

    @Autowired
    RuleInterfaceLogBiz ruleInterfaceLogBiz;
    @Autowired
    RuleSceneLogBiz ruleSceneLogBiz;

    /* *
     * 分页查询
     * @author ly
     * @modifyTime 2020/11/10 16:52:00
     */
    @PostMapping(value = "/page")
    public ObjectRestResponse page(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        PageInfo page = ruleInterfaceLogBiz.page(params);
        res.setData(page);
        res.setSuucessMsg("");
        return res;
    }

    /**
     * 各个接口的请求数量
     * @param ruleInterfaceLog
     * @return
     */
    @PostMapping(value = "/countByInterface")
    public ObjectRestResponse countByInterface(@RequestBody RuleInterfaceLog ruleInterfaceLog){
        ObjectRestResponse res = new ObjectRestResponse();
        List<RuleInterfaceLogVo> list = ruleInterfaceLogBiz.countByInterface(ruleInterfaceLog);
        res.setData(list);
        res.setSuucessMsg("");
        return res;
    }
    /**
     * 接口一周的请求数量
     * @return
     */
    @PostMapping(value = "/countByInterfaceId")
    public ObjectRestResponse countByInterfaceId(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String identify = (String) params.get("interfaceIdentify");
        List<RuleInterfaceLogVo> list = ruleInterfaceLogBiz.countByInterfaceIdentify(identify);
        res.setData(list);
        res.setSuucessMsg("");
        return res;
    }
    /**
     * 接口一周的请求数量
     * @return
     */
    @PostMapping(value = "/listScene")
    public ObjectRestResponse listScene(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String identify = (String) params.get("interfaceIdentify");
        String batchNo = (String) params.get("batchNo");
        List<RuleSceneLog> list = ruleSceneLogBiz.findList(identify,batchNo);
        res.setData(list);
        res.setSuucessMsg("");
        return res;
    }
}
