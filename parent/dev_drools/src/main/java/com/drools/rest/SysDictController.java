package com.drools.rest;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.drools.biz.RuleConditionBiz;
import com.drools.biz.RuleEntityBiz;
import com.drools.biz.RuleEntityItemBiz;
import com.drools.biz.sys.SysDictDetailBiz;
import com.drools.biz.sys.SysDictMainBiz;
import com.drools.common.ObjectRestResponse;
import com.drools.model.RuleEntityInfo;
import com.drools.model.RuleEntityItemInfo;
import com.drools.model.sys.SysDictDetail;
import com.drools.model.sys.SysDictMain;
import com.drools.util.FiledUtil;
import com.drools.util.ScanningFileUtil;
import com.drools.vo.ClassVo;
import com.drools.vo.PropertyVo;
import com.drools.vo.RuleEntityItemInfoVo;
import org.apache.commons.lang.StringUtils;
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
 * 数据字典 
 * @author ly
 * @modifyTime 2020/11/10 16:58:00
 */
@RestController
@RequestMapping(value = "/dict")
public class SysDictController {

    @Autowired
    private SysDictMainBiz sysDictMainBiz;
    @Autowired
    private SysDictDetailBiz sysDictDetailBiz;

    @PostMapping(value = "/page")
    public ObjectRestResponse page(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        PageInfo page = sysDictMainBiz.page(params);
        res.setData(page);
        res.setSuucessMsg("");
        return res;
    }
    @PostMapping(value = "/list")
    public ObjectRestResponse list(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        res.setSuucessMsg("查询成功");
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

        SysDictMain dictMain = this.sysDictMainBiz.getInfoById(Long.valueOf(id));
        List<SysDictDetail> dictDetails = this.sysDictDetailBiz.findByMainId(Long.valueOf(id));
        Map<String,Object> map = new HashMap<>();
        map.put("main",dictMain);
        map.put("details",dictDetails);

        res.setData(map);
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
        sysDictDetailBiz.delById(Long.valueOf(id));
        sysDictMainBiz.delInfoById(Long.valueOf(id));
        res.setSuucessMsg("删除成功");
        return res;
    }

    @PostMapping(value = "/saveOrUpdate")
    public ObjectRestResponse saveOrUpdate(@RequestBody Map<String,Object> params){
        ObjectRestResponse res = new ObjectRestResponse();
        String info = (String) params.get("main");
        String details = (String) params.get("details");
        SysDictMain sysDictMain = JSON.parseObject(info, SysDictMain.class);
        List<SysDictDetail> list = JSON.parseArray(details,SysDictDetail.class);

        if(sysDictMain.getId()!=null){
            sysDictDetailBiz.delById(sysDictMain.getId());
        }
        Long id = this.sysDictMainBiz.saveOrUpdate(sysDictMain);
        if(!CollectionUtils.isEmpty(list)){
            for (SysDictDetail dictDetail : list) {
                dictDetail.setSdmId(id);
                dictDetail.setSdmCode(sysDictMain.getSdmCode());
                this.sysDictDetailBiz.saveInfo(dictDetail);
            }
        }
        res.setSuucessMsg("");
        return res;
    }



}
