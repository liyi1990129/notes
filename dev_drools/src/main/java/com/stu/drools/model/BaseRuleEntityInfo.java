package com.stu.drools.model;

import org.apache.commons.lang.StringUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 描述：
 * CLASSPATH: com.stu.drools.model.BaseRuleEntityInfo
 * VERSION:   1.0
 * Created by lihao
 * DATE:      2017/7/20
 */
@Table(name = "rule_entity_info")
public class BaseRuleEntityInfo extends BaseModel {
    @Id
    @Column(name = "ENTITY_ID")
    private Integer entityId;//主键

    private String entityName;//名称
    private String entityDesc;//描述
    private String entityIdentify;//标识
    private String pkgName;//实体包路径

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getEntityDesc() {
        return entityDesc;
    }

    public void setEntityDesc(String entityDesc) {
        this.entityDesc = entityDesc;
    }

    public String getEntityIdentify() {
        return entityIdentify;
    }

    public void setEntityIdentify(String entityIdentify) {
        this.entityIdentify = entityIdentify;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    /**
     * 方法说明:获取实体类名称
     */
    public String getEntityClazz(){
        if(StringUtils.isNotBlank(pkgName)){
            int index =  pkgName.lastIndexOf(".");
            return pkgName.substring(index+1);
        }
        return "";
    }
}
