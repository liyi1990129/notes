package com.drools.entity.clm;

import com.drools.common.DroolsConstants;
import com.drools.common.annotation.EntityName;
import com.drools.common.annotation.FieldName;
import lombok.Data;

@Data
@EntityName(name="赔案保单备注信息")
public class PolicyNote {
    @FieldName(name="subKeyA",type = DroolsConstants.FieldType.BASE)
    public String subKeyA = "";  // NOTE.SUBKEYA
    @FieldName(name="subKeyB",type = DroolsConstants.FieldType.BASE)
    public String subKeyB = "";  // NOTE.SUBKEYB
    @FieldName(name="subKeyC",type = DroolsConstants.FieldType.BASE)
    public String subKeyC = "";  // NOTE.SUBKEYC
    @FieldName(name="subKeyD",type = DroolsConstants.FieldType.BASE)
    public String subKeyD = "";  // NOTE.SUBKEYD
    @FieldName(name="顺序号",type = DroolsConstants.FieldType.BASE)
    public String noteSeq = "";  // NOTE.NOTESEQ 顺序号
    @FieldName(name="终端ID",type = DroolsConstants.FieldType.BASE)
    public String termId = "";   // NOTE.TERMID 终端ID
    @FieldName(name="备注类型",type = DroolsConstants.FieldType.BASE)
    public String noteType = ""; // NOTE.NOTETYPE 备注类型
    @FieldName(name="界面代码",type = DroolsConstants.FieldType.BASE)
    public String screen = "";   // NOTE.SCREEN 界面代码
    @FieldName(name="提醒日",type = DroolsConstants.FieldType.BASE)
    public int    remDte;        // NOTE.REMDTE 提醒日
    @FieldName(name="打印标识",type = DroolsConstants.FieldType.BASE)
    public String prtFlg = "";   // NOTE.PRTFLG 打印标识
}
