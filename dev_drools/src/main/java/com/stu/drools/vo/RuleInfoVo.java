package com.stu.drools.vo;

import lombok.Data;

import java.util.Date;

@Data
public class RuleInfoVo {
    private Long ruleId;//主键
    private Long sceneId;//场景
    private String ruleName;//名称
    private String ruleDesc;//描述
    private String ruleEnabled;//是否启用

    private String sceneIdentify;//标识
    private Integer sceneType;//类型
    private String sceneName;//名称
    private String sceneDesc;//描述
    //创建人
    private Long creUserId;
    //创建时间
    private Date creTime;
    //是否有效
    private Integer isEffect;
    //备注
    private String remark;
}
