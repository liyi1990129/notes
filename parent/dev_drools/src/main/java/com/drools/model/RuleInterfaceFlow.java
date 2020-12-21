package com.drools.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/* *
 * 接口流程对象
 * @author ly
 * @modifyTime 2020/11/23 10:06:00
 */
@Data
@Table(name = "RULE_INTERFACE_FLOW")
public class RuleInterfaceFlow extends BaseModel {
    @Id
    @Column(name = "FLOW_ID")
    private Long flowId;//主键
    private String flowNode;//节点(node,root)
    private String flowShapeId;//节点id
    private String flowShapeName;//节点名称
    private Integer flowShapeX;//节点X坐标
    private Integer flowShapeY;//节点Y坐标
    private String flowShapeStatus;//节点状态（done,timeout）
    private String flowPreviousNode;//上一节点ID
    private String flowNextNode;//下一节点ID
    private String flowNodeType;//节点类型
    private Long interfaceId;//接口id
    private String interfaceName;//接口名称
    private String interfaceIdentify;//接口标识
    private Integer seq;//排序
    private Long entityId;//实体Id
    private Long itemId;//属性ID
    private String sceneIdentify;//场景标识
    private String endCircle;//结束循环

    public RuleInterfaceFlow(){}
    public RuleInterfaceFlow(String flowShapeId, String flowShapeName,  String flowPreviousNode, String flowNextNode, String flowNodeType, Integer seq) {
        this.flowShapeId = flowShapeId;
        this.flowShapeName = flowShapeName;
        this.flowPreviousNode = flowPreviousNode;
        this.flowNextNode = flowNextNode;
        this.flowNodeType = flowNodeType;
        this.seq = seq;
    }
}
