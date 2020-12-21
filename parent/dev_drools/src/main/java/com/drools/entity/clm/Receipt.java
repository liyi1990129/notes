package com.drools.entity.clm;

import com.drools.common.DroolsConstants;
import com.drools.common.annotation.EntityName;
import com.drools.common.annotation.FieldName;
import com.drools.entity.children.ReceiptItem;
import lombok.Data;

import java.util.List;

@Data
@EntityName(name="赔案收据信息")
public class Receipt {
    @FieldName(name="自定义收据类型",type = DroolsConstants.FieldType.BASE)
    public String  type = ""; // 自定义收据类型，因为原系统的定义方式混乱 OTH其他住院/OWH自费住院/SOH社保住院/OTO其他门诊/OWO自费门诊/SOO社保门诊
    @FieldName(name="收据类型",type = DroolsConstants.FieldType.BASE)
    public String  trecTyp = "";     // LCAE.TRECTYP  收据类型 1自费住院+其他住院/2社保住院/4门诊/3原其他住院(现在不用)
    @FieldName(name="收据序号",type = DroolsConstants.FieldType.BASE)
    public int    clmSeq;     // LCAE.CLMSEQ  收据序号
    @FieldName(name="收据起期",type = DroolsConstants.FieldType.BASE)
    public int    fromDate;     // LCAE.FROMDATE  收据起期
    @FieldName(name="收据止期",type = DroolsConstants.FieldType.BASE)
    public int    toDate;     // LCAE.TODATE  收据止期
    @FieldName(name="收据就医方式",type = DroolsConstants.FieldType.BASE)
    public String  hsMesh = "";     // LCAE.HSMETH  收据就医方式  1自费/2社保/3其他
    @FieldName(name="收据类别",type = DroolsConstants.FieldType.BASE)
    public String  paperTyp = "";     // LCAE.PAPERTYP  收据类别 1正本/2副本/3复印件
    @FieldName(name="收据排序权值",type = DroolsConstants.FieldType.BASE)
    public int     seqNo;    // 收据排序权值
    @FieldName(name="是否参与计算标记",type = DroolsConstants.FieldType.BASE)
    public String  computingFlag = "Y";  // 是否参与计算标记 ???
    @FieldName(name="是否已经赔付标记",type = DroolsConstants.FieldType.BASE)
    public String  paiedFlag = "N";  // 是否已经赔付标记  ???
    @FieldName(name="收据总申请金额",type = DroolsConstants.FieldType.BASE)
    public double totalAmt;  // 收据总申请金额
    public double tempTotalAmt;
    public double tempTotalAmt2;

    public List<ReceiptItem> items;
    public int  itemLength;
}
