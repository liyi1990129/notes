package com.drools.entity.clm;

import com.drools.common.DroolsConstants;
import com.drools.common.annotation.EntityName;
import com.drools.common.annotation.FieldName;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@EntityName(name="就医信息")
public class Doctor {
    @FieldName(name="就医序号",type = DroolsConstants.FieldType.BASE)
    public int     recId;          // LCAB.RECID 就医序号
    @FieldName(name="tranNo",type = DroolsConstants.FieldType.BASE)
    public int     tranNo;         // LCAB.TRANNO
    @FieldName(name="医院代码",type = DroolsConstants.FieldType.BASE)
    public String  hsCode = "";    // LCAB.HSCODE 医院代码
    @FieldName(name="就医日期",type = DroolsConstants.FieldType.BASE)
    public int     consultDte;     // LCAB.CONSULTDTE 就医日期  旧数据部分来源LCAAPF
    @FieldName(name="一般病房住院日期",type = DroolsConstants.FieldType.BASE)
    public int     hsDte;          // LCAB.HSDTE 一般病房住院日期  旧数据部分来源LCAAPF
    @FieldName(name="一般病房出院日期",type = DroolsConstants.FieldType.BASE)
    public int     leaveDte;       // LCAB.LEAVEDTE 一般病房出院日期  旧数据部分来源LCAAPF
    @FieldName(name="一般病房住院天数",type = DroolsConstants.FieldType.BASE)
    public int     hsDays;         // LCAB.HSDAYS 一般病房住院天数  旧数据部分来源LCAAPF
    @FieldName(name="加护病房住院日期",type = DroolsConstants.FieldType.BASE)
    public int     icuHsDte;       // LCAB.ICUHSDTE 加护病房住院日期
    @FieldName(name="加护病房出院日期",type = DroolsConstants.FieldType.BASE)
    public int     icuLeaveDte;    // LCAB.ICULVEDTE 加护病房出院日期
    @FieldName(name="加护病房住院天数",type = DroolsConstants.FieldType.BASE)
    public int     icuDays;        // LCAB.ICUDAYS 加护病房住院天数
    @FieldName(name="医院类型",type = DroolsConstants.FieldType.BASE)
    public String  trecTyp = "";   // LCCC.TRECTYP  医院类型
    @FieldName(name="医院有效标记",type = DroolsConstants.FieldType.BASE)
    public String  validFlag = ""; // LCCC.VALIDFLAG 医院有效标记  1有效/0无效
    @FieldName(name="医院等级",type = DroolsConstants.FieldType.BASE)
    public String  hclass = "";    // LCCC.HCLASS  医院等级 01一级 最差/02 二级/03 三级 最好/04 其他
    @FieldName(name="赔付范围",type = DroolsConstants.FieldType.BASE)
    public String  sumind = "";    // LCCC.SUMIND  赔付范围
    @FieldName(name="医院品质",type = DroolsConstants.FieldType.BASE)
    public String  hqind = "";     // LCCC.HQIND  医院品质
    @FieldName(name="是否参与计算标记",type = DroolsConstants.FieldType.BASE)
    public String  computingFlag = "Y";  // 是否参与计算标记 Y/N
    @FieldName(name="就医排序权值",type = DroolsConstants.FieldType.BASE)
    public int     seqNo;          // 就医排序权值，决定就医的赔付顺序，从小到大
    @FieldName(name="伤病代码",type = DroolsConstants.FieldType.COLLECT)
    public List<String>  illCodes;     // LCAB.ILLCODE01-10 伤病代码，最大长度10
    @FieldName(name="手术信息",listCls="com.drools.entity.clm.Surgery",type = DroolsConstants.FieldType.COLLECT)
    public List<Surgery> surgeries = new ArrayList<>();   // 手术信息，最大长度10
    @FieldName(name="手术数量",type = DroolsConstants.FieldType.BASE)
    public int     surgeryLength; // 手术数量
    @FieldName(name="收据信息",listCls="com.drools.entity.clm.Receipt",type = DroolsConstants.FieldType.COLLECT)
    public List<Receipt> receipts;   // 收据信息
    @FieldName(name="收据长度",type = DroolsConstants.FieldType.BASE)
    public int     receiptLength; // 收据长度
    @FieldName(name="当前收据类型内ICU合计的和",type = DroolsConstants.FieldType.BASE)
    public double icuTotalAmt = 0;  // 当前收据类型内ICU合计的和，只有自费/其他住院收据有ICU合计
    @FieldName(name="tempIcuAmt",type = DroolsConstants.FieldType.BASE)
    public double tempIcuAmt = 0;
    @FieldName(name="tempIcuAmt2",type = DroolsConstants.FieldType.BASE)
    public double tempIcuAmt2 = 0;
    @FieldName(name="自费方式其他住院合计",type = DroolsConstants.FieldType.BASE)
    public double ownOTH;  // 自费方式其他住院合计
    @FieldName(name="其他方式其他住院合计",type = DroolsConstants.FieldType.BASE)
    public double othOTH;  // 其他方式其他住院合计

    // 扩展属性
    @FieldName(name="扩展属性1",type = DroolsConstants.FieldType.BASE)
    public String ext1;
    @FieldName(name="扩展属性2",type = DroolsConstants.FieldType.BASE)
    public String ext2;
    @FieldName(name="扩展属性3",type = DroolsConstants.FieldType.BASE)
    public String ext3;
    @FieldName(name="一般病房住院天数",type = DroolsConstants.FieldType.BASE)
    public int bhsDays;     //一般病房住院天数
    @FieldName(name="加护病房住院天数",type = DroolsConstants.FieldType.BASE)
    public int bicuDays;    //加护病房住院天数



}
