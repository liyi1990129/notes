package com.drools.entity.clm;

import com.drools.common.DroolsConstants;
import com.drools.common.annotation.ActionMethodName;
import com.drools.common.annotation.EntityName;
import com.drools.common.annotation.FieldName;
import com.drools.entity.children.DfmtGroup;
import com.drools.entity.children.Note;
import com.drools.mapper.BenefitFormulaMapper;
import com.drools.mapper.EventDateMapper;
import com.drools.model.BenefitFormula;
import com.drools.model.EventDate;
import com.drools.service.impl.InvokeAuditServiceImpl;
import com.drools.util.ClmUtil;
import com.drools.util.SpringContextHolder;
import com.drools.util.StringUtil;
import com.drools.util.TimeUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Slf4j
@Data
@EntityName(name="赔案信息")
public class Claim {
    @FieldName(name = "赔案号", type = DroolsConstants.FieldType.BASE)
    public String clmnum = "";     // LCAA.CLMNUM  赔案号
    @FieldName(name = "赔案分公司", type = DroolsConstants.FieldType.BASE)
    public String clmcoy = "";     // LCAA.CLMCOY  赔案分公司，一位字符
    @FieldName(name = "分支机构代码", type = DroolsConstants.FieldType.BASE)
    public String branch = "";     // LCAA.BRANCH  分支机构代码
    @FieldName(name = "事故人ID", type = DroolsConstants.FieldType.BASE)
    public String secuityNo = "";  // LCAA.SECUITYNO  // 事故人ID 身份证
    @FieldName(name = "客户姓名", type = DroolsConstants.FieldType.BASE)
    public String surName = "";    // LCAA.SURNAME  客户姓名
    @FieldName(name = "事故人出生日期", type = DroolsConstants.FieldType.BASE)
    public int cltDob;     // LCAA.CLTDOB 事故人出生日期
    @FieldName(name = "事故人事故时周岁", type = DroolsConstants.FieldType.BASE)
    public int cltYear;    // 事故人事故时周岁?? 如何算
    @FieldName(name = "事故人性别", type = DroolsConstants.FieldType.BASE)
    public String cltSex = "";     // LCAA.CLTSEX  M/F
    @FieldName(name = "事故人客户号", type = DroolsConstants.FieldType.BASE)
    public String clntNum = "";    // LCAA.CLNTNUM 事故人客户号
    @FieldName(name = "多个事故人信息", type = DroolsConstants.FieldType.BASE)
    public String[] clntNumes;     // LCKY.CLNTNUM 多个事故人信息
    @FieldName(name = "受理日期", type = DroolsConstants.FieldType.BASE)
    public int submitDte; // LCAA.SUBMITDTE 受理日期
    @FieldName(name = "事故认定日", type = DroolsConstants.FieldType.BASE)
    public int eventDte;   // LCAA.EVENTDTE  事故认定日，每个险种有自己的事故认定日
    @FieldName(name = "意外发生地", type = DroolsConstants.FieldType.BASE)
    public String eventPlace = "";    // LCAY.CODE01  意外发生地
    @FieldName(name = "除外责任", type = DroolsConstants.FieldType.BASE)
    public String excludeBenefit = "";  // LCAY.CODE02  除外责任
    @FieldName(name = "新旧系统数据标记", type = DroolsConstants.FieldType.BASE)
    public String sysFlag = "";  // LCAY.FLAG08  新旧系统数据标记 Y-新系统赔案/N-老PB系统赔案
    @FieldName(name = "人工修改手术比例标志", type = DroolsConstants.FieldType.BASE)
    public String manualSurgeryFlag = "";  // LCAY.FLAG09 人工修改手术比例标志
    @FieldName(name = "意外事故日", type = DroolsConstants.FieldType.BASE)
    public int acdtDte;   // LCAA.ACDTDTE   意外事故日
    @FieldName(name = "身故/残疾/重大疾病认定日", type = DroolsConstants.FieldType.BASE)
    public int criticalDte;// LCAA.CRITICALDT  身故/残疾/重大疾病认定日
    @FieldName(name = "意外事故代码", type = DroolsConstants.FieldType.BASE)
    public String acdtCode = "";   // LCAA.ACDTCODE 意外事故代码
    @FieldName(name = "理赔型态", type = DroolsConstants.FieldType.BASE)
    public String claimType = "";  // LCAA.CLAIMTYPE 理赔型态 D身故/T全残/P部残/C重疾/N一般医疗/S慰问金
    @FieldName(name = "案件型态", type = DroolsConstants.FieldType.BASE)
    public String caseType = "";   // LCAA.CASETYPE 案件型态 N-一般处理件/R-报备件/C-报案件
    @FieldName(name = "营销员ID", type = DroolsConstants.FieldType.BASE)
    public String agtId = "";          // LCAA.AGTID  营销员ID
    @FieldName(name = "通讯处代号", type = DroolsConstants.FieldType.BASE)
    public String agencyNo = "";          // LCAA.AGENCY_NO  通讯处代号
    @FieldName(name = "营销员入职日期", type = DroolsConstants.FieldType.BASE)
    public long agtFromDate;   // AA01.DTEAPP  营销员入职日期
    @FieldName(name = "营销员离职日期", type = DroolsConstants.FieldType.BASE)
    public long agtToDate;     // AA01.DTETRM  营销员离职日期
    @FieldName(name = "报备日期", type = DroolsConstants.FieldType.BASE)
    public int reportDte;  // LCAA.REPORTDTE 报备日期
    @FieldName(name = "审核人员代号", type = DroolsConstants.FieldType.BASE)
    public String assessor = "";   // LCAA.ASSESSOR  审核人员代号
    @FieldName(name = "部门代号", type = DroolsConstants.FieldType.BASE)
    public String deptCd = "";     // LCAA.DEPTCD    部门代号
    @FieldName(name = "审核状态", type = DroolsConstants.FieldType.BASE)
    public String examStatus = ""; // LCAA.EXAMSTATUS 审核状态
    @FieldName(name = "理赔处理状态", type = DroolsConstants.FieldType.BASE)
    public String proceCode = "";  // LCAA.PROCECODE  理赔处理状态
    @FieldName(name = "理赔处理日期", type = DroolsConstants.FieldType.BASE)
    public int proceDate;  // LCAA. PROCEDATE  理赔处理日期
    @FieldName(name = "核准日期", type = DroolsConstants.FieldType.BASE)
    public int apprDate;    // LCAA.APPRDATE  核准日期
    @FieldName(name = "核准人员", type = DroolsConstants.FieldType.BASE)
    public String apprCode = "";  // LCAA.APPRCODE  核准人员
    @FieldName(name = "理赔给付金额", type = DroolsConstants.FieldType.BASE)
    public double clamAmt;  // LCAA.CLAMAMT     理赔给付金额
    @FieldName(name = "taxAmt", type = DroolsConstants.FieldType.BASE)
    public double taxAmt;  // LCAA.TAXAMT
    @FieldName(name = "延滞利息之代扣税额", type = DroolsConstants.FieldType.BASE)
    public double xAmt;  // LCAA.XAMT     延滞利息之代扣税额
    @FieldName(name = "延滞利息", type = DroolsConstants.FieldType.BASE)
    public double definter;  // LCAA.DEFINTER     延滞利息
    @FieldName(name = "给付净额", type = DroolsConstants.FieldType.BASE)
    public double payAmt;  // LCAA.PAYAMT     给付净额  (理赔给付金额 + 给付项目 – 代扣项目 + 延滞利息–代扣税额)
    @FieldName(name = "代扣项目", type = DroolsConstants.FieldType.BASE)
    public double debitAmout;  // LCAA.DEBIT_AMOUNT     代扣项目,代扣应缴保费、代扣贷款本息等
    @FieldName(name = "是否意外事故", type = DroolsConstants.FieldType.BASE)
    public String isAccidentCase = "N"; // 是否意外事故
    @FieldName(name = "是否医疗事故", type = DroolsConstants.FieldType.BASE)
    public String isMedicalCase = "N";  // 是否医疗事故


    //    @FieldName(name="保单索引",type = DroolsConstants.FieldType.BASE)
    public int idxPolicy;  // 保单索引 policy
    //    @FieldName(name="险种索引",type = DroolsConstants.FieldType.BASE)
    public int idxPlan;  // 险种索引 plan
    //    @FieldName(name="责任索引",type = DroolsConstants.FieldType.BASE)
    public int idxBenefit;  // 责任索引 benefit
    //    @FieldName(name="照会索引",type = DroolsConstants.FieldType.BASE)
    public int idxNote;  // 照会索引 note
    //    @FieldName(name="就医索引",type = DroolsConstants.FieldType.BASE)
    public int idxDoctor;  // 就医索引 doctor
    //    @FieldName(name="收据索引",type = DroolsConstants.FieldType.BASE)
    public int idxReceipt;  // 收据索引 receipt
    //    @FieldName(name="公式索引",type = DroolsConstants.FieldType.BASE)
    public int idxFormula;  // 公式索引 BenefitFormula
    //    @FieldName(name="责任分组索引",type = DroolsConstants.FieldType.BASE)
    public int idxBenGrp;  // 责任分组索引 BenefitGroup
    //    @FieldName(name="历史赔案索引",type = DroolsConstants.FieldType.BASE)
    public int idxHisClm; // 历史赔案索引 HistoryClaim
    //    @FieldName(name="历史险种索引",type = DroolsConstants.FieldType.BASE)
    public int idxHisPlan; // 历史险种索引 HistoryPlan
    //    @FieldName(name="历史责任索引",type = DroolsConstants.FieldType.BASE)
    public int idxHisBen; // 历史责任索引 HistoryBenefit
    //    @FieldName(name="历史就医索引",type = DroolsConstants.FieldType.BASE)
    public int idxHisDoc; // 历史就医索引 HistoryDoctor
    //    @FieldName(name="历史就医手术索引",type = DroolsConstants.FieldType.BASE)
    public int idxHisSur; // 历史就医手术索引 HistorySurgery
    //    @FieldName(name="批改(保全)索引",type = DroolsConstants.FieldType.BASE)
    public int idxEndorse;  // 批改(保全)索引 Endorse
    //    @FieldName(name="手术索引",type = DroolsConstants.FieldType.BASE)
    public int idxSurgery;  // 手术索引 Surgery
    //    @FieldName(name="比较险种索引",type = DroolsConstants.FieldType.BASE)
    public int idxCprPlan;  // 比较险种索引 ComparePlan
    //    @FieldName(name="运算险种索引",type = DroolsConstants.FieldType.BASE)
    public int idxCompPlan;  // 运算险种索引 Plan，运算时
    //    @FieldName(name="优先责任索引",type = DroolsConstants.FieldType.BASE)
    public int idxCompBen;  // 优先责任索引 benefit，运算时
    //    @FieldName(name="险种运算责任分组索引",type = DroolsConstants.FieldType.BASE)
    public int idxCompGrp;  // 险种运算责任分组索引
    //    @FieldName(name="运算收据索引",type = DroolsConstants.FieldType.BASE)
    public int idxCompRpt;  // 运算收据索引
    //    @FieldName(name="合并险种中险种索引索引",type = DroolsConstants.FieldType.BASE)
    public int idxMerge;  //   合并险种中险种索引索引
    //    @FieldName(name="受款人索引",type = DroolsConstants.FieldType.BASE)
    public int idxReceiver;  //  受款人索引
    //    @FieldName(name="收据类型索引",type = DroolsConstants.FieldType.BASE)
    public int idxReceiptType;  //  收据类型索引
    //    @FieldName(name="文件索引",type = DroolsConstants.FieldType.BASE)
    public int idxFile;    // 文件索引
    //    @FieldName(name="合并单位数险种责任索引",type = DroolsConstants.FieldType.BASE)
    public int idxCompPlanBen; // 合并单位数险种责任索引

    //    @FieldName(name="新伤残组索引",type = DroolsConstants.FieldType.BASE)
    public int idxDeformityGroup;//新伤残组索引
    //    @FieldName(name="今天日期",type = DroolsConstants.FieldType.BASE)
    public int today;  // 今天日期
    //    @FieldName(name="今天日期年",type = DroolsConstants.FieldType.BASE)
    public int todayYear;  // 今天日期年
    //    @FieldName(name="今天日期月",type = DroolsConstants.FieldType.BASE)
    public int todayMonth;  // 今天日期月
    //    @FieldName(name="今天日期日",type = DroolsConstants.FieldType.BASE)
    public int todayDay;  // 今天日期日
    //    @FieldName(name="规则调用用户",type = DroolsConstants.FieldType.BASE)
    public String opUser = "";  // 规则调用用户
    //    @FieldName(name="规则调用部门",type = DroolsConstants.FieldType.BASE)
    public String opDept = "";  // 规则调用部门
    //    @FieldName(name="规则调用分公司",type = DroolsConstants.FieldType.BASE)
    public String opCoy = "";   // 规则调用分公司
    //    @FieldName(name="取的最后保单",type = DroolsConstants.FieldType.BASE)
    public String statFund = "";   // COVR.STAT_FUND 取的最后保单
    //    @FieldName(name="benBillDate",type = DroolsConstants.FieldType.BASE)
    public int benBillDate;  // COVR.BEN_BILL_DATE  取的最后保单
    //    @FieldName(name="本次加总手术比例",type = DroolsConstants.FieldType.BASE)
    public double surgPct;      // LCAB.SURGPCT 本次加总手术比例，同一次手术取的最高，计算时需要除100
    //    @FieldName(name="本次用于计算的手术加总比例",type = DroolsConstants.FieldType.BASE)
    public double tempSurgPct;  // 本次用于计算的手术加总比例
    //    @FieldName(name="本次手术次数",type = DroolsConstants.FieldType.BASE)
    public int surgTimes = 0;  // 本次手术次数
    //    @FieldName(name="本次手术信息MAP",type = DroolsConstants.FieldType.COLLECT)
    public Map surgMap = new HashMap();  // 本次手术信息MAP，由"就医序号_日期_序号"为key，保存手术比例，同一次手术保存的最高比例
    //    @FieldName(name="所有身故总计限额",type = DroolsConstants.FieldType.BASE)
    public double deadLmtAmt = 0;  // 所有身故（A类）/残疾保险金（B类）总计限额
    //    @FieldName(name="总收据申请金额",type = DroolsConstants.FieldType.BASE)
    public double receiptRequestAmt = 0;   // 总收据申请金额，针对指定类收据
    @FieldName(name = "受款人信息", listCls = "com.drools.entity.clm.Receiver", type = DroolsConstants.FieldType.COLLECT)
    public List<Receiver> receivers;     // 受款人信息，来自LCAM
    public int receiverLength;   // 受款人信息数量
    @FieldName(name = "赔案保单", listCls = "com.drools.entity.clm.Policy", type = DroolsConstants.FieldType.COLLECT)
    public List<Policy> policies;  // 赔案保单，来自LCAC
    public int policyLength;  // 保单长度
    @FieldName(name = "就医信息", listCls = "com.drools.entity.clm.Doctor", type = DroolsConstants.FieldType.COLLECT)
    public List<Doctor> doctors = new ArrayList<>();   // 就医信息
    public int doctorLength;  // 就医信息长度
    public Set noteSet = new HashSet();  // 保单照会，处理新增的照会，用于写入数据库，需要与旧照会对比是否已经存在
    public Note[] notes;  // 历史照会，来自LCAL
    public int noteLength;  // 历史照会总数
    public String dbSchema = "";  // 数据库SCHEMA名，已经带"."

    public Set computingPlanSet = new TreeSet();  // 可赔付险种列表，需要赔付的险种先保存到这里，SET自动排序后再转为数组computingPlans
    @FieldName(name = "可赔付险种数组", listCls = "com.drools.entity.clm.Plan", type = DroolsConstants.FieldType.COLLECT)
    public List<Plan> computingPlans;  // 可赔付险种数组
    public int computingPlanLength;
    public Set computingPriorBenefitSet = new TreeSet();  // 优先赔付责任列表，需要赔付的责任先保存到这里，SET自动排序后再转为数组computingBenefits
    //    @FieldName(name="优先赔付责任数组",listCls="com.drools.entity.clm.Benefit",type = DroolsConstants.FieldType.COLLECT)
//    public List<Benefit>  computingBenefits;  // 优先赔付责任数组
    public int computingBenefitLength;
    //    @FieldName(name="回写操作时间",type = DroolsConstants.FieldType.BASE)
//    public ComparePlan[]  comparingPlans;  // 与computingPlans进行分组合并判断的对象，长度与computingPlanLength相同，所以不需要
    public Map mergePlanMap = new HashMap();  // 保存合并单位数险种的的信息，根据Plan.mergeNo获得MergePlan对象
    public Set orderGroupSet = new HashSet();  // 数据库险种分类排序信息OrderGroup，表示历史信息，来自LCIE
    public Set sortedGroupSet = new HashSet(); // 理算险种分类排序信息OrderGroup，表示实时的信息，由适格的险种产生
    public String[] receiptType; // 收据排序组
    public int receiptTypeLength;
    public String receiptCurrType = "";
    @FieldName(name = "文件信息", listCls = "com.drools.entity.clm.Document", type = DroolsConstants.FieldType.COLLECT)
    public List<Document> documents;
    public int documentLength;
    public Date busiDate; // BUSIDATE用于回写操作时间
    public double receiptItemtAmt = 0;     // 社保外药品费临时金额
    public StringBuffer outLog = new StringBuffer();  // 计算过程日志信息，用于保存到固定目录

    // 扩展属性
    public String ext1;
    public String ext2;
    public String ext3;


    /**
     * 插入一个新照会
     *
     * @param pnCode  照会码
     * @param trecTyp 类型
     */
    @ActionMethodName(name = "新增一个照会", props = "照会码,类型", params = "pnCode=String,trecTyp=String")
    public void addNote(String pnCode, String trecTyp) {
        Note note = new Note();
        note.pnCode = pnCode;
        note.trecTyp = trecTyp;
        note.pnDate = this.today;
        note.chdrNum = "";
        note.remark01 = "";

        if (!this.noteSet.contains(note))
            this.noteSet.add(note);
    }

    @ActionMethodName(name = "打印日志", props = "提示内容", params = "msg=String")
    public void addLog(String msg) {
        log.debug("" + msg);
    }

//    @ActionMethodName(name = "打印日志",props = "提示内容",params ="msg=String")
//    public void addLogTxt(String log) {
//        this.outLog.append(log).append("\r\n");
//    }

    @ActionMethodName(name = "新增指定照会代码信息并增加补充保单号", props = "照会码,类型", params = "pnCode=String,trecTyp=String", specialParams = "chdrNum=$policy.getChdrNum()")
    public void addNoteWithRemarkByChdrNum(String pnCode, String trecTyp, String chdrNum) {
        this.addNoteWithRemark(pnCode, trecTyp, chdrNum);
    }

    @ActionMethodName(name = "新增指定照会代码信息并增加补充保单号和险种", props = "照会码,类型", params = "pnCode=String,trecTyp=String", specialParams = "chdrNum=$policy.getChdrNum(),crtable=$plan.getCrtable()")
    public void addNoteWithRemarkByCrtableAndChdrNum(String pnCode, String trecTyp, String chdrNum, String crtable) {
        String remark = chdrNum + ":" + crtable;
        this.addNoteWithRemark(pnCode, trecTyp, remark);
    }

    @ActionMethodName(name = "获取责任赔付公式", props = "理赔详细,保单信息,险种信息", params = "", specialParams = "claim=$claim,policy=$policy,plan=$plan")
    public static void getBenefitFormula(Claim claim, Policy policy, Plan plan) {
        log.info("获取责任赔付公式[{}][{}]", claim.getClntNumes(), plan.getCrtable());
        //设置险种生效日与保单生效日
        if (plan.crrCd != policy.occDate) {
            plan.setFormulaCrrCd(policy.hpropDte);
        } else {
            plan.setFormulaCrrCd(policy.crrcd);
        }
        log.info("修改险种生效日formulaCrrCd=" + plan.getFormulaCrrCd());
        if (!CollectionUtils.isEmpty(plan.getBenefits())) {
            for (Benefit benefit : plan.getBenefits()) {
                BenefitFormulaMapper benefitFormulaMapper = SpringContextHolder.getBean(BenefitFormulaMapper.class);
                List<BenefitFormula> benefitFormulas = benefitFormulaMapper.findBenefitFormulaByCrtableAndBenCode(plan.getCrtable(), benefit.getBenCode());
                if (!CollectionUtils.isEmpty(benefitFormulas)) {
                    for (BenefitFormula benefitFormula : benefitFormulas) {
                        log.info("循环责任公式配置，获取公式，责任公式配置：[{}]",benefitFormula);
                        Boolean clTypeFlag = false;
                        Boolean clmTypeFlag = false;
                        Boolean dateFlag = false;
                        Boolean ageFlag = false;
                        Boolean payresFlag = false;
                        Boolean illCodeFlag = false;
                        Boolean placeFlag = false;
                        Boolean surgeryFlag = false;
                        Boolean surgeryTimeFlag = false;
                        Boolean arrivalAgeFlag = false;
                        if (plan.crrCd >= Integer.parseInt(benefitFormula.getStartTime()) && plan.crrCd < Integer.parseInt(benefitFormula.getEndTime())) {

                        } else {
                            log.info("险种生效日不在在公式版本区间！");
                            continue;
                        }
                        if (
                                (benefitFormula.getClmType().equals("不做选择"))
                                        || (benefitFormula.getClmType().equals("D-身故") && claim.getClaimType().equals("D"))
                                        || (benefitFormula.getClmType().equals("C-重大疾病") && claim.getClaimType().equals("C"))
                                        || (benefitFormula.getClmType().equals("T-全残") && claim.getClaimType().equals("T"))
                                        || (benefitFormula.getClmType().equals("T-全残P-部分残疾") && (claim.getClaimType().equals("T") || claim.getClaimType().equals("P")))
                                        || (benefitFormula.getClmType().equals("D-身故T-全残P-部分残疾") && (claim.getClaimType().equals("D") || claim.getClaimType().equals("T") || claim.getClaimType().equals("P")))
                                        || (benefitFormula.getClmType().equals("C-重大疾病D-身故") && (claim.getClaimType().equals("C") || claim.getClaimType().equals("D")))
                        ) {
                            clTypeFlag = true;
                            clmTypeFlag = true;
                        }
                        if (
                                (benefitFormula.getDateType().equals("不做选择"))
                                        || (benefitFormula.getDateType().equals("意外事故日落在有效期") && StringUtil.isContain("IF、PU、WV", plan.statCode3) && claim.acdtDte >= plan.currFrom3 && claim.acdtDte < plan.currTo3)
                                        || (benefitFormula.getDateType().equals("重大疾病/残疾/身故认定日落在有效期") && StringUtil.isContain("IF、PU、WV", plan.statCode2) && claim.criticalDte >= plan.currFrom2 && claim.criticalDte < plan.currTo2)
                                        || (benefitFormula.getDateType().equals("重大疾病/残疾/身故认定日落在有效期且意外事故日非空") && claim.acdtDte >= 1 && StringUtil.isContain("IF、PU、WV", plan.statCode2) && claim.criticalDte >= plan.currFrom2 && claim.criticalDte < plan.currTo2)
                                        || (benefitFormula.getDateType().equals("重大疾病/残疾/身故认定日落在有效期且意外事故日为空") && claim.acdtDte == 0 && StringUtil.isContain("IF、PU、WV", plan.statCode2) && claim.criticalDte >= plan.currFrom2 && claim.criticalDte < plan.currTo2)
                                        || (benefitFormula.getDateType().equals("意外事故日落在有效期内，且DD不大于AD后的180天") &&
                                        StringUtil.isContain("IF、PU、WV", plan.statCode3)
                                        && claim.acdtDte >= plan.currFrom3
                                        && claim.acdtDte < plan.currTo3
                                        && claim.acdtDte > 0
                                        && TimeUtil.isDateBetween(claim.acdtDte, claim.criticalDte, 180))
                        ) {
                            dateFlag = true;
                        }
                        if (
                                (benefitFormula.getAgeType().equals("不做选择"))
                                        || (benefitFormula.getAgeType().equals("小于1周岁") && TimeUtil.calYear(claim.cltDob, plan.eventDte, claim.eventDte) >= 0 && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) >= 1)
                                        || (benefitFormula.getAgeType().equals("大于等于1周岁") && TimeUtil.calYear(claim.cltDob, plan.eventDte, claim.eventDte) >= 0 && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) >= 999)
                                        || (benefitFormula.getAgeType().equals("大于等于1周岁并小于2周岁") && TimeUtil.calYear(claim.cltDob, plan.eventDte, claim.eventDte) >= 0 && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) >= 2)
                                        || (benefitFormula.getAgeType().equals("大于等于2周岁") && TimeUtil.calYear(claim.cltDob, plan.eventDte, claim.eventDte) >= 2 && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) >= 999)
                                        || (benefitFormula.getAgeType().equals("小于18周岁") && TimeUtil.calYear(claim.cltDob, plan.eventDte, claim.eventDte) >= 0 && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) >= 18)
                                        || (benefitFormula.getAgeType().equals("大于等于18周岁") && TimeUtil.calYear(claim.cltDob, plan.eventDte, claim.eventDte) >= 18 && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) >= 999)
                                        || (benefitFormula.getAgeType().equals("小于50周岁") && TimeUtil.calYear(claim.cltDob, plan.eventDte, claim.eventDte) >= 0 && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) >= 50)
                                        || (benefitFormula.getAgeType().equals("小于60周岁") && TimeUtil.calYear(claim.cltDob, plan.eventDte, claim.eventDte) >= 0 && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) >= 60)
                                        || (benefitFormula.getAgeType().equals("大于等于60周岁") && TimeUtil.calYear(claim.cltDob, plan.eventDte, claim.eventDte) >= 60 && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) >= 999)
                                        || (benefitFormula.getAgeType().equals("小于65周岁") && TimeUtil.calYear(claim.cltDob, plan.eventDte, claim.eventDte) >= 0 && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) >= 65)
                                        || (benefitFormula.getAgeType().equals("大于等于65周岁") && TimeUtil.calYear(claim.cltDob, plan.eventDte, claim.eventDte) >= 65 && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) >= 999)
                                        || (benefitFormula.getAgeType().equals("小于等于70周岁") && TimeUtil.calYear(TimeUtil.getPlyYear(claim.cltDob, policy.occDate), claim.eventDte) < 70)
                                        || (benefitFormula.getAgeType().equals("大于70周岁") && TimeUtil.calYear(TimeUtil.getPlyYear(claim.cltDob, policy.occDate), claim.eventDte) >= 70)
                                        || (benefitFormula.getAgeType().equals("小于等于75周岁") && TimeUtil.calYear(TimeUtil.getPlyYear(claim.cltDob, policy.occDate), claim.eventDte) <= 75)
                                        || (benefitFormula.getAgeType().equals("大于75周岁") && TimeUtil.calYear(TimeUtil.getPlyYear(claim.cltDob, policy.occDate), claim.eventDte) > 75)
                                        || (benefitFormula.getAgeType().equals("小于80周岁") && TimeUtil.calYear(claim.cltDob, plan.eventDte, claim.eventDte) >= 0 && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) >= 80)
                                        || (benefitFormula.getAgeType().equals("大于等于18周岁小于70周岁") && TimeUtil.calYear(claim.cltDob, plan.eventDte, claim.eventDte) >= 18 && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) >= 70)
                                        || (benefitFormula.getAgeType().equals("小于25周岁") && TimeUtil.calYear(claim.cltDob, plan.eventDte, claim.eventDte) >= 0 && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) >= 25)
                                        || (benefitFormula.getAgeType().equals("大于等于18周岁小于75周岁") && TimeUtil.calYear(claim.cltDob, plan.eventDte, claim.eventDte) >= 18 && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) >= 75)
                                        || (benefitFormula.getAgeType().equals("大于等于2周岁小于18周岁") && TimeUtil.calYear(claim.cltDob, plan.eventDte, claim.eventDte) >= 2 && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) >= 18)
                        ) {
                            ageFlag = true;
                        }
                        if (
                                (benefitFormula.getArrivalAgeType().equals("不做选择"))
                                        || (benefitFormula.getArrivalAgeType().equals("18-40周岁") && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) >= 18 && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) <= 40)
                                        || (benefitFormula.getArrivalAgeType().equals("41-60周岁") && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) >= 41 && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) <= 60)
                                        || (benefitFormula.getArrivalAgeType().equals("61-80周岁") && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) >= 61 && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) <= 80)
                                        || (benefitFormula.getArrivalAgeType().equals("大于等于61周岁") && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) >= 61 && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) <= 999)
                                        || (benefitFormula.getArrivalAgeType().equals("小于等于40周岁") && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) >= 0 && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) <= 40)
                                        || (benefitFormula.getArrivalAgeType().equals("小于等于17周岁") && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) >= 0 && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) <= 17)
                                        || (benefitFormula.getArrivalAgeType().equals("18-36周岁") && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) >= 18 && TimeUtil.calArrivalAge(policy.anbAtCcd, policy.crrcd, claim.eventDte) <= 36)
                        ) {
                            arrivalAgeFlag = true;
                        }

                        if (
                                (benefitFormula.getPaieSaveType().equals("不做选择"))
                                        || (benefitFormula.getPaieSaveType().equals("已领生存金期数等于0") && policy.payres == 0)
                                        || (benefitFormula.getPaieSaveType().equals("已领生存金期数大于0并且小于10") && policy.payres >= 1 && policy.payres < 10)
                                        || (benefitFormula.getPaieSaveType().equals("已领生存金期数大于等于10") && policy.payres >= 10 && policy.payres < 999)
                        ) {
                            payresFlag = true;
                        }
                        if (
                                (benefitFormula.getIllType().equals("不做选择"))
                                        || (benefitFormula.getIllType().equals("伤病代码等于") && StringUtil.isContain(ClmUtil.getAllIllcodes(claim.getDoctors()), benefitFormula.getIllCode()))
                                        || (benefitFormula.getIllType().equals("伤病代码类似于") && StringUtil.isCheckIllCodeContainsLike(ClmUtil.getAllIllcodes(claim.getDoctors()), benefitFormula.getIllCode()))
                        ) {
                            illCodeFlag = true;
                        }
                        if (CollectionUtils.isEmpty(benefit.getDeformityGroup())) {
                            for (DfmtGroup dfmtGroup : benefit.deformityGroup) {
                                if ((benefitFormula.getIllType().equals("新伤残代码等于") && StringUtil.isContain(dfmtGroup.formulaIllcodes, benefitFormula.getIllCode()))
                                        || (benefitFormula.getIllType().equals("新伤残代码类似于") && StringUtil.isCheckIllCodeContainsLike(dfmtGroup.formulaIllcodes, benefitFormula.getIllCode()))
                                        || (benefitFormula.getIllType().equals("新伤残代码等级等于") && StringUtil.isContain3(dfmtGroup.formulaIllcodes, benefitFormula.getIllCode()))
                                ) {
                                    illCodeFlag = true;
                                }
                            }
                        }
                        if ((benefitFormula.getPlaceType().equals("不做选择"))
                                || (benefitFormula.getPlaceType().equals("事故地点类似于") && StringUtil.isContainsEqualOrLike(benefitFormula.getPlaceCode(), claim.eventPlace))
                                || (benefitFormula.getPlaceType().equals("事故地点不等于") && StringUtil.isNotContain(benefitFormula.getPlaceCode(), claim.eventPlace))
                        ) {
                            placeFlag = true;
                        }
                        if (benefitFormula.getSurgeryType().equals("不做选择")) {
                            surgeryFlag = true;
                        }
                        if (!CollectionUtils.isEmpty(claim.doctors)) {
                            for (Doctor doctor : claim.doctors) {
                                if (!CollectionUtils.isEmpty(doctor.getSurgeries())) {
                                    for (Surgery surgery : doctor.surgeries) {
                                        if (benefitFormula.getSurgeryType().equals("手术代码类似于") && StringUtil.isContainsEqualOrLike(benefitFormula.getSurgeryCode(), surgery.surgCode)
                                        ) {
                                            surgeryFlag = true;
                                        }
                                    }
                                }
                            }
                        }
                        if (benefitFormula.getSurgeryTimeType().equals("不做选择")) {
                            surgeryTimeFlag = true;
                        }
                        if (!CollectionUtils.isEmpty(claim.doctors)) {
                            for (Doctor doctor : claim.doctors) {
                                if (!CollectionUtils.isEmpty(doctor.getSurgeries())) {
                                    for (Surgery surgery : doctor.surgeries) {
                                        if (benefitFormula.getSurgeryTimeType().equals("未超认定日后365天") && TimeUtil.isDateBetween(claim.eventDte, surgery.surgDte, 365)
                                        ) {
                                            surgeryTimeFlag = true;
                                        }
                                    }
                                }
                            }
                        }
                        //设置公式
                        if (clTypeFlag && dateFlag && ageFlag && payresFlag && illCodeFlag && placeFlag && surgeryFlag && surgeryTimeFlag && arrivalAgeFlag){
                            benefit.setExamStatus("EL");
                            benefit.setFormulaStr(benefitFormula.getFormula());
                            benefit.setCalDesc(benefitFormula.getRemark());
                            benefit.setIllPct(Double.parseDouble(benefitFormula.getIllPct()));
                            benefit.setAgePct(Double.parseDouble(benefitFormula.getAgePct()));
                            log.info("循环责任公式配置，审核状态EL,最终公式配置：[{}]",benefitFormula);
                            break;
                        }
                        if (clmTypeFlag){
                            log.info("循环责任公式配置，审核状态DC");
                            benefit.setExamStatus("DC");
                        }else {
                            log.info("循环责任公式配置，审核状态EL");
                            benefit.setExamStatus("NA");
                        }

                    }
                }

            }
        }

    }

    @ActionMethodName(name = "获取事故认定日，保单年度", props = "理赔详细,保单信息,险种信息", params = "", specialParams = "claim=$claim,policy=$policy,plan=$plan")
    public static void getEventDate(Claim claim, Policy policy, Plan plan) {
        log.info("获取事故认定日，保单年度[{}][{}][{}]", claim.getClntNumes(), policy.getChdrNum(), plan.getCrtable());
        EventDateMapper bean = SpringContextHolder.getBean(EventDateMapper.class);
        List<EventDate> eventDates = bean.findByCrtable(plan.getCrtable());
        plan.setEventDte(0);
        if (!CollectionUtils.isEmpty(eventDates)) {
            for (EventDate eventDate : eventDates) {
                if (    //版本日期比较
                        (StringUtil.strIsNull(eventDate.getStartTime()) || plan.getVerFromDate() == Integer.parseInt(eventDate.getStartTime()))
                                //出险年龄大于等于65 TimeUtil.calYear($c.cltDob,$c.acdtDte)$param65
                                && (StringUtil.strIsNull(eventDate.getCltdobCon()) || (eventDate.getCltdobCon().equals("1") && TimeUtil.calYear(claim.cltDob, claim.acdtDte) >= 65))
                                //理赔形态
                                && (StringUtil.strIsNull(eventDate.getClmType()) || StringUtil.isContain(eventDate.getClmType(), claim.claimType))
                                //意外事故日判断
                                && (StringUtil.strIsNull(eventDate.getAcdtDteCon()) || (eventDate.getAcdtDteCon().equals("1") && claim.getAcdtDte() == 0) || (eventDate.getCltdobCon().equals("2") && claim.getAcdtDte() != 0))
                                //入院日期判断
                                && (StringUtil.strIsNull(eventDate.getEarlyHsDte()) || (eventDate.getEarlyHsDte().equals("1") && ClmUtil.getEarlyHsDte(claim.getDoctors()) == 0) || (eventDate.getEarlyHsDte().equals("2") && ClmUtil.getEarlyHsDte(claim.getDoctors()) != 0))
                ) {
                    log.info("获取事故认定日，匹配配置[{}]", eventDate);
                    if (eventDate.getEventDate().equals("1")) {
                        //身故/残疾/重大疾病认定日
                        plan.setEventDte(claim.getCriticalDte());
                    } else if (eventDate.getEventDate().equals("2")) {
                        //意外事故日
                        plan.setEventDte(claim.getAcdtDte());
                    } else if (eventDate.getEventDate().equals("3")) {
                        //最早的入院日期
                        plan.setEventDte((int) ClmUtil.getEarlyHsDte(claim.getDoctors()));
                    } else if (eventDate.getEventDate().equals("4")) {
                        //最早的手术日期
                        plan.setEventDte(ClmUtil.getEarlySurgDte(claim.getDoctors()));
                    } else if (eventDate.getEventDate().equals("5")) {
                        //最早的就医信息
                        plan.setEventDte(ClmUtil.getEarlyConsultDte(claim.getDoctors()));
                    }
                }
            }
        }
        //设置保单年度
        if (plan.getEventDte() == 0) {
            plan.setEventDte(claim.getEventDte());
        }
        plan.assumeBgnDate = TimeUtil.getPolicyBgnDay(policy.currFrom, plan.eventDte);
        plan.assumeEndDate = TimeUtil.getPolicyEndDay(policy.currFrom, policy.currTo, plan.eventDte);
    }

    /**
     * 新增指定照会代码信息并增加补充
     *
     * @param pnCode
     * @param trecTyp
     * @param remark
     */
    public void addNoteWithRemark(String pnCode, String trecTyp, String remark) {
        Note note = new Note();
        note.pnCode = pnCode;
        note.trecTyp = trecTyp;
        note.pnDate = this.today;
        note.chdrNum = "";
        note.remark01 = remark;
        if (this.noteSet.contains(note)) {
            Iterator it = noteSet.iterator();
            while (it.hasNext()) {
                Note n2 = (Note) it.next();
                if (note.equals(n2)) {
                    if (n2.getRemark01().length() <= 0) {
                        n2.setRemark01(remark);
                    } else {
                        int mao = remark.indexOf(':');
                        if (mao < 0) {
                            if (n2.getRemark01().indexOf(remark) < 0)
                                n2.setRemark01(n2.getRemark01() + "," + remark);
                        } else {
                            // 分解保单号
                            String[] array = remark.split(":");
                            if (n2.getRemark01().indexOf(array[0]) < 0) {
                                n2.setRemark01(n2.getRemark01() + "," + remark);
                            } else {
                                // 已经有相同保单存在 判断保单下面是否有相同的险种
                                StringBuffer buf = new StringBuffer(n2.getRemark01());
                                int idx2 = n2.getRemark01().indexOf(",", n2.getRemark01().indexOf(array[0]));
                                int ai = n2.getRemark01().indexOf(array[0]);
                                int ab = n2.getRemark01().indexOf(",", ai + 9);
                                String planS = "";
                                if (ai >= 0) {
                                    planS = n2.getRemark01().substring(ai + 9, ab < 0 ? n2.getRemark01().length() : ab);
                                }
                                String[] arrayPlan = planS.split("/");
                                boolean bool = true;
                                for (int i = 0; i < arrayPlan.length; i++) {
                                    if (arrayPlan[i].equals(array[1])) {
                                        bool = false;
                                    }
                                }
                                if (idx2 < 0) {
                                    if (bool) {
                                        n2.setRemark01(n2.getRemark01() + "/" + array[1]);
                                    }
                                } else {
                                    if (bool) {
                                        buf.insert(idx2 - 1, "/" + array[1]);
                                    }
                                    n2.setRemark01(buf.toString());
                                }
                            }
                        }
                    }

                    return;
                }
            }
        } else {
            this.noteSet.add(note);
        }
    }


    @ActionMethodName(name = "文档审核", props = "理赔信息", params = "", specialParams = "claim=$claim")
    public static void documentAudit( Claim claim) {
        InvokeAuditServiceImpl invokeAuditService =  SpringContextHolder.getBean("invokeAuditServiceImpl");
        invokeAuditService.documentAudit(claim);
    }
    @ActionMethodName(name = "初始化保单状态", props = "保单信息", params = "", specialParams = "policy=$policy")
    public static void initPolicyStatus( Policy policy) {
        //初始化保单状态
        policy.setExamStatus("");
        policy.setReimbursed(0);
    }

    @ActionMethodName(name = "初始化险种状态", props = "险种信息", params = "", specialParams = "claim=$claim,policy=$policy,plan=$plan")
    public static void initPlanStatus(Claim claim, Policy policy, Plan plan) {
        log.info("初始化险种审核状态EL与赔付金额0");
        plan.setExamStatus(DroolsConstants.PlanExamStatus.EL);
        plan.setClmAmt(0);

        log.info("初始化险种事故认定日为0");
        plan.setEventDte(0);

//        Claim.getEventDate(claim, policy, plan);
    }
    @ActionMethodName(name = "前置审核", props = "理赔信息,保单信息,险种信息", params = "", specialParams = "claim=$claim,policy=$policy,plan=$plan")
    public static void preAudit(Claim claim,Policy policy, Plan plan) {
        InvokeAuditServiceImpl invokeAuditService =  SpringContextHolder.getBean("invokeAuditServiceImpl");
        invokeAuditService.preAudit(claim,policy,plan);
    }
    @ActionMethodName(name = "后置审核", props = "理赔信息,保单信息,险种信息", params = "", specialParams = "claim=$claim,policy=$policy,plan=$plan")
    public static void afterAudit(Claim claim,Policy policy, Plan plan) {
        InvokeAuditServiceImpl invokeAuditService =  SpringContextHolder.getBean("invokeAuditServiceImpl");
        invokeAuditService.afterAudit(claim,policy,plan);
    }
    @ActionMethodName(name = "根据险种状态修改保单状态", props = "保单信息,险种信息", params = "", specialParams = "policy=$policy,plan=$plan")
    public static void setExamStatusByPlan(Policy policy, Plan plan) {
        InvokeAuditServiceImpl invokeAuditService =  SpringContextHolder.getBean("invokeAuditServiceImpl");
        invokeAuditService.setExamStatusByPlan(policy,plan);
    }
    @ActionMethodName(name = "根据保单状态修改赔案审核状态", props = "理赔信息,保单信息", params = "", specialParams = "claim=$claim,policy=$policy")
    public static void setExamStatusByPolicy(Claim claim,Policy policy) {
        InvokeAuditServiceImpl invokeAuditService =  SpringContextHolder.getBean("invokeAuditServiceImpl");
        invokeAuditService.setExamStatusByPolicy(claim,policy);
    }
    @ActionMethodName(name = "续赔件规则", props = "理赔信息", params = "", specialParams = "claim=$claim")
    public static void auditHistory(Claim claim) {
        InvokeAuditServiceImpl invokeAuditService =  SpringContextHolder.getBean("invokeAuditServiceImpl");
        invokeAuditService.auditHistory(claim);
    }
}
