package com.drools.util;
import java.util.ArrayList;
import java.util.Date;

import com.drools.common.DroolsConstants;
import com.drools.common.annotation.EntityName;
import com.drools.model.RuleEntityInfo;
import com.drools.model.RuleEntityItemInfo;
import com.drools.vo.ClassVo;
import com.drools.vo.PropertyVo;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class EntityUtil {

    public static List<PropertyVo> getProperty(String className){
        List<PropertyVo> proList = ScanningFileUtil.getEntityFields(className);
        if(!CollectionUtils.isEmpty(proList)){
            for (PropertyVo propertyVo : proList) {
                if("List".equals(propertyVo.getFiledType())){
                    List<PropertyVo> childList = getProperty(propertyVo.getListCls());
                    propertyVo.setChildren(childList);
                }
            }
        }
        return proList;
    }

    public static RuleEntityInfo createEntity(ClassVo classVo) {
        RuleEntityInfo ruleEntityInfo = null;
        try{
            Class clazz =  Class.forName(classVo.getClassName());
            EntityName en = (EntityName) clazz.getAnnotation(EntityName.class);
            String entityName = "";
            if(en!=null){
                entityName = en.name();
            }


            ruleEntityInfo = new RuleEntityInfo();
            ruleEntityInfo.setEntityId(0L);
            ruleEntityInfo.setEntityName(entityName);
            ruleEntityInfo.setEntityDesc("");
            ruleEntityInfo.setEntityIdentify(FiledUtil.getEntityIdentify(classVo.getName()));
            ruleEntityInfo.setPkgName(classVo.getClassName());
            ruleEntityInfo.setCreUserId(0L);
            ruleEntityInfo.setCreTime(new Date());
            ruleEntityInfo.setIsEffect(DroolsConstants.IsEffect.YES);
            ruleEntityInfo.setRemark("");
        }catch (Exception e){
            e.printStackTrace();
        }

        return ruleEntityInfo;
    }

    public static List<RuleEntityItemInfo> createEntityItem(RuleEntityInfo ruleEntityInfo){
        List<RuleEntityItemInfo> result = new ArrayList<>();
        List<PropertyVo> proList = ScanningFileUtil.getEntityFields(ruleEntityInfo.getPkgName());
        if(!CollectionUtils.isEmpty(proList)){
            for (PropertyVo propertyVo : proList) {
                RuleEntityItemInfo ruleEntityItemInfo = new RuleEntityItemInfo();
                ruleEntityItemInfo.setItemId(0L);
                ruleEntityItemInfo.setEntityId(0L);
                ruleEntityItemInfo.setItemName(propertyVo.getChName());
                ruleEntityItemInfo.setItemIdentify(propertyVo.getFiledName());
                ruleEntityItemInfo.setItemDesc("");
                ruleEntityItemInfo.setEnumName(propertyVo.getEnumName());
                ruleEntityItemInfo.setItemType(propertyVo.getFiledType());
                ruleEntityItemInfo.setItemCls(propertyVo.getListCls());
                ruleEntityItemInfo.setItemValue("");
                ruleEntityItemInfo.setEntityIdentify(ruleEntityInfo.getEntityIdentify());
                ruleEntityItemInfo.setCreUserId(0L);
                ruleEntityItemInfo.setCreTime(new Date());
                ruleEntityItemInfo.setIsEffect(DroolsConstants.IsEffect.YES);
                ruleEntityItemInfo.setRemark("");
                ruleEntityItemInfo.setItemObjType(propertyVo.getFiledObjType());
                result.add(ruleEntityItemInfo);
            }
        }

        return result;
    }
}
