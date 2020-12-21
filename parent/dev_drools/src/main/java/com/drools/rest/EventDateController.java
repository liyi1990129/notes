package com.drools.rest;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.drools.biz.EventDateBiz;
import com.drools.common.ObjectRestResponse;
import com.drools.model.EventDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/eventdate")
public class EventDateController {

    @Autowired
    private EventDateBiz eventDateBiz;

    /* *
     * 分页查询
     * @author ly
     * @modifyTime 2020/11/10 16:49:00
     */
    @PostMapping(value = "/page")
    public ObjectRestResponse list(@RequestBody Map<String, Object> params) {
        ObjectRestResponse res = new ObjectRestResponse();
        PageInfo page = eventDateBiz.page(params);
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
    public ObjectRestResponse get(@RequestBody Map<String, Object> params) {
        ObjectRestResponse res = new ObjectRestResponse();
        String crtable = (String) params.get("crtable");
        if (StringUtils.isBlank(crtable)) {
            res.setErrorMsg("参数缺失");
            return res;
        }
        List<EventDate> list = eventDateBiz.getInfoByCrtable(crtable);
        res.setSuucessMsg("");
        res.setData(list);
        return res;
    }

    /* *
     * 删除规则信息
     * @author ly
     * @modifyTime 2020/11/10 16:49:00
     */
    @PostMapping(value = "/del")
    public ObjectRestResponse del(@RequestBody Map<String, Object> params) {
        ObjectRestResponse res = new ObjectRestResponse();
        String crtable = (String) params.get("crtable");
        if (StringUtils.isBlank(crtable)) {
            res.setErrorMsg("参数缺失");
            return res;
        }
        eventDateBiz.delInfoByCrtable(crtable);
        res.setSuucessMsg("删除成功");
        return res;
    }

    /* *
     * 保存
     * @author ly
     * @modifyTime 2020/11/10 16:49:00
     */
    @PostMapping(value = "/saveOrUpdate")
    public ObjectRestResponse saveOrUpdate(@RequestBody Map<String,Object> params) {
        ObjectRestResponse res = new ObjectRestResponse();
        String crtable = (String) params.get("crtable");
        String list = (String) params.get("list");

        if(StringUtils.isBlank(crtable)){
            res.setErrorMsg("参数缺失");
            return res;
        }
        List<EventDate> eventDates = null;
        if (StringUtils.isNotBlank(list)){
            eventDates = JSON.parseArray(list,EventDate.class);
        }

        eventDateBiz.saveOrUpdate(crtable,eventDates);
        res.setSuucessMsg("");
        return res;
    }


}
