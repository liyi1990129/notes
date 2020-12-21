package com.drools.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/* *
 * 接口对象
 * @author ly
 * @modifyTime 2020/11/23 10:06:00
 */
@Data
@Table(name = "RULE_INTERFACE")
public class RuleInterface extends BaseModel {
    @Id
    @Column(name = "INTERFACE_ID")
    private Long interfaceId;//主键
    private String interfaceName;//接口名称
    private String interfaceType;//接口类型
    private String interfaceReqPkgname;//接口请求对象包名
    private String interfaceResPkgname;//接口返回对象包名
    private String interfaceReqMsg;//接口请求示例
    private String interfaceIdentify;//接口标识
    private String interfaceJava;//代码
    private String interfaceFlowJava;//流程代码
    private String interfaceAddr;//地址
    private Long javaVersion;//版本
}
