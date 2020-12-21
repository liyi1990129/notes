package com.drools.vo;

import com.drools.model.sys.SysDictDetail;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RuleEntityItemInfoVo {
    //创建人
    private Long creUserId;
    //创建时间
    private Date creTime;
    //是否有效
    private String isEffect;
    //备注
    private String remark;
    private Long itemId;//主键
    private Long entityId;//实体id
    private String itemName;//字段名称
    private String itemIdentify;//字段标识
    private String itemDesc;//属性描述
    private String enumName;//枚举类
    private String itemValue;
    private String itemCls;// 集合属性实体类路径
    private String itemType;//属性类型
    List<SysDictDetail> enumList;
}
