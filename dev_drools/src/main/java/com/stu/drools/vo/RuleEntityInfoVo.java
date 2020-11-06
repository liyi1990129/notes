package com.stu.drools.vo;

import com.stu.drools.model.RuleEntityItemInfo;
import lombok.Data;

import java.util.List;

@Data
public class RuleEntityInfoVo {
    private Long entityId;//主键
    private String entityName;//名称
    private String entityDesc;//描述
    private String entityIdentify;//标识
    private String pkgName;//实体包路径

    List<RuleEntityItemInfo> itemInfos;
}
