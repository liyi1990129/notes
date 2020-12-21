package com.drools.biz;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.drools.mapper.RuleInterfaceLogMapper;
import com.drools.model.RuleInterfaceLog;
import com.drools.util.DateUtil;
import com.drools.vo.RuleInterfaceLogVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class RuleInterfaceLogBiz {
    @Autowired
    RuleInterfaceLogMapper ruleInterfaceLogMapper;

    public List<RuleInterfaceLogVo> countByInterface(RuleInterfaceLog ruleInterfaceLog){
        List<RuleInterfaceLogVo> list = ruleInterfaceLogMapper.countByInterface(ruleInterfaceLog);
        return list;
    }
    public List<RuleInterfaceLogVo> countByInterfaceIdentify(String interfaceIdentify){
        RuleInterfaceLog ruleInterfaceLog = new RuleInterfaceLog();
        ruleInterfaceLog.setInterfaceIdentify(interfaceIdentify);
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - 7);
        String begin = DateUtil.get4yMd(c.getTime()) + " 00:00:00";
        Date beginTime = DateUtil.parse4yMdHms(begin);

        String end = DateUtil.get4yMd(now)+" 23:59:59";
        Date endTime = DateUtil.parse4yMdHms(end);

        ruleInterfaceLog.setBeginTime(beginTime);
        ruleInterfaceLog.setEndTime(endTime);

        List<RuleInterfaceLogVo> list = ruleInterfaceLogMapper.countByInterfaceIdentify(ruleInterfaceLog);
        return list;
    }

    /* *
     * 分页查询
     * @author ly
     * @modifyTime 2020/10/22 16:36:00
     */
    public PageInfo page(Map<String, Object> params) {
        Integer pageNumber = (Integer) params.get("pageNumber");
        Integer pageSize = (Integer) params.get("pageSize");
        String interfaceIdentify = (String) params.get("interfaceIdentify");
        String beginTime = (String) params.get("beginTime");
        String endTime = (String) params.get("endTime");

        RuleInterfaceLog ruleInterfaceLog = new RuleInterfaceLog();
        if(StringUtils.isNotBlank(interfaceIdentify)){
            ruleInterfaceLog.setInterfaceIdentify(interfaceIdentify);
        }
        if(beginTime!=null && endTime!=null){
            ruleInterfaceLog.setBeginTime(DateUtil.parse4yMdHms(beginTime));
            ruleInterfaceLog.setEndTime(DateUtil.parse4yMdHms(endTime));
        }

        Page page = PageHelper.startPage(pageNumber, pageSize,true);
        List<RuleInterfaceLogVo> list = this.ruleInterfaceLogMapper.listByParams(ruleInterfaceLog);

        PageInfo pageInfo = new PageInfo(list);
        pageInfo.setTotal(page.getTotal());
        return pageInfo;
    }


   public Boolean countByBatchNo(String batchNo){
        if(StringUtils.isBlank(batchNo)){
            return false;
        }
        RuleInterfaceLog ruleInterfaceLog = new RuleInterfaceLog();
        ruleInterfaceLog.setBatchNo(batchNo);
       RuleInterfaceLog entity = this.ruleInterfaceLogMapper.countByBatchNo(ruleInterfaceLog);
        if(entity != null){
            return false;
        }
        return true;
    }
}
