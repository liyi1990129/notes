package com.drools.entity.clm;

import com.drools.common.DroolsConstants;
import com.drools.common.annotation.EntityName;
import com.drools.common.annotation.FieldName;
import lombok.Data;

@Data
@EntityName(name="历史手术信息")
public class HistorySurgery {
    @FieldName(name="手术代码",type = DroolsConstants.FieldType.BASE)
    public String  surgCode = "";     // LCAB.SURGCODE01-10 手术代码
    @FieldName(name="手术日期",type = DroolsConstants.FieldType.BASE)
    public int     surgDte;     // LCAB.SURGDTE01-10 手术日期
    @FieldName(name="同一次手术序号",type = DroolsConstants.FieldType.BASE)
    public String  surgSeq = "";     // LCAB.SURGSEQ01-10 同一次手术序号
    @FieldName(name="手术比例",type = DroolsConstants.FieldType.BASE)
    public double surgPct;      // LCCB.SURGPCT 手术比例，用的分子值
}
