package com.drools.entity.clm;

import com.drools.common.DroolsConstants;
import com.drools.common.annotation.EntityName;
import com.drools.common.annotation.FieldName;
import lombok.Data;

import java.util.List;

@Data
@EntityName(name="赔案保单险种信息")
public class Plan {
    @FieldName(name="被保人序号",type = DroolsConstants.FieldType.BASE)
    public String  life = "";     // LCAD.LIFE  被保人序号
    @FieldName(name="",type = DroolsConstants.FieldType.BASE)
    public String  jlife = "";     // LCAD.JLIFE
    @FieldName(name="",type = DroolsConstants.FieldType.BASE)
    public String  coverage = "";    // LCAD.COVERAGE  00 主险 01/02/..附加险
    @FieldName(name="",type = DroolsConstants.FieldType.BASE)
    public String  rider = "";    // LCAD.RIDER   00 主险 01/02/..附加险
    @FieldName(name="险种代码",type = DroolsConstants.FieldType.BASE)
    public String  crtable = "";     // LCAD.CRTABLE  险种代码
    @FieldName(name="承保起期",type = DroolsConstants.FieldType.BASE)
    public int     currFrom;      // LCAD.CURRFROM  承保起期
    @FieldName(name="承保止期",type = DroolsConstants.FieldType.BASE)
    public int     currTo;      // LCAD.CURRTO   承保止期
    @FieldName(name="险种生效日期",type = DroolsConstants.FieldType.BASE)
    public int     crrCd;      // LCAD.CRRCD   险种生效日期
    @FieldName(name="险种终止日期",type = DroolsConstants.FieldType.BASE)
    public int     rcesDte;      // LCAD.RCESDTE  险种终止日期
    @FieldName(name="险种版本生效日期",type = DroolsConstants.FieldType.BASE)
    public int     verFromDate;   // ITEM.ITMFRM 险种版本生效日期，根据条件查询得到
    @FieldName(name="险种版本终止日期",type = DroolsConstants.FieldType.BASE)
    public int     verToDate;     // ITEM.ITMTO  险种版本终止日期
    @FieldName(name="险种状态",type = DroolsConstants.FieldType.BASE)
    public String  statCode = "";    //  LCAD.STATCODE   险种状态
    @FieldName(name="保费状态",type = DroolsConstants.FieldType.BASE)
    public String  pstatCode = "";      // LCAD.PSTATCODE   保费状态
    @FieldName(name="险种审核状态",type = DroolsConstants.FieldType.BASE)
    public String  examStatus="";  // LCAD.EXAMSTATUS 险种审核状态
    @FieldName(name="险种保额",type = DroolsConstants.FieldType.BASE)
    public double sumins = 0;  // LCAD.SUMINS 单位数/险种保额
    @FieldName(name="险种给付金额",type = DroolsConstants.FieldType.BASE)
    public double clmAmt;  // LCAD.CLAMAMT 险种给付金额
    @FieldName(name="",type = DroolsConstants.FieldType.BASE)
    public int     jobNo;      // LCAD.JOBNO 99999999 代表被保险人非事故人本人 0表示本人
    @FieldName(name="险种产品",type = DroolsConstants.FieldType.BASE)
    public String  prdTyp = "";     // LCCK.PRDTYP  险种产品
    @FieldName(name="险种分组",type = DroolsConstants.FieldType.BASE)
    public String  compGrp = "";     //LCCK.COMPGRP   险种分组
    @FieldName(name="险种给付类型",type = DroolsConstants.FieldType.BASE)
    public String  trecTyp = "";     // LCCK.TRECTYP  险种给付类型
    @FieldName(name="险种类型",type = DroolsConstants.FieldType.BASE)
    public String  statFund = "";     // LCCK.STAT_FUND 险种类型  M主险/R附加险/U投连险
    @FieldName(name="险种大类",type = DroolsConstants.FieldType.BASE)
    public String  statSect = "";     // LCCK.STAT_SECT 险种大类 H1-医疗险/不为H1-是非医疗险
    @FieldName(name="家庭型保单记录",type = DroolsConstants.FieldType.BASE)
    public String  famType = "";     // LCCK.FAMTYPE 家庭型保单记录
    @FieldName(name="是否新规定",type = DroolsConstants.FieldType.BASE)
    public String  flag01 = "";     // LCCK.FLAG01  是否新规定  Y/N
    @FieldName(name="等待期间",type = DroolsConstants.FieldType.BASE)
    public int     waitProd;      // LCCK.WAITPROD  等待期间
    @FieldName(name="每计划值",type = DroolsConstants.FieldType.BASE)
    public double  valuePer;      // LCCK.VALUEPER  每计划值
    @FieldName(name="",type = DroolsConstants.FieldType.BASE)
    public String levelTyp = "";  // LCCK.LEVELTYP H一般实支实付/G重症费用/I住院津贴/J手术津贴/A/F/B/D/E
    @FieldName(name="险种排序分组",type = DroolsConstants.FieldType.BASE)
    public String  orderCompGrp = "";     //LCCR.COMPGRP   险种排序分组，TRECTYP='1'
    @FieldName(name="豁免险种标记",type = DroolsConstants.FieldType.BASE)
    public String  prop01 = "";         // LCHJ.PROP01 豁免险种标记 PROP01=01
    @FieldName(name="保障类型",type = DroolsConstants.FieldType.BASE)
    public String compType = "";  // LCCJ.COMPTYP 保障类型
    @FieldName(name="清算",type = DroolsConstants.FieldType.BASE)
    public String cleanFlag = "";  // LCJM.FLAG01 清算
    @FieldName(name="等待期间",type = DroolsConstants.FieldType.BASE)
    public int waitReins;  // 等待期间

    @FieldName(name="责任数组",listCls="com.drools.entity.clm.Benefit",type = DroolsConstants.FieldType.COLLECT)
    public List<Benefit> benefits;  // 责任数组
    public int benefitLength;
    public String hasFormula = "N"; // 险种是否有公式
    public String formulaNext = ""; // 是否继续公式判断
    public String nextStep = "";    //  下一步标志
    public int  currFrom2;      //COVR.CURRFROM
    public int  currTo2;        // COVR.CURRTO
    public int  planSuffix2;     // COVR.PLAN_SUFFIX
    public String statCode2 = ""; // COVR.STATCODE
    public int  currFrom3;      //COVR.CURRFROM
    public int  currTo3;        // COVR.CURRTO
    public int  planSuffix3;     // COVR.PLAN_SUFFIX
    public String statCode3 = ""; // COVR.STATCODE
    public String  covrStatFund = "";     // COVR.STAT_FUND
    public String  covrStatSect = "";     // COVR.STAT_SECT
    public int tranNo;  // 险种排序权值，初始化根据compGrp取LCCV.TRANNO02，有LCIE时在规则引擎中更新为LCIE的TRANNO，
    public int     formulaCrrCd; // 用于非医疗险审核的crrCd
    public int     eventDte;   // LCFE.PNDATE02 事故认定日(新需求，由规则引擎判断并保存)
    public int computingSeq = 0;  // 险种计算顺序，从1开始，合并的取最先一个险种的
    public String paiedFlag = "N";  // 险种是否已经赔付 Y是/N否
    public double compareAmt = 0; // 比较金额1
    public double compareAmt2 = 0; // 比较金额2
    public int notPayDays;  // 免赔天数
    public double notPayAmt; // 免赔额
    public double currentNotPayAmt; // 本次免赔额
    public double tempNotPayAmt; // 免赔额
    public double tempNotPayAmt2; // 免赔额
    public int tempNotPayDays;  // 免赔天数
    public int tempNotPayDays2;  // 免赔天数
    public String computingType = "X";  // 合并H 先期X(缺省)
    public String mergeNo = "";  // 合并单位数组号，一个UUID
    public double exgraPct = 0; // LCAD.EXGRAPCT 融通比例
    public double protocolRate = 1.0; // LCFE.XAMT22 协议比例
    public String  excludeBenefitGroup = "";   // LCKM.BENCODETYP  除外责任分组号
    public List<String>  excludeBenefits;     // LCKO.EXCBENCODE  除外责任
    public List<String>  excludeIllCodes;     // 除外伤病代码 2010/5/19增加
    public List<String>  excludeAcdtCodes;    // 除外意外事故码 2010/6/10增加
    public List<String>  excludeSurgeryCodes; // 除外意外手术码 2010/6/10增加
    public List<String>  excludeEventPlaces;  // 除外意外事故地 2010/6/10增加
    public int      excludeAge = 0;          // 除外年龄 ,取的天数  2010/6/10增加
    public String excludeFlag = "N";      // 除外责任标志，判断险种有无除外的责任/伤病代码
    public double M01; // LCJI.XAMT17 基本保额
    public double M02; // LCJI.XAMT11 保单现金价值
    public double M03; // LCJI.XAMT18 保额
    public double M04; // LCJI.XAMT19 当年度保险金额
    public double M05; // LCJI.XAMT12 已领取生存保险金总额
    public double M06; // LCJI.XAMT09 保单账户值
    public double M07; // LCJI.XAMT10 已领取保单账户值
    public double M08; // LCJI.XAMT14 累计已缴保费
    public double M09; // LCJI.XAMT14 累计主合同已缴保费
    public double M10; // LCJI.XAMT21 累计所缴本附加合同保险费
    public double M11; // LCJI.XAMT22 年缴保费
    public double M12; // LCJI.XAMT23 所缴保费
    public double M13; // LCJI (XAMT24 - CRRCD ) / 10000 缴费期
    public double M14; //
    public double M15; //
    public double M16; //
    public double M17; //
    public double M18; //
    public double M19; //
    public double M20; // LCJI.XAMT03 欠缴保费
    public double M21; // LCJI.XAMT04 其他欠缴保费
    public double M22; // LCJI.XAMT05 垫缴保费
    public double M23; // LCJI.XAMT06 垫缴保费之利息
    public double M24; // LCJI.XAMT07 保单借款
    public double M25; // LCJI.XAMT08 借款利息
    public double M26; // LCJI.XAMT29 累计应缴保费
    public double M27; // LCJI.XAMT14 累计已缴保费
    public double M28; // LCJI.XAMT15 现金红利
    public double M29; // LCJI.XAMT27 保单年度
    public double M30; //
    public double M31; //
    public double M32; // lcfe.xamt20 首次理赔当年度保额
    public double M33; // 应领祝寿金
    public double M34; // 累积提取手续费
    public double M35; // 累积提取手续费
    public double M36; // 主险首年期缴标准保费
    public double M37;//累计已领取特别生存金
    public double M38;//公式代码M38（累计已领取年金）：指身故前累计已领取年金的总额
    public double M44;//保险期间内已缴保费 fej 20180517
    public double M45;//主合同标准保费  fej 20180517
    public double M46;//累积红利保险金额 add by yinruirong 20180903 金雪球需求
    public double M41;//累积红利保险金额 add by yinruirong 20180903 金雪球需求
    public double M40;//累积红利保险金额 add by yezhiyong 20181021 金雪球需求
    public double firstClaimM04;  // 首次理赔当年度保险金额
    public String  compareFlag = "N";  // 险种责任赔付比较标记,Y表示有比较过
    public String  compareFlag2 = "N";
    public String  compareFlag3 = "N";
    public String finalSelectForH08 = "0"; // 针对H08赔付其他住院，先判断其他住院是自费还是其他选择
    public Policy policy;
    public double orderSumins = 0;
    public String  orderFlag = "N";
    public int  mergeSeq = 0;
    public String ext1;
    public String ext2;
    public String ext3;
    public int     assumeBgnDate;   // 本次理赔保单年度起期，根据事故认定日计算
    public int     assumeEndDate;   // 本次理赔保单年度止期
    public String newFlag = "N";    //新标准险种
    public String preFlag = "N";
}
