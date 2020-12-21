package com.drools.vo;

import com.drools.model.RuleEntityItemInfo;
import lombok.Data;

import java.util.List;

@Data
public class RuleEntityInfoVo {
    private Long entityId;//主键
    private String entityName;//名称
    private String entityDesc;//描述
    private String entityIdentify;//标识
    private String pkgName;//实体包路径
    private String remark;

    private Long parentId;//父类ID
    private String parentIdentify;//父类标识
    private String treeId;
    private Integer level;
    private Boolean leaf;

    List<RuleEntityItemInfoVo> itemInfos;
}
