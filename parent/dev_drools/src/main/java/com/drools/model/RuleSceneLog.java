package com.drools.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 场景执行日志
 */
@Data
@Table(name = "RULE_SCENE_LOG")
public class RuleSceneLog  {
    private String clmnum;//受理号
    private String interfaceIdentify;//接口标识
    private String sceneIdentify;//场景标识
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date beginTime;//开始时间
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;//结束时间
    private Long useTime;//耗时
    private String result;//结果
    private String remark;//备注
    private Integer resultStatus;//执行结果状态(0-未通过，1-通过)
    private String batchNo;//批次

    @Transient
    private String sceneName;//场景名称
}
