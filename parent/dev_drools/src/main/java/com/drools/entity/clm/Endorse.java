package com.drools.entity.clm;

import com.drools.common.DroolsConstants;
import com.drools.common.annotation.EntityName;
import com.drools.common.annotation.FieldName;
import lombok.Data;

@Data
@EntityName(name="赔案保单批改信息")
public class Endorse {
    @FieldName(name="保全代码",type = DroolsConstants.FieldType.BASE)
    public String  batctrCde = "";  // PTRN.BATCTRCDE 保全代码
    @FieldName(name="生效日期",type = DroolsConstants.FieldType.BASE)
    public int     ptrnEff;        // PTRN.PTRNEFF   生效日期
}
