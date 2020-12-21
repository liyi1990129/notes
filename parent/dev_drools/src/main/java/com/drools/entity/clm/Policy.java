package com.drools.entity.clm;

import com.drools.common.DroolsConstants;
import com.drools.common.annotation.EntityName;
import com.drools.common.annotation.FieldName;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@EntityName(name="赔案保单信息")
public class Policy {
    @FieldName(name="保单号码",type = DroolsConstants.FieldType.BASE)
    public String  chdrNum = "";     // LCAC.CHDRNUM 保单号码
    @FieldName(name="保单所属分公司",type = DroolsConstants.FieldType.BASE)
    public String  chdrCoy = "";     // CHDR.CHDRCOY 保单所属分公司
    @FieldName(name="主被保险人",type = DroolsConstants.FieldType.BASE)
    public String  clntNum = "";     // LCAC.CLNTNUM  主被保险人
    @FieldName(name="事故人与主被保人关系",type = DroolsConstants.FieldType.BASE)
    public String  relation = "";    // LCAC.RELATION 事故人与主被保人关系 I/S/C/O : 本人/配偶/子女/其他
    @FieldName(name="保单生效日期",type = DroolsConstants.FieldType.BASE)
    public int     currFrom;    // LCAC.CURRFROM 保单生效日期
    @FieldName(name="保单终止日期",type = DroolsConstants.FieldType.BASE)
    public int     currTo;      // LCAC.CURRTO  保单终止日期
    @FieldName(name="保单生效日",type = DroolsConstants.FieldType.BASE)
    public int     occDate;     // LCAC.OCCDATE 保单生效日
    @FieldName(name="保单状态",type = DroolsConstants.FieldType.BASE)
    public String  statCode = "";    // LCAC.STATCODE  保单状态
    @FieldName(name="保费状态",type = DroolsConstants.FieldType.BASE)
    public String  pstatCode = "";   // LCAC.PSTATCODE  保费状态
    @FieldName(name="复效日期",type = DroolsConstants.FieldType.BASE)
    public int     reinsDate;   // LCAC.REINSDATE  复效日期
    @FieldName(name="理赔审核状态",type = DroolsConstants.FieldType.BASE)
    public String  examStatus = "";  // LCAC.EXAMSTATUS 理赔审核状态 NA-非理赔项目/DC-拒赔/PE-未决/EL-适格/EX-融通/CO-和解
    @FieldName(name="记录类型",type = DroolsConstants.FieldType.BASE)
    public String  trecTyp = "";     // LCAC.TRECTYP 记录类型 N-正常给付/	A-留意案件/E-融通给付/D-拒赔/W-撤回/T-解除附约/R-解除主契约/C-和解/F-非理赔项目
    @FieldName(name="投保人",type = DroolsConstants.FieldType.BASE)
    public String  cownNum = "";     // CHDR.COWNNUM    投保人
    @FieldName(name="付款人",type = DroolsConstants.FieldType.BASE)
    public String  payrNum = "";     // CHDR.PAYRNUM    付款人
    public String[]  lifCnum;     // LIFE.LIFCNUM  被保人
    @FieldName(name="给付金额",type = DroolsConstants.FieldType.BASE)
    public double reimbursed;  // LCAC.REIMBURSED 给付金额
    public String  manualAdd = "";   // LCAC.MANUALADD  人工新增保单号码  Y-人工输入/N-系统索引/X-无附约
    public boolean isEubb;  // EUBB表是否有'01'数据
    public boolean isLoan;  // LOAN表是否有数据
    @FieldName(name="批注标记",type = DroolsConstants.FieldType.BASE)
    public String  noteFlag = "N";  // 通用审核用，批注标记
    @FieldName(name="复效标记",type = DroolsConstants.FieldType.BASE)
    public String  reindFlag = "N";  // 通用审核用，复效标记
    @FieldName(name="pcsindFlag",type = DroolsConstants.FieldType.BASE)
    public String  pcsindFlag = "N";  // 通用审核用，
    @FieldName(name="有无借款标记",type = DroolsConstants.FieldType.BASE)
    public String  loanFlag = "N";  // 通用审核用，有无借款标记
    public String  nextStep = ""; // 通用审核用，用于标示保单是否符合审核条件
    public int     ptDate;   // PAYR.PTDATE
    public String  statFund = "";   // COVR.STAT_FUND 保单主险的STAT_FUND
    public int     benBillDate;  // COVR.BEN_BILL_DATE 保单主险的BEN_BILL_DATE
    @FieldName(name="受理日",type = DroolsConstants.FieldType.BASE)
    public int     hpropDte;   // HPAD.HPROPDTE 受理日
    @FieldName(name="核保通过日",type = DroolsConstants.FieldType.BASE)
    public int     huwdcDte;   // HPAD.HUWDCDTE 核保通过日
    @FieldName(name="已领取生存金次数",type = DroolsConstants.FieldType.BASE)
    public int     payres; // 已领取生存金次数,来自表PAYRES1，PAYRES='COU'
    @FieldName(name="本次理赔保单年度起期",type = DroolsConstants.FieldType.BASE)
    public int     assumeBgnDate;   // 本次理赔保单年度起期
    @FieldName(name="本次理赔保单年度止期",type = DroolsConstants.FieldType.BASE)
    public int     assumeEndDate;   // 本次理赔保单年度止期
    @FieldName(name="保单险种信息",listCls="com.drools.entity.clm.Plan",type = DroolsConstants.FieldType.COLLECT)
    public List<Plan> plans;  // 保单险种
    public int     planLength;
    @FieldName(name="保单险种信息",listCls="com.drools.entity.clm.Endorse",type = DroolsConstants.FieldType.COLLECT)
    public List<Endorse> endorses;  // 保单批改记录,PTRN
    public int     endorseLength;
    @FieldName(name="保单备注信息",listCls="com.drools.entity.clm.PolicyNote",type = DroolsConstants.FieldType.COLLECT)
    public List<PolicyNote> notepfs; // NOTEPF表数据
    public int     notepfLength;
    @FieldName(name="保单历史赔付",listCls="com.drools.entity.clm.HistoryClaim",type = DroolsConstants.FieldType.COLLECT)
    public List<HistoryClaim> historyClaims; // 保单历史赔付
    @FieldName(name="保单历史赔付次数",type = DroolsConstants.FieldType.BASE)
    public int     historyClaimLength;
    public Map benefitGroups = new HashMap(); // 保存责任分组限额，BenefitGroup对象
    public int benefitGroupLength = 0;
    public String[] benefitGroupKeys ;

    // 扩展属性
    public String ext1;
    public String ext2;
    @FieldName(name="是否移动端案件",type = DroolsConstants.FieldType.BASE)
    public String ext3;
    public int  lapsfDate = 0;   // 失效日
    public int proStartDte = 0;     //保障起始日
    public int proEndDte;		    //保障截止日
    public int anbAtCcd; //被保人投保年龄
    public int crrcd; //周年日
}
