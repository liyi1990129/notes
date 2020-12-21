package com.drools.entity.clm;

import com.drools.common.DroolsConstants;
import com.drools.common.annotation.EntityName;
import com.drools.common.annotation.FieldName;
import lombok.Data;

@Data
@EntityName(name="文件")
public class Document {
    @FieldName(name="文件代码",type = DroolsConstants.FieldType.BASE)
    public String docCode;    //文件代码	LCAJ.DOCCODE
    @FieldName(name="文件份数",type = DroolsConstants.FieldType.BASE)
    public int docCount = 0;  //文件份数	LCAJ.DOCCOUNT
    @FieldName(name="文件状态",type = DroolsConstants.FieldType.BASE)
    public String docStat;    //文件状态	LCAJ.DOCSTAT   2待补
}
