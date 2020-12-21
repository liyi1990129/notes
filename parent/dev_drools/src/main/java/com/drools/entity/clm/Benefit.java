package com.drools.entity.clm;

import com.drools.common.DroolsConstants;
import com.drools.common.annotation.EntityName;
import com.drools.common.annotation.FieldName;
import com.drools.entity.children.DfmtGroup;
import lombok.Data;

import java.util.*;

@Data
@EntityName(name="责任信息")
public class Benefit {
    @FieldName(name="责任代码",type = DroolsConstants.FieldType.BASE)
    public String  benCode = "";  // LCCM.BENCODE 责任代码，根据COMPGRP查出
    @FieldName(name="责任的赔付金额",type = DroolsConstants.FieldType.BASE)
    public double payAmt;  // LCAR.PAYAMT 责任的赔付金额(本次)
    @FieldName(name="责任比较标志",type = DroolsConstants.FieldType.BASE)
    public String  compareFlag = "N"; // 责任比较标志，表示责任在收据算费时是否参与自费与其他的结果比较 Y/N，可能是与其他责任比较，也可能是自身比较
    @FieldName(name="是否有保额限额分组",type = DroolsConstants.FieldType.BASE)
    public String  isGroupAmt = "";    // 是否有保额限额分组
    @FieldName(name="保额限额分组组号",type = DroolsConstants.FieldType.BASE)
    public String  groupAmtNo = "";    // 保额限额分组组号
    @FieldName(name="是否有保额限额分组2",type = DroolsConstants.FieldType.BASE)
    public String  isGroupAmt2 = "";    // 是否有保额限额分组2
    @FieldName(name="保额限额分组组号2",type = DroolsConstants.FieldType.BASE)
    public String  groupAmtNo2 = "";    // 保额限额分组组号2
    @FieldName(name="是否有保额年度限额分组",type = DroolsConstants.FieldType.BASE)
    public String  isGroupYear = "";    // 是否有保额年度限额分组
    @FieldName(name="保额年度限额分组组号",type = DroolsConstants.FieldType.BASE)
    public String  groupYearNo = "";    // 保额年度限额分组组号
    @FieldName(name="是否有保额限额分组2",type = DroolsConstants.FieldType.BASE)
    public String  isGroupYear2 = "";    // 是否有保额限额分组2
    @FieldName(name="保额限额分组组号2",type = DroolsConstants.FieldType.BASE)
    public String  groupYearNo2 = "";    // 保额限额分组组号2
    @FieldName(name="是否有保额限额分组",type = DroolsConstants.FieldType.BASE)
    public String  isGroupAccident = "";    // 是否有保额限额分组
    @FieldName(name="保额事故限额分组组号",type = DroolsConstants.FieldType.BASE)
    public String  groupAccidentNo = "";    // 保额事故限额分组组号
    @FieldName(name="是否有保额限额分组2",type = DroolsConstants.FieldType.BASE)
    public String  isGroupAccident2 = "";    // 是否有保额限额分组2
    @FieldName(name="保额事故限额分组组号2",type = DroolsConstants.FieldType.BASE)
    public String  groupAccidentNo2 = "";    // 保额事故限额分组组号2
    @FieldName(name="是否有次数分组",type = DroolsConstants.FieldType.BASE)
    public String  isGroupTime = "";    // 是否有次数分组
    @FieldName(name="次数分组组号",type = DroolsConstants.FieldType.BASE)
    public String  groupTimeNo = "";    // 次数分组组号
    @FieldName(name="是否有其他分组",type = DroolsConstants.FieldType.BASE)
    public String  isGroupOther = "";    // 是否有其他分组
    @FieldName(name="其他分组组号",type = DroolsConstants.FieldType.BASE)
    public String  groupOtherNo = "";    // 其他分组组号
    @FieldName(name="isSelFirst",type = DroolsConstants.FieldType.BASE)
    public String isSelFirst = "Y";
    @FieldName(name="isSelSencod",type = DroolsConstants.FieldType.BASE)
    public String isSelSencod = "N";
    @FieldName(name="责任最最终算费选择",type = DroolsConstants.FieldType.BASE)
    public String finalSelect = "1";  // 责任最最终算费选择 1/2
    @FieldName(name="是否优先责任",type = DroolsConstants.FieldType.BASE)
    public String  isPrior = "N";    // 是否优先责任
    @FieldName(name="责任是否赔付标识",type = DroolsConstants.FieldType.BASE)
    public String  payFlag = "Y";  // 责任是否赔付标识 Y/N
    @FieldName(name="责任是否已赔付标识",type = DroolsConstants.FieldType.BASE)
    public String  paiedFlag = "N";  // 责任是否已赔付标识 Y/N
    @FieldName(name="优先级",type = DroolsConstants.FieldType.BASE)
    public int tranNo;                     // 优先级
    @FieldName(name="责任是否比较保额",type = DroolsConstants.FieldType.BASE)
    public String  isAmt = "N";         // 责任是否比较保额
    @FieldName(name="责任是否比较年度保额",type = DroolsConstants.FieldType.BASE)
    public String  isAmtYear = "N";     // 责任是否比较年度保额
    @FieldName(name="责任是否比较事故保额",type = DroolsConstants.FieldType.BASE)
    public String  isAmtAccident = "N"; // 责任是否比较事故保额
    @FieldName(name="责任保额",type = DroolsConstants.FieldType.BASE)
    public double amt;  // 责任保额
    @FieldName(name="责任年度保额",type = DroolsConstants.FieldType.BASE)
    public double amtYear;  // 责任年度保额
    @FieldName(name="责任事故保额",type = DroolsConstants.FieldType.BASE)
    public double amtAccident;  // 责任事故保额
    @FieldName(name="任的原始年度/事故保额",type = DroolsConstants.FieldType.BASE)
    public double orginalAmtYear;  // 责任的原始年度/事故保额 ，用于保存到LCEG
    @FieldName(name="本次运算前责任的剩余年度/事故保额",type = DroolsConstants.FieldType.BASE)
    public double amtPaied;   // 本次运算前责任的剩余年度/事故保额
    @FieldName(name="责任的保单年度余额",type = DroolsConstants.FieldType.BASE)
    public double yearRemainder; // 责任的保单年度余额
    @FieldName(name="责任是否比较赔付次数",type = DroolsConstants.FieldType.BASE)
    public String  isTime = "N";         // 责任是否比较赔付次数
    @FieldName(name="责任是否比较年度赔付次数",type = DroolsConstants.FieldType.BASE)
    public String  isTimeYear = "N";     // 责任是否比较年度赔付次数
    @FieldName(name="责任是否比较事故赔付次数",type = DroolsConstants.FieldType.BASE)
    public String  isTimeAccident = "N"; // 责任是否比较事故赔付次数
    @FieldName(name="责任赔付次数",type = DroolsConstants.FieldType.BASE)
    public int time = 0;  // 责任赔付次数
    @FieldName(name="责任年度赔付次数",type = DroolsConstants.FieldType.BASE)
    public int timeYear = 0;  // 责任年度赔付次数
    @FieldName(name="责任的事故赔付次数",type = DroolsConstants.FieldType.BASE)
    public int timeAccident = 0;  // 责任的事故赔付次数
    @FieldName(name="责任是否比较意外赔付次数",type = DroolsConstants.FieldType.BASE)
    public String  isAccidentTime = "N"; // 责任是否比较意外赔付次数
    @FieldName(name="责任的意外赔付次数",type = DroolsConstants.FieldType.BASE)
    public int accidentTime = 0;  // 责任的意外赔付次数
    @FieldName(name="责任是否比较医疗赔付次数",type = DroolsConstants.FieldType.BASE)
    public String  isMedicalTime = "N"; // 责任是否比较医疗赔付次数
    @FieldName(name="责任的医疗赔付次数",type = DroolsConstants.FieldType.BASE)
    public int medicalTime = 0;  // 责任的医疗赔付次数
    @FieldName(name="责任是否比较手术赔付次数",type = DroolsConstants.FieldType.BASE)
    public String  isSurgeryTime = "N";         // 责任是否比较手术赔付次数
    @FieldName(name="责任的手术赔付次数",type = DroolsConstants.FieldType.BASE)
    public int surgeryTime = 0;  // 责任的手术赔付次数
    @FieldName(name="责任历史手术赔付次数累计",type = DroolsConstants.FieldType.BASE)
    public int historySurgeryTime;  // 责任历史手术赔付次数累计
    @FieldName(name="责任是否比较赔付天数",type = DroolsConstants.FieldType.BASE)
    public String  isDay = "N";         // 责任是否比较赔付天数
    @FieldName(name="责任是否比较年度赔付天数",type = DroolsConstants.FieldType.BASE)
    public String  isDayYear = "N";     // 责任是否比较年度赔付天数
    @FieldName(name="责任是否比较事故赔付天数",type = DroolsConstants.FieldType.BASE)
    public String  isDayAccident = "N"; // 责任是否比较事故赔付天数
    @FieldName(name="责任赔付天数",type = DroolsConstants.FieldType.BASE)
    public int day = 0;  // 责任赔付天数
    @FieldName(name="责任年度赔付天数",type = DroolsConstants.FieldType.BASE)
    public int dayYear = 0;  // 责任年度赔付天数
    @FieldName(name="责任事故赔付天数",type = DroolsConstants.FieldType.BASE)
    public int dayAccident = 0;  // 责任事故赔付天数
    @FieldName(name="责任是否运算免赔额标记",type = DroolsConstants.FieldType.BASE)
    public String notPayAmtFlag = "Y";  // 责任是否运算免赔额标记，确认Y
    @FieldName(name="免赔天数",type = DroolsConstants.FieldType.BASE)
    public int notPayDays;  // 免赔天数 LCAQ.HSDAYS
    @FieldName(name="本次可用的免赔天数",type = DroolsConstants.FieldType.BASE)
    public int currentNotPayDays; // 本次可用的免赔天数 LCKV.XAMT03
    @FieldName(name="备注",type = DroolsConstants.FieldType.BASE)
    public String remark="";            // 备注 LCKV.REMARK
    @FieldName(name="免赔额",type = DroolsConstants.FieldType.BASE)
    public double notPayAmt; // 免赔额
    @FieldName(name="责任历史赔付的保额",type = DroolsConstants.FieldType.BASE)
    public double historyAmt;  // 责任历史赔付的保额
    @FieldName(name="责任历史的年度保额",type = DroolsConstants.FieldType.BASE)
    public double historyAmtYear;  // 责任历史的年度保额
    @FieldName(name="责任历史的事故保额",type = DroolsConstants.FieldType.BASE)
    public double historyAmtAccident;  // 责任历史的事故保额
    @FieldName(name="责任临时赔付金额",type = DroolsConstants.FieldType.BASE)
    public double tempPayAmt; // 责任临时赔付金额，用于保存非医疗险结果、医疗险收据累加结果

//	public double tempPayAmt1; // 责任临时赔付金额，用于保存非医疗险结果、医疗险收据累加结果
//	public double tempPayAmt2; // 责任临时赔付金额，用于保存非医疗险结果、医疗险收据累加结果
    @FieldName(name="用于合计每次就医的给付金额",type = DroolsConstants.FieldType.BASE)
    public double tempDoctorPayAmt; // 用于合计每次就医的给付金额
    @FieldName(name="用于合计每次就医的给付金额",type = DroolsConstants.FieldType.BASE)
    public double tempDoctorPayAmt2;  // 用于合计每次就医的给付金额(2)
    @FieldName(name="责任临时每次就医方式赔付金额",type = DroolsConstants.FieldType.BASE)
    public double tempReceiptTypePayAmt; // 责任临时每次就医方式赔付金额
    @FieldName(name="责任临时每次就医方式赔付金额",type = DroolsConstants.FieldType.BASE)
    public double tempReceiptTypePayAmt2; // 责任临时每次就医方式赔付金额(2)
    @FieldName(name="责任临时赔付金额",type = DroolsConstants.FieldType.BASE)
    public double tempReceiptPayAmt; // 责任临时赔付金额，用于每张收据的计算
    @FieldName(name="责任临时赔付金额",type = DroolsConstants.FieldType.BASE)
    public double tempReceiptPayAmt2; // 责任临时赔付金额，用于每张收据的计算
    @FieldName(name="责任临时非ICU合计分摊部分赔付金额",type = DroolsConstants.FieldType.BASE)
    public double tempReceiptNotIcuPayAmt;  // 责任临时非ICU合计分摊部分赔付金额，用于每张收据的计算
    @FieldName(name="tempReceiptNotIcuPayAmt2",type = DroolsConstants.FieldType.BASE)
    public double tempReceiptNotIcuPayAmt2;
    @FieldName(name="责任临时ICU合计部分分摊赔付金额",type = DroolsConstants.FieldType.BASE)
    public double tempReceiptIcuPayAmt;     // 责任临时ICU合计部分分摊赔付金额，用于每张收据的计算
    @FieldName(name="tempReceiptIcuPayAmt2",type = DroolsConstants.FieldType.BASE)
    public double tempReceiptIcuPayAmt2;
    @FieldName(name="责任临时保额限额",type = DroolsConstants.FieldType.BASE)
    public double tempLmtAmt; // 责任临时保额限额
    @FieldName(name="责任临时保额限额",type = DroolsConstants.FieldType.BASE)
    public double tempLmtAmt2; // 责任临时保额限额
    @FieldName(name="责任临时年度保额限额",type = DroolsConstants.FieldType.BASE)
    public double tempLmtAmtYear; // 责任临时年度保额限额
    @FieldName(name="责任临时年度保额限额",type = DroolsConstants.FieldType.BASE)
    public double tempLmtAmtYear2; // 责任临时年度保额限额
    @FieldName(name="责任临事故时保额限额",type = DroolsConstants.FieldType.BASE)
    public double tempLmtAmtAccident; // 责任临事故时保额限额
    @FieldName(name="责任临时事故保额限额",type = DroolsConstants.FieldType.BASE)
    public double tempLmtAmtAccident2; // 责任临时事故保额限额

//	public int tempLmtTime;
//	public int tempLmtTime2;
//	public int tempLmtTimeYear;
//	public int tempLmtTimeYear2;
//	public int tempLmtTimeAccident;
//	public int tempLmtTimeAccident2;

    @FieldName(name="定额给付责任处理标记",type = DroolsConstants.FieldType.BASE)
    public String dayFlag = ""; // 定额给付责任处理标记
    @FieldName(name="责任总实际赔付天数",type = DroolsConstants.FieldType.BASE)
    public int payDay = 0; // 责任总实际赔付天数
    @FieldName(name="责任总申请赔付天数",type = DroolsConstants.FieldType.BASE)
    public int reqDay = 0; // 责任总申请赔付天数
    @FieldName(name="责任每次就医临时可赔付天数",type = DroolsConstants.FieldType.BASE)
    public int tempDoctorPayDay = 0; // 责任每次就医临时可赔付天数
    @FieldName(name="收据申请金额累加",type = DroolsConstants.FieldType.BASE)
    public double receiptRequestAmt; // 收据申请金额累加，要减去扣除
    @FieldName(name="收据扣除金额累加",type = DroolsConstants.FieldType.BASE)
    public double receiptDeductAmt; // 收据扣除金额累加  LCKV.XAMT02

//	public int tempLmtDay;
//	public int tempLmtDay2;
//	public int tempLmtDayYear;
//	public int tempLmtDayYear2;
//	public int tempLmtDayAccident;
//	public int tempLmtDayAccident2;
@FieldName(name="责任赔付收据计算比例",type = DroolsConstants.FieldType.BASE)
    public double payRate = 1.0;    // 责任赔付收据计算比例，由规则得出
    //xuwei 2011-08-30 S201108127-社保外药品费赔付错误
    @FieldName(name="社保外药品费计算比例",type = DroolsConstants.FieldType.BASE)
    public double payRate02 = 1.0;    // 社保外药品费计算比例，由规则得出
    @FieldName(name="本次责任赔付公式",type = DroolsConstants.FieldType.BASE)
    public String  formulaStr = ""; // LCJJ.PARAMSTR 本次责任赔付公式
    @FieldName(name="本次责任赔付公式",type = DroolsConstants.FieldType.BASE)
    public String  calDesc = ""; // LCJJ.CALDESC 本次责任赔付公式
    @FieldName(name="实际赔付公式",type = DroolsConstants.FieldType.BASE)
    public String  tempParamStr = "";   // 实际赔付公式，初始与formulaStr相同
    @FieldName(name="本次年龄系数",type = DroolsConstants.FieldType.BASE)
    public double agePct = 1;  // LCJJ.AGEPCT 本次年龄系数
    @FieldName(name="本次伤病系数",type = DroolsConstants.FieldType.BASE)
    public double illPct = 0;  // LCJJ.ILLPCT 本次伤病系数
    @FieldName(name="医疗险实支实付的赔付比例",type = DroolsConstants.FieldType.BASE)
    public double payPct = 1;  // 医疗险实支实付的赔付比例，缺省100%
    @FieldName(name="examStatus",type = DroolsConstants.FieldType.BASE)
    public String examStatus = "";
    @FieldName(name="双倍给付标识",type = DroolsConstants.FieldType.BASE)
    public String  doubleInd = "N";  // LCAR.DOUBLEIND 双倍给付标识Y/N
    @FieldName(name="是否参与运算分组计算标志",type = DroolsConstants.FieldType.BASE)
    public String  attendGroupFlag = "Y";  // 是否参与运算分组计算标志，缺省永真
    @FieldName(name="责任计算类型",type = DroolsConstants.FieldType.BASE)
    public String  type = "";         // 责任计算类型 NM-非医疗险/MA医疗险津贴型/MR医疗险收据计算型/MF-医疗险但是不看收据
    @FieldName(name="回写LACR标记",type = DroolsConstants.FieldType.BASE)
    public String  lcarFlag = "Y";  // 回写LACR标记，Y插入/N不插入

    // 从非医疗审核 公式中设置 start
    @FieldName(name="理赔形态条件",type = DroolsConstants.FieldType.BASE)
    public String  conClmTyp = "";     // 理赔形态条件
    @FieldName(name="日期条件",type = DroolsConstants.FieldType.BASE)
    public String  conDate = "";    //  日期条件
    @FieldName(name="年龄条件",type = DroolsConstants.FieldType.BASE)
    public String  conAge = "";    //  年龄条件
    @FieldName(name="已付生存年金条件",type = DroolsConstants.FieldType.BASE)
    public String  conPaiedSave = "";    // 已付生存年金条件
    @FieldName(name="伤病代码条件",type = DroolsConstants.FieldType.BASE)
    public String  conIllcode = "";    //  伤病代码条件
    @FieldName(name="事故地点条件",type = DroolsConstants.FieldType.BASE)
    public String  conPlace = "";    //  事故地点条件
    @FieldName(name="手术代码条件",type = DroolsConstants.FieldType.BASE)
    public String  conSurgery = "";    //  手术代码条件
    @FieldName(name="手术时间条件",type = DroolsConstants.FieldType.BASE)
    public String  conSurgeryTime = "";    //  手术时间条件
    @FieldName(name="版本起期",type = DroolsConstants.FieldType.BASE)
    public int verDte01 ;//  版本起期
    @FieldName(name="版本止期",type = DroolsConstants.FieldType.BASE)
    public int verDte02 ;//版本止期
    @FieldName(name="年龄系数",type = DroolsConstants.FieldType.BASE)
    public double formulaAgePct;  // 年龄系数
    @FieldName(name="伤病系数",type = DroolsConstants.FieldType.BASE)
    public double formulaIllPct;  // 伤病系数
    @FieldName(name="公式",type = DroolsConstants.FieldType.BASE)
    public String  paramStr02 = "";   //公式
    @FieldName(name="公式",type = DroolsConstants.FieldType.BASE)
    public String  formulaCalDesc = "";   // 公式
    @FieldName(name="理赔形态结果标识",type = DroolsConstants.FieldType.BASE)
    public String  clTypeFlag = "N";    // 理赔形态结果标识
    @FieldName(name="理赔形态结果标识",type = DroolsConstants.FieldType.BASE)
    public String  clmTypeFlag = "N";    //  理赔形态结果标识
    @FieldName(name="日期结果标识",type = DroolsConstants.FieldType.BASE)
    public String  dateFlag = "N";    // 日期结果标识
    @FieldName(name="年龄结果标识",type = DroolsConstants.FieldType.BASE)
    public String  ageFlag = "N";    // 年龄结果标识
    @FieldName(name="已付生存年金结果标识",type = DroolsConstants.FieldType.BASE)
    public String  payresFlag = "N";    // 已付生存年金结果标识
    @FieldName(name="伤病代码结果标识",type = DroolsConstants.FieldType.BASE)
    public String  illCodeFlag = "N";    // 伤病代码结果标识
    @FieldName(name="事故地点结果标识",type = DroolsConstants.FieldType.BASE)
    public String  placeFlag = "N";    // 事故地点结果标识
    @FieldName(name="手术代码结果标识",type = DroolsConstants.FieldType.BASE)
    public String  surgeryFlag = "N";    // 手术代码结果标识
    @FieldName(name="手术时间结果标识",type = DroolsConstants.FieldType.BASE)
    public String  surgeryTimeFlag = "N";    // 手术时间结果标识
    @FieldName(name="用、分隔的伤病代码",type = DroolsConstants.FieldType.BASE)
    public String formulaIllcodes; // 用、分隔的伤病代码，从决策表获得
    @FieldName(name="用、分隔的事故地点代码",type = DroolsConstants.FieldType.BASE)
    public String formulaPlaces;  // 用、分隔的事故地点代码，从决策表获得
    @FieldName(name="用、分隔的手术代码",type = DroolsConstants.FieldType.BASE)
    public String formulaSurgeryCodes; // 用、分隔的手术代码，从决策表获得

    //	 从非医疗审核 公式中设置 end
    @FieldName(name="决策表设置的责任条件公式数量",type = DroolsConstants.FieldType.BASE)
    public int formulaLength; //  决策表设置的责任条件公式数量

    public String[]  illCodeArray;    // LCKJ,LCKH.ILLCODES 疾病分组，每个字符串保存一个分组，“、”分隔

    public Map doctorAmtMap = new HashMap(); // 保存责任在每次就医的总赔付金额,key:就医序号

    public Set receiptSet = new HashSet();

    public Plan plan;

    public String  othFlag = "N";  // 其他住院责任累加标记 Y/N

    //xuwei 20140505 新伤残代码需求 add
    public String deformityFlg = "N";
    public int deformityLength; //  决策表设置的新伤残责任条件公式数量
    public double deformityIllPct = 0;  // 新伤残系数
    public double  deformityAgePct = 0;  //  本组年龄系数
    public String  deformityPStr02 = "";   //新伤残公式
    public String  deformityCalDesc = "";   // 新伤残公式
    public List<DfmtGroup> deformityGroup;  // 新伤残代码分组
    public int     groupLength;  // 新伤残分组长度
    public String sameLevelFlg = "Y";//分组赔付比例是否相同
    public String upLevelFlg = "N";//+10%的标志

    //xuwei 2014-07-15 update
    public String  illcode = "";   //获取临时最高等级的伤病代码
    public String sameIllcodeFlg = "Y";//分组获取的伤病代码是否相同

    //yinruirong 2018-08-16 金悦行两全新需求 add 	start
    public String arrivalAge = "";	//到达年龄
    public String arrivalAgeFlag = "N";	//到达年龄结果标识
}
