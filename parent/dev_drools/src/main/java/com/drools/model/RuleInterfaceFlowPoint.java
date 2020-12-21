package com.drools.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/* *
 * 接口流程节点循环线
 * @author ly
 * @modifyTime 2020/11/23 10:06:00
 */
@Data
@Table(name = "RULE_INTERFACE_FLOW_POINT")
public class RuleInterfaceFlowPoint extends BaseModel {
    @Id
    @Column(name = "POINT_ID")
    private Long pointId;//主键
    private String sourceName;//起点节点
    private String source;//起点
    private String targetName;//终点节点
    private String target;//终点
    private String labelText;//文本
    private Integer x1;//X坐标
    private Integer y1;//Y坐标
    private Integer x2;//X坐标
    private Integer y2;//Y坐标
    private Integer seq;//排序
    private Long interfaceId;//接口iD
    private String edgeId;
    private String pointType;
    private String circleType;//循环类型(left-左循环,right-右循环)
}
