package com.drools.rest;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.drools.biz.*;
import com.drools.common.ObjectRestResponse;
import com.drools.model.RuleInterface;
import com.drools.model.RuleInterfaceFlow;
import com.drools.model.RuleInterfaceFlowPoint;
import com.drools.model.RuleInterfaceJava;
import com.drools.service.CreateJavaServce;
import com.drools.util.FiledUtil;
import com.drools.util.JavaCodeFormattingUtil;
import com.drools.vo.FlowVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/rule/interface")
public class RuleInterfaceController {

    @Resource
    RuleInterfaceBiz ruleInterfaceBiz;
    @Resource
    RuleInterfaceFlowBiz ruleInterfaceFlowBiz;
    @Resource
    RuleInterfaceFlowPointBiz ruleInterfaceFlowPointBiz;
    @Resource
    private RuleInterfaceJavaBiz ruleInterfaceJavaBiz;
    @Resource
    private CreateJavaServce createJavaServce;

    /* *
     * 分页查询
     * @author ly
     * @modifyTime 2020/11/10 16:44:00
     */
    @PostMapping(value = "/page")
    public ObjectRestResponse page(@RequestBody Map<String, Object> params) {
        ObjectRestResponse res = new ObjectRestResponse();
        PageInfo page = ruleInterfaceBiz.page(params);
        res.setData(page);
        res.setSuucessMsg("");
        return res;
    }
    /* *
     * 列表查询
     * @author ly
     * @modifyTime 2020/11/10 16:44:00
     */
    @PostMapping(value = "/list")
    public ObjectRestResponse list(@RequestBody Map<String, Object> params) {
        ObjectRestResponse res = new ObjectRestResponse();
        List<RuleInterface> data = ruleInterfaceBiz.findAllList();
        res.setData(data);
        res.setSuucessMsg("");
        return res;
    }

    /* *
     * 获取接口文档说明
     * @author ly
     * @modifyTime 2020/11/17 13:17:00
     */
    @PostMapping(value = "/getApi")
    public ObjectRestResponse getApi(@RequestBody Map<String, Object> params) throws Exception {
        log.info("getApi入参[{}]", JSON.toJSONString(params));
        String id = (String) params.get("id");
        ObjectRestResponse res = new ObjectRestResponse();
        if (StringUtils.isBlank(id)) {
            res.setErrorMsg("参数缺失");
            return res;
        }
        RuleInterface ruleInterface = ruleInterfaceBiz.findByID(Long.valueOf(id));
        Map<String, Object> map = FiledUtil.thirdApi(ruleInterface);
        map.put("info",ruleInterface);
        res.setData(map);
        res.setSuucessMsg("");
        log.info("getApi返回[{}]", JSON.toJSONString(res));
        return res;
    }

    /* *
     * 获取接口流程节点
     * @author ly
     * @modifyTime 2020/11/17 13:17:00
     */
    @PostMapping(value = "/getInterfaceFlows")
    public ObjectRestResponse getInterfaceFlows(@RequestBody Map<String, Object> params) throws Exception {
        log.info("getInterfaceFlows入参[{}]", JSON.toJSONString(params));
        String id = (String) params.get("id");
        ObjectRestResponse res = new ObjectRestResponse();
        if (StringUtils.isBlank(id)) {
            res.setErrorMsg("参数缺失");
            return res;
        }
//        List<RuleInterfaceFlow> list = this.ruleInterfaceFlowBiz.findListByInterfaceId(Long.valueOf(id));
        FlowVo flowVo = ruleInterfaceFlowBiz.getFlowInfo(Long.valueOf(id));

        res.setData(flowVo);
        res.setSuucessMsg("");
        log.info("getInterfaceFlows返回[{}]", JSON.toJSONString(res));
        return res;
    }


    /* *
     * 更新接口流程节点
     * @author ly
     * @modifyTime 2020/11/17 13:17:00
     */
    @PostMapping(value = "/updateFlow")
    public ObjectRestResponse updateFlow(@RequestBody Map<String, Object> params) throws Exception {
        log.info("updateFlow 入参[{}]", JSON.toJSONString(params));
        String id = (String) params.get("id");
        String flows = (String) params.get("flows");
        String points = (String) params.get("points");
        ObjectRestResponse res = new ObjectRestResponse();
        if (StringUtils.isBlank(id)) {
            res.setErrorMsg("参数缺失");
            return res;
        }

        // 节点
        if(StringUtils.isNotBlank(flows)){
            List<RuleInterfaceFlow> flowList = JSON.parseArray(flows,RuleInterfaceFlow.class);
            this.ruleInterfaceFlowBiz.saveOrUpdate(Long.valueOf(id),flowList);
        }

        //连接线
        if(StringUtils.isNotBlank(points)){
            List<RuleInterfaceFlowPoint> pointList = JSON.parseArray(points,RuleInterfaceFlowPoint.class);
            this.ruleInterfaceFlowPointBiz.saveOrUpdate(Long.valueOf(id),pointList);
        }

        // 生成代码
        RuleInterface ruleInterface = ruleInterfaceBiz.findByID(Long.valueOf(id));
        String javaStr = createJavaServce.createJavaCode(ruleInterface.getInterfaceId());
//        if(StringUtils.isBlank(ruleInterface.getInterfaceJava())){
            Long version = ruleInterfaceJavaBiz.getCurVersion(ruleInterface.getInterfaceId());
//            ruleInterface.setInterfaceJava(javaStr);

            RuleInterfaceJava java = new RuleInterfaceJava();
            java.setInterfaceId(ruleInterface.getInterfaceId());
            java.setInterfaceIdentify(ruleInterface.getInterfaceIdentify());
            java.setInterfaceJava(javaStr);
            java.setJavaVersion(version);
            ruleInterfaceJavaBiz.save(java);
//        }
        ruleInterface.setJavaVersion(version);
        ruleInterface.setInterfaceFlowJava(javaStr);

        this.ruleInterfaceBiz.updateInterface(ruleInterface);

        res.setSuucessMsg("保存成功");
        log.info("updateFlow返回[{}]", JSON.toJSONString(res));
        return res;
    }


    /* *
     * 更新java代码
     * @author ly
     * @modifyTime 2020/11/17 13:17:00
     */
    @PostMapping(value = "/updateJava")
    public ObjectRestResponse updateJava(@RequestBody Map<String, Object> params) throws Exception {
        log.info("updateJava 入参[{}]", JSON.toJSONString(params));
        String id = (String) params.get("id");
        String javaStr = (String) params.get("javaStr");
        ObjectRestResponse res = new ObjectRestResponse();
        if (StringUtils.isBlank(id)) {
            res.setErrorMsg("参数缺失");
            return res;
        }


        // 更新代码
        RuleInterface ruleInterface = ruleInterfaceBiz.findByID(Long.valueOf(id));

        Long version = ruleInterfaceJavaBiz.getCurVersion(ruleInterface.getInterfaceId());
        ruleInterface.setInterfaceJava(javaStr);

        //新增版本
        RuleInterfaceJava java = new RuleInterfaceJava();
        java.setInterfaceId(ruleInterface.getInterfaceId());
        java.setInterfaceIdentify(ruleInterface.getInterfaceIdentify());
        java.setInterfaceJava(javaStr);
        java.setJavaVersion(version);
        ruleInterfaceJavaBiz.save(java);

        ruleInterface.setInterfaceJava(javaStr);
        ruleInterface.setJavaVersion(version);
        this.ruleInterfaceBiz.updateInterface(ruleInterface);

        res.setSuucessMsg("保存成功");
        log.info("updateJava返回[{}]", JSON.toJSONString(res));
        return res;
    }
    /* *
     * 获取当前版本java代码
     * @author ly
     * @modifyTime 2020/11/17 13:17:00
     */
    @PostMapping(value = "/getJavaByVersion")
    public ObjectRestResponse getJavaByVersion(@RequestBody Map<String, Object> params) throws Exception {
        log.info("getJavaByVersion 入参[{}]", JSON.toJSONString(params));
        String id = (String) params.get("id");
        ObjectRestResponse res = new ObjectRestResponse();
        if (StringUtils.isBlank(id)) {
            res.setErrorMsg("参数缺失");
            return res;
        }
        RuleInterface ruleInterface = ruleInterfaceBiz.findByID(Long.valueOf(id));

        RuleInterfaceJava ruleInterfaceJava = ruleInterfaceJavaBiz.getJavaByVersion(ruleInterface.getJavaVersion(),ruleInterface.getInterfaceId());
        ruleInterface.setInterfaceJava(ruleInterfaceJava.getInterfaceJava());

        List<RuleInterfaceJava> versions = ruleInterfaceJavaBiz.getVersion(ruleInterface.getInterfaceId());
        Map<String,Object> map = new HashMap<>();
        map.put("interface",ruleInterface);
        map.put("versions",versions);
        res.setSuucessMsg("成功");
        res.setData(map);
        log.info("getJavaByVersion返回[{}]", JSON.toJSONString(res));
        return res;
    }
    /* *
     * 回退版本
     * @author ly
     * @modifyTime 2020/11/17 13:17:00
     */
    @PostMapping(value = "/backJavaByVersion")
    public ObjectRestResponse backJavaByVersion(@RequestBody Map<String, Object> params) throws Exception {
        log.info("backJavaByVersion 入参[{}]", JSON.toJSONString(params));
        String id = (String) params.get("id");
        String version = (String) params.get("version");
        ObjectRestResponse res = new ObjectRestResponse();
        if (StringUtils.isBlank(id) || StringUtils.isBlank(version)) {
            res.setErrorMsg("参数缺失");
            return res;
        }
        RuleInterface ruleInterface = ruleInterfaceBiz.findByID(Long.valueOf(id));
        ruleInterface.setJavaVersion(Long.valueOf(version));
        this.ruleInterfaceBiz.updateInterface(ruleInterface);

        res.setSuucessMsg("成功");
        log.info("backJavaByVersion返回[{}]", JSON.toJSONString(res));
        return res;
    }
}
