package com.drools.entity.clm;

import com.drools.common.DroolsConstants;
import com.drools.common.annotation.EntityName;
import com.drools.common.annotation.FieldName;
import lombok.Data;

import java.util.List;

@Data
@EntityName(name="历史险种信息")
public class HistoryPlan {
    @FieldName(name="理赔号",type = DroolsConstants.FieldType.BASE)
    public String  clmnum = "";        //LCAD.CLMNUM 理赔号（小雨伞产品新增）
    @FieldName(name="保单号",type = DroolsConstants.FieldType.BASE)
    public String  chdrnum = "";      // LCAD.CHDRNUM 保单号（小雨伞产品新增）
    @FieldName(name="被保人序号",type = DroolsConstants.FieldType.BASE)
    public String  life = "";         // LCAD.LIFE  被保人序号
    @FieldName(name="jlife",type = DroolsConstants.FieldType.BASE)
    public String  jlife = "";        // LCAD.JLIFE
    @FieldName(name="coverage",type = DroolsConstants.FieldType.BASE)
    public String  coverage = "";     // LCAD.COVERAGE  00 主险 01/02/..附加险
    @FieldName(name="rider",type = DroolsConstants.FieldType.BASE)
    public String  rider = "";        // LCAD.RIDER   00 主险 01/02/..附加险
    @FieldName(name="险种代码",type = DroolsConstants.FieldType.BASE)
    public String  crtable = "";      // LCAD.CRTABLE  险种代码
    @FieldName(name="险种状态",type = DroolsConstants.FieldType.BASE)
    public String  statCode = "";     // LCAD.STATCODE   险种状态
    @FieldName(name="保费状态",type = DroolsConstants.FieldType.BASE)
    public String  pstatCode = "";    // LCAD.PSTATCODE   保费状态
    @FieldName(name="险种审核状态",type = DroolsConstants.FieldType.BASE)
    public String  examStatus = "";   // LCAD.EXAMSTATUS 险种审核状态
    @FieldName(name="险种给付金额",type = DroolsConstants.FieldType.BASE)
    public double clmAmt;            // LCAD.CLAMAMT 险种给付金额
    @FieldName(name="险种系统判断续赔标记",type = DroolsConstants.FieldType.BASE)
    public String  sysInd = "N";      // LCAH.FLAG01 险种系统判断续赔标记
    @FieldName(name="险种人工干预标记",type = DroolsConstants.FieldType.BASE)
    public String  joinInd = "N";     // LCAH.JOININD 险种人工干预标记
    @FieldName(name="险种非续赔但要是否写入LCAH标记",type = DroolsConstants.FieldType.BASE)
    public String  saveInd = "N";     // 险种非续赔但要是否写入LCAH标记 Y-写入/N-不写入
    @FieldName(name="险种分组",type = DroolsConstants.FieldType.BASE)
    public String  compGrp = "";     //LCCK.COMPGRP   险种分组
    @FieldName(name="历史就医信息长度",type = DroolsConstants.FieldType.BASE)
    public int     eventDte;   // LCFE.PNDATE02

    @FieldName(name="险种历史赔付责任",listCls="com.drools.entity.clm.Surgery",type = DroolsConstants.FieldType.COLLECT)
    public List<HistoryBenefit> benefits; // 险种历史赔付责任
    @FieldName(name="险种历史赔付责任长度",type = DroolsConstants.FieldType.BASE)
    public int     benefitLength;
    @FieldName(name="本次理赔保单年度起期",type = DroolsConstants.FieldType.BASE)
    public int     assumeBgnDate;   // 本次理赔保单年度起期
    @FieldName(name="本次理赔保单年度止期",type = DroolsConstants.FieldType.BASE)
    public int     assumeEndDate;   // 本次理赔保单年度止期

    public HistoryClaim claim;        // 险种所属赔案
}
