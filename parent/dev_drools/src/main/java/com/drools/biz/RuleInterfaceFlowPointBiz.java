package com.drools.biz;

import com.drools.mapper.RuleInterfaceFlowPointMapper;
import com.drools.model.RuleInterfaceFlowPoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class RuleInterfaceFlowPointBiz {
    @Resource
    RuleInterfaceFlowPointMapper ruleInterfaceFlowPointMapper;

    /* *
     * 根据接口ID获取循环节点线的坐标 
     * @author ly
     * @modifyTime 2020/11/23 14:27:00
     */
    public List<RuleInterfaceFlowPoint> findByInterfaceId(Long interfaceId){
        RuleInterfaceFlowPoint point = new RuleInterfaceFlowPoint();
        point.setInterfaceId(interfaceId);
        return this.ruleInterfaceFlowPointMapper.select(point);
    }

    public void saveOrUpdate(Long interfaceId,List<RuleInterfaceFlowPoint> list){
        RuleInterfaceFlowPoint point = new RuleInterfaceFlowPoint();
        point.setInterfaceId(interfaceId);
        this.ruleInterfaceFlowPointMapper.delete(point);

        if(!CollectionUtils.isEmpty(list)){
            for (RuleInterfaceFlowPoint ruleInterfaceFlowPoint : list) {
                ruleInterfaceFlowPoint.setInterfaceId(interfaceId);
                this.ruleInterfaceFlowPointMapper.insertSelective(ruleInterfaceFlowPoint);
            }
        }
    }

}
