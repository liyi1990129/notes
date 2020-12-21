package com.drools.biz;

import com.drools.mapper.RuleSceneLogMapper;
import com.drools.model.RuleSceneLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RuleSceneLogBiz {
    @Autowired
    private RuleSceneLogMapper ruleSceneLogMapper;

    public List<RuleSceneLog> findList(String interfaceIdentify,String batchNo){
        RuleSceneLog entity = new RuleSceneLog();
        entity.setInterfaceIdentify(interfaceIdentify);
        entity.setBatchNo(batchNo);
        return this.ruleSceneLogMapper.listByBatch(entity);
    }

}
