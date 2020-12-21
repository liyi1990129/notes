package com.drools.vo;

import com.drools.model.RuleInterfaceFlow;
import com.drools.model.RuleInterfaceFlowPoint;
import lombok.Data;

import java.util.List;

@Data
public class FlowVo {
    public List<NodeVo> nodes;
    public List<EdgeVo> edges;
    public List<RuleInterfaceFlow> flows;
    public List<RuleInterfaceFlowPoint> points;


    //节点对象
    @Data
    public static class NodeVo{
        private String shape; // 节点类型  root,node
        private String id;   //节点唯一标识
        private String name;//节点名称
        private Integer x;//节点坐标x
        private Integer y;//节点坐标y
        private String status;//状态done,timeout
    }

    //节点连接线
    @Data
    public static class EdgeVo{
        private String id;  //唯一标识
        private String source;//连接线起点
        private String target;//连接线终点
        private String label;//文本
        private String circleType;//循环类型(left-左循环,right-右循环)
        private List<PointVo> controlPoints;//坐标
    }

    @Data
    public static class PointVo{
        private Integer x;
        private Integer y;
    }
}


