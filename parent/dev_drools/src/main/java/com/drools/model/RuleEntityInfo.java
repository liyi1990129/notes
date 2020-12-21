package com.drools.model;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 描述：
 */
@Data
@Table(name = "RULE_ENTITY_INFO")
public class RuleEntityInfo extends BaseModel {

    @Id
    @Column(name = "ENTITY_ID")
    private Long entityId;//主键

    private String entityName;//名称
    private String entityDesc;//描述
    private String entityIdentify;//标识
    private String pkgName;//实体包路径
}
