package com.drools.entity.clm;

import com.drools.common.DroolsConstants;
import com.drools.common.annotation.EntityName;
import com.drools.common.annotation.FieldName;
import lombok.Data;

@Data
@EntityName(name="历史赔案责任赔付信息")
public class HistoryBenefit {
    @FieldName(name="责任代码",type = DroolsConstants.FieldType.BASE)
    public String  benCode = "";       // LCAR.BENCODE 责任代码
    @FieldName(name="分组号",type = DroolsConstants.FieldType.BASE)
    public String  compGrp = "";       // LCAR.COMPGRP 分组号
    @FieldName(name="历史赔案，险种的责任赔付金额",type = DroolsConstants.FieldType.BASE)
    public double payAmt;             // LCAR.PAYAMT   历史赔案，险种的责任赔付金额
    @FieldName(name="计算机运算金额",type = DroolsConstants.FieldType.BASE)
    public double allocAmt;           // LCAR.ALLOCAMT   计算机运算金额
    @FieldName(name="计算机运算天数",type = DroolsConstants.FieldType.BASE)
    public double allocDay;           // LCAR.ALLOCDAY   计算机运算天数/周数/月数/次数
    @FieldName(name="计算机运算百分比",type = DroolsConstants.FieldType.BASE)
    public double allocPct;           // LCAR.ALLOCPCT   计算机运算百分比
    @FieldName(name="申请金额",type = DroolsConstants.FieldType.BASE)
    public double presAmt;            // LCAR.PRESAMT   申请金额
    @FieldName(name="申请赔付天数",type = DroolsConstants.FieldType.BASE)
    public int    presDay;            // LCAR.PRESDAY 申请赔付天数
    @FieldName(name="申请赔付比例",type = DroolsConstants.FieldType.BASE)
    public double presPct;            // LCAR.PRESPCT 申请赔付比例
    @FieldName(name="保额/计划别",type = DroolsConstants.FieldType.BASE)
    public double sumins;             // LCAR.SUMINS  保额/计划别
    @FieldName(name="重大疾病/双倍给付/重大手术标记 ",type = DroolsConstants.FieldType.BASE)
    public String  doubleInd = "";     // LCAR.DOUBLEIND 重大疾病/双倍给付/重大手术标记 Y-是/N-否
    @FieldName(name="手术代码",type = DroolsConstants.FieldType.BASE)
    public String  surgCode = "";      // LCAR.SURGCODE 手术代码
    @FieldName(name="年龄系数",type = DroolsConstants.FieldType.BASE)
    public double agePct;             // LCJJ.AGEPCT 年龄系数
    @FieldName(name="伤病系数",type = DroolsConstants.FieldType.BASE)
    public double illPct;             // LCJJ.ILLPCT 伤病系数
    @FieldName(name="赔付公式",type = DroolsConstants.FieldType.BASE)
    public String  paramStr = "";      // LCJJ.PARAMSTR 赔付公式
    @FieldName(name="公式说明",type = DroolsConstants.FieldType.BASE)
    public String  calDesc = "";       // LCJJ.CALDESC 公式说明
    @FieldName(name="保额续赔",type = DroolsConstants.FieldType.BASE)
    public String  joinAmt = "";       // LCKB.SUMJOIN 保额续赔
    @FieldName(name="年度续赔",type = DroolsConstants.FieldType.BASE)
    public String  joinYear = "";      // LCKB.YEARJOIN  年度续赔
    @FieldName(name="同事故续赔",type = DroolsConstants.FieldType.BASE)
    public String  joinAccident = "";  // LCKB.ACCJOIN 同事故续赔
    @FieldName(name="次数续赔",type = DroolsConstants.FieldType.BASE)
    public String  joinTime = "";      // LCKB.TIMESJOIN 次数续赔
    @FieldName(name="其他续赔",type = DroolsConstants.FieldType.BASE)
    public String  joinOther = "";     // LCKB.OTHERSJOIN  其他续赔

    @FieldName(name="免赔额",type = DroolsConstants.FieldType.BASE)
    public double notAmt;            // LCAR.PRESAMT   免赔额

    /* 初始值为 "N"
     * 跨责任续赔时 不需要判断历史是否赔付该责任
     *  amt--保额
        year--年度
        accid--事故
        time--次数
        other--其他
    */
    @FieldName(name="payAmtSys",type = DroolsConstants.FieldType.BASE)
    public String  payAmtSys = "N";

    //xuwei 2014-06-30 新伤残代码需求
    @FieldName(name="是否已经累计当前历史责任的赔付金额",type = DroolsConstants.FieldType.BASE)
    public String  historyAmtFlag = "N";  //是否已经累计当前历史责任的赔付金额

}
