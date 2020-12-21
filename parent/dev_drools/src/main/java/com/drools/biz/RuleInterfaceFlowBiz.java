package com.drools.biz;

import com.drools.common.DroolsConstants;
import com.drools.mapper.RuleInterfaceFlowMapper;
import com.drools.model.RuleInterfaceFlow;
import com.drools.model.RuleInterfaceFlowPoint;
import com.drools.vo.FlowVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RuleInterfaceFlowBiz {

    @Resource
    private RuleInterfaceFlowMapper ruleInterfaceFlowMapper;
    @Resource
    private RuleInterfaceFlowPointBiz ruleInterfaceFlowPointBiz;

    /* *
     * 根据接口ID 获取流程节点 
     * @author ly
     * @modifyTime 2020/11/23 14:02:00
     */
    public List<RuleInterfaceFlow> findListByInterfaceId(Long interfaceId){
        RuleInterfaceFlow flow = new RuleInterfaceFlow();
        flow.setInterfaceId(interfaceId);
        return this.ruleInterfaceFlowMapper.select(flow);
    }
    
    /* *
     * 封装前台流程图对象 
     * @author ly
     * @modifyTime 2020/11/23 14:37:00
     */
    public FlowVo getFlowInfo(Long interfaceId){
        FlowVo vo = null;
        List<RuleInterfaceFlow> flowList = this.findListByInterfaceId(interfaceId);
        if(!CollectionUtils.isEmpty(flowList)){
            int i = 1;
            vo = new FlowVo();
            List<FlowVo.EdgeVo> edgeVos = new ArrayList<>();
            List<FlowVo.NodeVo> nodeVos = new ArrayList<>();

            for (RuleInterfaceFlow ruleInterfaceFlow : flowList) {
//                //普通连接线
//                if(StringUtils.isNotBlank(ruleInterfaceFlow.getFlowNextNode())){
//                    FlowVo.EdgeVo edgeVo = new FlowVo.EdgeVo();
//                    edgeVo.setId("edge"+i);
//                    edgeVo.setLabel(ruleInterfaceFlow.getRemark());
//                    edgeVo.setSource(ruleInterfaceFlow.getFlowShapeId());
//                    edgeVo.setTarget(ruleInterfaceFlow.getFlowNextNode());
//                    edgeVos.add(edgeVo);
//                    i++;
//                }
                
                //封装节点
                FlowVo.NodeVo nodeVo = new FlowVo.NodeVo();
                nodeVo.setId(ruleInterfaceFlow.getFlowShapeId());
                nodeVo.setName(ruleInterfaceFlow.getFlowShapeName());
                nodeVo.setShape(ruleInterfaceFlow.getFlowNode());
                nodeVo.setX(ruleInterfaceFlow.getFlowShapeX());
                nodeVo.setY(ruleInterfaceFlow.getFlowShapeY());
                if(DroolsConstants.IsEffect.YES.equals(ruleInterfaceFlow.getIsEffect())){
                    if(StringUtils.isNotBlank(ruleInterfaceFlow.getFlowShapeStatus())){
                        nodeVo.setStatus(ruleInterfaceFlow.getFlowShapeStatus());
                    }else{
                        nodeVo.setStatus("");
                    }
                }else{
                    if(StringUtils.isNotBlank(ruleInterfaceFlow.getFlowShapeStatus())){
                        nodeVo.setStatus("cancel");
                    }else{
                        nodeVo.setStatus("");
                    }
                }

                nodeVos.add(nodeVo);
            }

            //循环线
            List<RuleInterfaceFlowPoint> pointList = ruleInterfaceFlowPointBiz.findByInterfaceId(interfaceId);
            if(!CollectionUtils.isEmpty(pointList)){
                for (RuleInterfaceFlowPoint point : pointList) {
                    FlowVo.EdgeVo edgeVo = new FlowVo.EdgeVo();
                    edgeVo.setId("edge"+i);
                    edgeVo.setLabel(point.getLabelText());
                    edgeVo.setSource(point.getSource());
                    edgeVo.setTarget(point.getTarget());
                    edgeVo.setCircleType(point.getCircleType());

                    List<FlowVo.PointVo> pointVos = new ArrayList<>();
                    if(point.getX1()!=null && point.getY1()!=null){
                        FlowVo.PointVo vo1 = new FlowVo.PointVo();
                        vo1.setX(point.getX1());
                        vo1.setY(point.getY1());
                        pointVos.add(vo1);
                    }
                    if(point.getX2()!=null && point.getY2()!=null){
                        FlowVo.PointVo vo1 = new FlowVo.PointVo();
                        vo1.setX(point.getX2());
                        vo1.setY(point.getY2());
                        pointVos.add(vo1);
                    }
                    edgeVo.setControlPoints(pointVos);
                    edgeVos.add(edgeVo);
                    i++;
                }
            }

            vo.setFlows(flowList);
            vo.setPoints(pointList);
            vo.setEdges(edgeVos);
            vo.setNodes(nodeVos);
        }

        return vo;
    }

    /* *
     * 更新节点状态
     * @author ly
     * @modifyTime 2020/11/23 17:32:00
     */
    public void updateFlow(Long id,String isEffect){
        RuleInterfaceFlow flow = new RuleInterfaceFlow();
        flow.setFlowId(id);
        RuleInterfaceFlow ruleInterfaceFlow = this.ruleInterfaceFlowMapper.selectOne(flow);

        ruleInterfaceFlow.setIsEffect(isEffect);
        this.ruleInterfaceFlowMapper.updateByPrimaryKeySelective(ruleInterfaceFlow);
    }

    public void saveOrUpdate(Long interfaceId,List<RuleInterfaceFlow> list){
        RuleInterfaceFlow flow = new RuleInterfaceFlow();
        flow.setInterfaceId(interfaceId);
        this.ruleInterfaceFlowMapper.delete(flow);

        if(!CollectionUtils.isEmpty(list)){
            for (RuleInterfaceFlow ruleInterfaceFlow : list) {
                ruleInterfaceFlow.setInterfaceId(interfaceId);
                ruleInterfaceFlow.setIsEffect(DroolsConstants.IsEffect.YES);
                this.ruleInterfaceFlowMapper.insertSelective(ruleInterfaceFlow);
            }
        }
    }

}
