package com.drools.vo;

import com.drools.model.sys.SysDictDetail;
import lombok.Data;

import java.util.List;

@Data
public class PropertyVo {
    private String filedName;
    private String chName;
    private String enumName;
    private String listCls;
    private String filedType;
    private String filedObjType;
    private String remark;
    List<ClassVo> enumList;
    List<SysDictDetail> dictDetails;
    List<PropertyVo> children;
    private String parent;
    private String parentText;
}
