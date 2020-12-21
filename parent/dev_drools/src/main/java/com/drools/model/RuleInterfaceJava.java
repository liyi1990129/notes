package com.drools.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/* *
 * 接口对象java代码
 * @author ly
 * @modifyTime 2020/11/23 10:06:00
 */
@Data
@Table(name = "RULE_INTERFACE_JAVA")
public class RuleInterfaceJava extends BaseModel {
    @Column(name = "INTERFACE_ID")
    private Long interfaceId;//主键
    private String interfaceIdentify;//接口标识
    private String interfaceJava;//代码
    private Long javaVersion;//版本
}
