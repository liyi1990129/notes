package com.drools.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/* *
 * 接口对象
 * @author ly
 * @modifyTime 2020/11/23 10:06:00
 */
@Data
@Table(name = "RULE_INTERFACE_LOG")
public class RuleInterfaceLog {
    private String clmnum;//受理号
    private String interfaceIdentify;//接口标识
    private Date beginTime;//开始时间
    private Date endTime;//结束时间
    private Long useTime;//耗时
    private String req;//请求
    private String remark;//备注
    private String res;//返回
    private String batchNo;//批次
    private String ip;//批次
}
