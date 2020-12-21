package com.drools.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class RuleInterfaceLogVo {
    private String interfaceIdentify;//接口标识
    private String clmnum;//受理号
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date beginTime;//开始时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;//结束时间
    private Long useTime;//耗时
    private String req;//请求
    private String remark;//备注
    private String res;//返回
    private String batchNo;//批次
    private String ip;//批次

    private String interfaceName;//接口名称
    private Long reqCount;//请求数量

//    @JsonFormat(pattern = "yyyy-MM-dd")
    private String oneDay;
}
