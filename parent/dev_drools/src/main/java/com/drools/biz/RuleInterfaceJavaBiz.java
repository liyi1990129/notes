package com.drools.biz;

import com.drools.mapper.RuleInterfaceJavaMapper;
import com.drools.model.RuleInterfaceJava;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RuleInterfaceJavaBiz {
    @Autowired
    RuleInterfaceJavaMapper ruleInterfaceJavaMapper;

    public void save(RuleInterfaceJava ruleInterfaceJava){
        this.ruleInterfaceJavaMapper.insertSelective(ruleInterfaceJava);
    }

    public Long getCurVersion(Long interfaceId){
        Long version = ruleInterfaceJavaMapper.findMaxVersion(interfaceId);
        if(version==null){
            version = 0L;
        }
        return version+1;
    }

    /**
     * 根据版本和接口id查询java代码
     * @Author ly
     * @Date  2020/12/12 14:20
     * @Param [version, interfaceId]
     * @return com.drools.model.RuleInterfaceJava
     **/
    public RuleInterfaceJava getJavaByVersion(Long version,Long interfaceId){
        RuleInterfaceJava entity = new RuleInterfaceJava();
        entity.setInterfaceId(interfaceId);
        entity.setJavaVersion(version);
        return this.ruleInterfaceJavaMapper.selectOne(entity);
    }

    /**
     * 获取版本
     * @Author ly
     * @Date  2020/12/12 14:42
     * @Param
     * @return
     **/
    public List<RuleInterfaceJava> getVersion(Long interfaceId){
        RuleInterfaceJava entity = new RuleInterfaceJava();
        entity.setInterfaceId(interfaceId);
        return this.ruleInterfaceJavaMapper.select(entity);
    }

}
