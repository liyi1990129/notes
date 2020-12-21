package com.drools.entity.clm;

import com.drools.common.DroolsConstants;
import com.drools.common.annotation.EntityName;
import com.drools.common.annotation.FieldName;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@EntityName(name="保单历史赔案信息")
public class HistoryClaim {
    @FieldName(name="赔案号",type = DroolsConstants.FieldType.BASE)
    public String  clmnum = "";     // LCAA.CLMNUM 赔案号
    @FieldName(name="赔案分公司",type = DroolsConstants.FieldType.BASE)
    public String  clmcoy = "";     // LCAA.CLMCOY  赔案分公司，一位字符
    @FieldName(name="分支机构代码",type = DroolsConstants.FieldType.BASE)
    public String  branch = "";     // LCAA.BRANCH  分支机构代码
    @FieldName(name="事故人客户号",type = DroolsConstants.FieldType.BASE)
    public String  clntNum = "";    // LCAA.CLNTNUM 事故人客户号
    @FieldName(name="营销员ID",type = DroolsConstants.FieldType.BASE)
    public String  agtId = "";      // LCAA.AGTID  营销员ID
    @FieldName(name="审核人员代号",type = DroolsConstants.FieldType.BASE)
    public String  assessor = "";   // LCAA.ASSESSOR  审核人员代号
    @FieldName(name="部门代号",type = DroolsConstants.FieldType.BASE)
    public String  deptCd = "";     // LCAA.DEPTCD    部门代号
    @FieldName(name="案件型态",type = DroolsConstants.FieldType.BASE)
    public String  caseType = "";   // LCAA.CASETYPE 案件型态 N-一般处理件/R-报备件/C-报案件
    @FieldName(name="理赔型态",type = DroolsConstants.FieldType.BASE)
    public String  claimType = "";  // LCAA.CLAIMTYPE 理赔型态 D身故/T全残/P部残/C重疾/N一般医疗/S慰问金
    @FieldName(name="审核状态",type = DroolsConstants.FieldType.BASE)
    public String  examStatus = ""; // LCAA.EXAMSTATUS 审核状态
    @FieldName(name="理赔处理状态",type = DroolsConstants.FieldType.BASE)
    public String  proceCode = "";  // LCAA.PROCECODE 理赔处理状态
    @FieldName(name="理赔给付金额",type = DroolsConstants.FieldType.BASE)
    public double clamAmt;  // LCAA.CLAMAMT 理赔给付金额
    @FieldName(name="事故认定日",type = DroolsConstants.FieldType.BASE)
    public int     eventDte;   // LCAA.EVENTDTE 事故认定日
    @FieldName(name="事故发生地",type = DroolsConstants.FieldType.BASE)
    public String  eventPlace = "";  // LCAY.CODE01  事故发生地
    @FieldName(name="意外事故代码",type = DroolsConstants.FieldType.BASE)
    public String  acdtCode = "";   // LCAA.ACDTCODE  意外事故代码
    @FieldName(name="意外事故日",type = DroolsConstants.FieldType.BASE)
    public int     acdtDte;   // LCAA.ACDTDTE 意外事故日
    @FieldName(name="身故/残疾/重大疾病认定日",type = DroolsConstants.FieldType.BASE)
    public int     criticalDte;// LCAA.CRITICALDT  身故/残疾/重大疾病认定日
    @FieldName(name="受理日期",type = DroolsConstants.FieldType.BASE)
    public int     submitDte; // LCAA.SUBMITDTE 受理日期
    @FieldName(name="报备日期",type = DroolsConstants.FieldType.BASE)
    public int     reportDte;  // LCAA.REPORTDTE 报备日期
    @FieldName(name="理赔处理日期",type = DroolsConstants.FieldType.BASE)
    public int     proceDate;  // LCAA. PROCEDATE  理赔处理日期
    @FieldName(name="核准日期",type = DroolsConstants.FieldType.BASE)
    public int     apprDate;    // LCAA.APPRDATE  核准日期
    @FieldName(name="核准人员",type = DroolsConstants.FieldType.BASE)
    public String     apprCode = "";  // LCAA.APPRCODE  核准人员
    @FieldName(name="延滞利息之代扣税额",type = DroolsConstants.FieldType.BASE)
    public double xAmt;  // LCAA.XAMT     延滞利息之代扣税额
    @FieldName(name="延滞利息",type = DroolsConstants.FieldType.BASE)
    public double definter;  // LCAA.DEFINTER     延滞利息
    @FieldName(name="给付净额",type = DroolsConstants.FieldType.BASE)
    public double payAmt;  // LCAA.PAYAMT     给付净额  (理赔给付金额 + 给付项目 – 代扣项目 + 延滞利息–代扣税额)
    @FieldName(name="代扣项目",type = DroolsConstants.FieldType.BASE)
    public double debitAmout;  // LCAA.DEBIT_AMOUNT     代扣项目,代扣应缴保费、代扣贷款本息等
    @FieldName(name="保单审核状态",type = DroolsConstants.FieldType.BASE)
    public String  policyExamStatus = "";  // LCAC.EXAMSTATUS 保单审核状态
    @FieldName(name="保单结案型态",type = DroolsConstants.FieldType.BASE)
    public String  policyTrecTyp = "";     // LCAC.TRECTYP    保单record type 保单结案型态:N-正常给付/A-留意案件/E-融通给付/D-拒赔/W-撤回/T-解除附约/R-解除主契约/C-和解/F-非理赔项目
    @FieldName(name="保单给付金额",type = DroolsConstants.FieldType.BASE)
    public double policyReimbursed;       // LCAC.REIMBURSED 保单给付金额
    @FieldName(name="本次理赔保单年度起期",type = DroolsConstants.FieldType.BASE)
    public int     assumeBgnDate;   // 本次理赔保单年度起期
    @FieldName(name="本次理赔保单年度止期",type = DroolsConstants.FieldType.BASE)
    public int     assumeEndDate;   // 本次理赔保单年度止期
    @FieldName(name="加总手术比例",type = DroolsConstants.FieldType.BASE)
    public double  surgPct;      // LCAB.SURGPCT 加总手术比例
    @FieldName(name="本次手术次数",type = DroolsConstants.FieldType.BASE)
    public int     surgTimes = 0;  // 本次手术次数
    @FieldName(name="新旧系统数据标记",type = DroolsConstants.FieldType.BASE)
    public String  sysFlag = "";  // LCAY.FLAG08  新旧系统数据标记 Y-新系统赔案/N-老PB系统赔案

    public Map surgMap = new HashMap(); // 本次手术信息MAP，由"就医序号_日期_序号"为key，保存手术比例，同一次手术保存的最高比例

    @FieldName(name="历史保单险种信息",listCls="com.drools.entity.clm.HistoryPlan",type = DroolsConstants.FieldType.COLLECT)
    public List<HistoryPlan> plans;  // 历史保单险种信息
    @FieldName(name="历史保单险种信息长度",type = DroolsConstants.FieldType.BASE)
    public int     planLength;

    @FieldName(name="历史就医信息",listCls="com.drools.entity.clm.HistoryDoctor",type = DroolsConstants.FieldType.COLLECT)
    public List<HistoryDoctor> doctors;
    @FieldName(name="历史就医信息长度",type = DroolsConstants.FieldType.BASE)
    public int    doctorLength;

    public Policy policy;
}
