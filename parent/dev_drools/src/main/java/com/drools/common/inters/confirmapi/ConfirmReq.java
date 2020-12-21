package com.drools.common.inters.confirmapi;

import com.drools.common.DroolsConstants;
import com.drools.common.annotation.FieldName;
import com.drools.common.inters.BaseReq;
import com.drools.common.validator.IsCode;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/* *
 * 资料确认请求对象 
 * @author ly
 * @modifyTime 2020/11/20 14:25:00
 */
@Data
public class ConfirmReq extends BaseReq {
    @Valid
    @FieldName(name = "头部信息", type = DroolsConstants.FieldType.OBJECT)
    private ConfirmReq.ConfirmReqHead head;
    @Valid
    @FieldName(name = "报文主体信息", type = DroolsConstants.FieldType.OBJECT)
    private ConfirmReq.ConfirmReqBody body;

    @Data
    public static class ConfirmReqHead {
        @NotBlank(message = "流水号不能为空")
        @FieldName(name = "流水号", type = DroolsConstants.FieldType.BASE, remark = "交易流水号，用于唯一标识")
        private String transactionNo;//流水号  交易流水号，用于唯一标识
        @NotBlank(message = "请求时间不能为空")
        @FieldName(name = "流水号", type = DroolsConstants.FieldType.BASE, remark = "YYYY-MM-DD HH:MM:SS")
        private String timeStamp;//请求时间   YYYY-MM-DD HH:MM:SS
        @NotBlank(message = "接口选择不能为空")
        @FieldName(name = "流水号", type = DroolsConstants.FieldType.BASE, remark = "invokeConfirm")
        @IsCode(code = "invokeConfirm", message = "接口选择不正确")
        private String visitCode;//接口选择  "invokeCreate"
        @FieldName(name = "数据来源", type = DroolsConstants.FieldType.BASE)
        @NotBlank(message = "数据来源不能为空")
        private String requestFrom;//数据来源
    }

    @Data
    public static class ConfirmReqBody {
        @Valid
        @FieldName(name = "基本信息", type = DroolsConstants.FieldType.OBJECT)
        private ConfirmBaseInfo baseInfo;
        @Valid
        @FieldName(name = "营销员信息", type = DroolsConstants.FieldType.OBJECT)
        private ConfirmReq.AgentInfo agentInfo;
        @Valid
        @FieldName(name = "保单信息", type = DroolsConstants.FieldType.COLLECT)
        private List<ConfirmReq.PolicyInfo> policyInfoList;
        @Valid
        @FieldName(name = "受款人信息", type = DroolsConstants.FieldType.COLLECT)
        private List<ConfirmReq.Receiver> receiverList;
        @Valid
        @FieldName(name = "就医信息", type = DroolsConstants.FieldType.COLLECT)
        private List<ConfirmReq.Doctor> doctorList;

        @Valid
        @FieldName(name = "文件信息", type = DroolsConstants.FieldType.COLLECT)
        private List<ConfirmReq.Document> documentList;

    }

    @Data
    public static class Plan {
        @NotBlank(message = "险种代码不能为空")
        @FieldName(name = "险种代码", type = DroolsConstants.FieldType.BASE)
        private String crtable;//险种代码
        @NotBlank(message = "life不能为空")
        @FieldName(name = "life", type = DroolsConstants.FieldType.BASE)
        private String life;//
        @NotBlank(message = "jlife不能为空")
        @FieldName(name = "jlife", type = DroolsConstants.FieldType.BASE)
        private String jlife;//
        @NotBlank(message = "coverage不能为空")
        @FieldName(name = "coverage", type = DroolsConstants.FieldType.BASE)
        private String coverage;//
        @NotBlank(message = "rider不能为空")
        @FieldName(name = "rider", type = DroolsConstants.FieldType.BASE)
        private String rider;//
        @NotNull(message = "承保起期不能为空")
        @FieldName(name = "承保起期", type = DroolsConstants.FieldType.BASE)
        private long currFrom;//承保起期
        @NotNull(message = "承保止期不能为空")
        @FieldName(name = "承保止期", type = DroolsConstants.FieldType.BASE)
        private long currTo;//承保止期
        @NotBlank(message = "险种生效日期不能为空")
        @FieldName(name = "险种生效日期", type = DroolsConstants.FieldType.BASE)
        private String crrCd;//险种生效日期
        @NotBlank(message = "险种状态不能为空")
        @FieldName(name = "险种状态", type = DroolsConstants.FieldType.BASE)
        private String statCode;//险种状态
        @NotBlank(message = "保费状态不能为空")
        @FieldName(name = "保费状态", type = DroolsConstants.FieldType.BASE)
        private String pstatCode;//保费状态
        @FieldName(name = "单位数/险种保额", type = DroolsConstants.FieldType.BASE)
        private Double sumins;//单位数/险种保额
        @FieldName(name = "险种产品", type = DroolsConstants.FieldType.BASE)
        private String prdTyp;//险种产品
        @FieldName(name = "险种分组", type = DroolsConstants.FieldType.BASE)
        private String compGrp;//险种分组
        @FieldName(name = "险种给付类型", type = DroolsConstants.FieldType.BASE)
        private String trecTyp;//险种给付类型
        @FieldName(name = "险种类型", type = DroolsConstants.FieldType.BASE)
        private String statFund;//险种类型
        @FieldName(name = "险种大类", type = DroolsConstants.FieldType.BASE)
        private String statSect;//险种大类
        @FieldName(name = "家庭型保单记录", type = DroolsConstants.FieldType.BASE)
        private String famType;//家庭型保单记录
        @FieldName(name = "是否新规定", type = DroolsConstants.FieldType.BASE)
        private String flag01;//是否新规定
        @FieldName(name = "豁免险种标记", type = DroolsConstants.FieldType.BASE)
        private String prop01;//豁免险种标记
        @FieldName(name = "等待期间", type = DroolsConstants.FieldType.BASE)
        private String waitProd;//等待期间
        @FieldName(name = "每计划值", type = DroolsConstants.FieldType.BASE)
        private String waitReins;//每计划值
        @FieldName(name = "保障类型", type = DroolsConstants.FieldType.BASE)
        private String compType;//保障类型
        @FieldName(name = "事故认定日", type = DroolsConstants.FieldType.BASE)
        private long eventDte;//事故认定日
        @FieldName(name = "本次理赔保单年度起期", type = DroolsConstants.FieldType.BASE)
        private long assumeBgnDate;//本次理赔保单年度起期
        @FieldName(name = "本次理赔保单年度止期", type = DroolsConstants.FieldType.BASE)
        private long assumeEndDate;//本次理赔保单年度止期
        @FieldName(name = "协议比例", type = DroolsConstants.FieldType.BASE)
        private Double protocolRate;//协议比例
        @FieldName(name = "险种排序权值", type = DroolsConstants.FieldType.BASE)
        private Double tranNo;//险种排序权值
        @FieldName(name = "每计划值", type = DroolsConstants.FieldType.BASE)
        private Double valuePer;//每计划值
        @FieldName(name = "levelTyp", type = DroolsConstants.FieldType.BASE)
        private String levelTyp;//
        @FieldName(name = "险种给付金额", type = DroolsConstants.FieldType.BASE)
        private Double clmAmt;//险种给付金额
        @FieldName(name = "融通比例", type = DroolsConstants.FieldType.BASE)
        private Double exgraPct;//融通比例
        @FieldName(name = "险种排序分组", type = DroolsConstants.FieldType.BASE)
        private String orderCompGrp;//险种排序分组
        @FieldName(name = "covrStatFund", type = DroolsConstants.FieldType.BASE)
        private String covrStatFund;//
        @FieldName(name = "covrStatSect", type = DroolsConstants.FieldType.BASE)
        private String covrStatSect;//
        @NotBlank(message = "清算标记不能为空")
        @FieldName(name = "清算标记", type = DroolsConstants.FieldType.BASE)
        private String cleanFlag;//清算标记
    }

    @Data
    public static class Life {
        @NotBlank(message = "被保人客户号不能为空")
        @FieldName(name = "被保人客户号", type = DroolsConstants.FieldType.BASE)
        private String lifcnum;//被保人客户号
    }

    @Data
    public static class PolicyInfo {
        @NotBlank(message = "保单号码不能为空")
        @FieldName(name = "保单号码", type = DroolsConstants.FieldType.BASE)
        private String chdrNum;//保单号码
        @NotBlank(message = "事故人与主被保人关系不能为空")
        @FieldName(name = "事故人与主被保人关系", type = DroolsConstants.FieldType.BASE)
        private String relation;//事故人与主被保人关系
        @NotNull(message = "保单生效日期不能为空")
        @FieldName(name = "保单生效日期", type = DroolsConstants.FieldType.BASE)
        private long currFrom;//保单生效日期
        @NotNull(message = "保单终止日期不能为空")
        @FieldName(name = "保单终止日期", type = DroolsConstants.FieldType.BASE)
        private long currTo;//保单终止日期
        @NotNull(message = "保单生效日不能为空")
        @FieldName(name = "保单生效日", type = DroolsConstants.FieldType.BASE)
        private long occDate;//保单生效日
        @NotBlank(message = "保单状态不能为空")
        @FieldName(name = "保单状态", type = DroolsConstants.FieldType.BASE)
        private String statCode;//保单状态
        @NotBlank(message = "保费状态不能为空")
        @FieldName(name = "保费状态", type = DroolsConstants.FieldType.BASE)
        private String pstatCode;//保费状态
        @NotNull(message = "复效日期不能为空")
        @FieldName(name = "复效日期", type = DroolsConstants.FieldType.BASE)
        private long reinsDate;//复效日期
        @NotBlank(message = "理赔审核状态不能为空")
        @FieldName(name = "理赔审核状态", type = DroolsConstants.FieldType.BASE)
        private String examStatus;//理赔审核状态
        @NotBlank(message = "记录类型不能为空")
        @FieldName(name = "记录类型", type = DroolsConstants.FieldType.BASE)
        private String trecTyp;//记录类型
        @NotNull(message = "给付金额不能为空")
        @FieldName(name = "给付金额", type = DroolsConstants.FieldType.BASE)
        private Double reimbursed;//给付金额
        @NotBlank(message = "人工新增保单号码不能为空")
        @FieldName(name = "人工新增保单号码", type = DroolsConstants.FieldType.BASE)
        private String manualAdd;//人工新增保单号码
        @NotNull(message = "ptDate不能为空")
        @FieldName(name = "ptDate", type = DroolsConstants.FieldType.BASE)
        private long ptDate;//
        @NotBlank(message = "保单所属分公司不能为空")
        @FieldName(name = "保单所属分公司", type = DroolsConstants.FieldType.BASE)
        private String chdrCoy;//保单所属分公司
        @NotBlank(message = "投保人不能为空")
        @FieldName(name = "投保人", type = DroolsConstants.FieldType.BASE)
        private String cownNum;//投保人
        @NotBlank(message = "付款人不能为空")
        @FieldName(name = "付款人", type = DroolsConstants.FieldType.BASE)
        private String payrNum;//付款人
        @NotNull(message = "受理日不能为空")
        @FieldName(name = "受理日", type = DroolsConstants.FieldType.BASE)
        private long hpropDte;//受理日
        @NotNull(message = "核保通过日不能为空")
        @FieldName(name = "核保通过日", type = DroolsConstants.FieldType.BASE)
        private long huwdcDte;//核保通过日
        @NotNull(message = "本次理赔保单年度起期不能为空")
        @FieldName(name = "本次理赔保单年度起期", type = DroolsConstants.FieldType.BASE)
        private long assumeBgnDate;//本次理赔保单年度起期
        @NotNull(message = "本次理赔保单年度止期不能为空")
        @FieldName(name = "本次理赔保单年度止期", type = DroolsConstants.FieldType.BASE)
        private long assumeEndDate;//本次理赔保单年度止期

        @Valid
        @FieldName(name = "险种信息信息", type = DroolsConstants.FieldType.COLLECT)
        private List<ConfirmReq.Plan> planList;

        @Valid
        @FieldName(name = "保单被保人信息", type = DroolsConstants.FieldType.COLLECT)
        private List<ConfirmReq.Life> lifeList;
    }

    @Data
    public static class Document {
        @NotBlank(message = "文件代码不能为空")
        @FieldName(name = "文件代码", type = DroolsConstants.FieldType.BASE)
        private String docCode;//文件代码
        @NotBlank(message = "文件份数不能为空")
        @FieldName(name = "文件份数", type = DroolsConstants.FieldType.BASE)
        private String docCount;//文件份数
        @NotBlank(message = "文件状态不能为空")
        @FieldName(name = "文件状态", type = DroolsConstants.FieldType.BASE)
        private String docStat;//文件状态
    }


    @Data
    public static class ConfirmBaseInfo {
        @FieldName(name = "理赔业务号", type = DroolsConstants.FieldType.BASE)
        @NotBlank(message = "理赔业务号不能为空")
        private String clmnum;//理赔业务号
        @FieldName(name = "分公司", type = DroolsConstants.FieldType.BASE)
        @NotBlank(message = "分公司不能为空")
        private String clmcoy;//分公司
        @FieldName(name = "分支机构", type = DroolsConstants.FieldType.BASE)
        @NotBlank(message = "分支机构不能为空")
        private String branch;//分支机构
        @FieldName(name = "事故人ID", type = DroolsConstants.FieldType.BASE)
        @NotBlank(message = "事故人ID不能为空")
        private String secuityNo;//事故人ID
        @FieldName(name = "事故人客户号", type = DroolsConstants.FieldType.BASE)
        @NotBlank(message = "事故人客户号不能为空")
        private String clntNum;//事故人客户号
        @FieldName(name = "事故人性别", type = DroolsConstants.FieldType.BASE)
        @NotBlank(message = "事故人性别不能为空")
        private String cltSex;//事故人性别
        @FieldName(name = "事故人出生日期", type = DroolsConstants.FieldType.BASE)
        @NotNull(message = "事故人出生日期不能为空")
        private long cltDob;//事故人出生日期
        @FieldName(name = "受理日期", type = DroolsConstants.FieldType.BASE)
        @NotNull(message = "受理日期不能为空")
        private long submitDte;//受理日期
        @FieldName(name = "报备日期", type = DroolsConstants.FieldType.BASE)
        @NotNull(message = "报备日期不能为空")
        private long reportDte;//报备日期
        @FieldName(name = "理赔处理日期", type = DroolsConstants.FieldType.BASE)
        @NotNull(message = "理赔处理日期不能为空")
        private long proceDate;//理赔处理日期
        @FieldName(name = "事故认定日", type = DroolsConstants.FieldType.BASE)
        @NotNull(message = "事故认定日不能为空")
        private long eventDte;//事故认定日
        @FieldName(name = "意外事故日", type = DroolsConstants.FieldType.BASE)
        private long acdtDte;//意外事故日
        @FieldName(name = "身故/残疾/重大疾病认定日", type = DroolsConstants.FieldType.BASE)
        private long criticalDte;//身故/残疾/重大疾病认定日
        @FieldName(name = "意外事故代码", type = DroolsConstants.FieldType.BASE)
        private String acdtCode;//意外事故代码
        @FieldName(name = "意外发生地", type = DroolsConstants.FieldType.BASE)
        private String eventPlace;//意外发生地
        @FieldName(name = "除外责任", type = DroolsConstants.FieldType.BASE)
        private String excludeBenefit;//除外责任
        @FieldName(name = "人工修改手术比例标志", type = DroolsConstants.FieldType.BASE)
        @NotBlank(message = "人工修改手术比例标志不能为空")
        private String manualSurgeryFlag;//人工修改手术比例标志
        @NotBlank(message = "理赔型态不能为空")
        @FieldName(name = "理赔型态", type = DroolsConstants.FieldType.BASE)
        private String claimType;//理赔型态
        @FieldName(name = "案件型态", type = DroolsConstants.FieldType.BASE)
        @NotBlank(message = "案件型态不能为空")
        private String caseType;//案件型态
    }

    @Data
    public static class AgentInfo {
        @FieldName(name = "营销员ID", type = DroolsConstants.FieldType.BASE)
        @NotBlank(message = "营销员ID不能为空")
        private String agtId;//营销员ID
        @FieldName(name = "营销员入职日期", type = DroolsConstants.FieldType.BASE)
        private long agtFromDate;//营销员入职日期
        @FieldName(name = "营销员离职日期", type = DroolsConstants.FieldType.BASE)
        private long agtToDate;//营销员离职日期
    }

    @Data
    public static class IllCode {
        @FieldName(name = "伤病代码id", type = DroolsConstants.FieldType.BASE)
        private String illCode;//伤病代码id
    }

    @Data
    public static class Doctor {
        @FieldName(name = "就医序号", type = DroolsConstants.FieldType.BASE)
        @NotNull(message = "就医序号不能为空")
        private int recId;//就医序号
        @FieldName(name = "医院代码", type = DroolsConstants.FieldType.BASE)
        @NotBlank(message = "医院代码不能为空")
        private String hsCode;//医院代码
        @FieldName(name = "医院类型", type = DroolsConstants.FieldType.BASE)
        private String trecTyp;//医院类型
        @FieldName(name = "医院有效标记", type = DroolsConstants.FieldType.BASE)
        private String validFlag;//医院有效标记
        @FieldName(name = "医院等级", type = DroolsConstants.FieldType.BASE)
        private String hclass;//医院等级
        @FieldName(name = "赔付范围", type = DroolsConstants.FieldType.BASE)
        private String sumind;//赔付范围
        @FieldName(name = "医院品质", type = DroolsConstants.FieldType.BASE)
        private String hqind;//医院品质
        @FieldName(name = "就医日期", type = DroolsConstants.FieldType.BASE)
        @NotNull(message = "就医日期不能为空")
        private int consultDte;//就医日期
        @FieldName(name = "一般病房住院日期", type = DroolsConstants.FieldType.BASE)
        @NotNull(message = "一般病房住院日期不能为空")
        private int hsDte;//一般病房住院日期
        @FieldName(name = "一般病房出院日期", type = DroolsConstants.FieldType.BASE)
        @NotNull(message = "一般病房出院日期不能为空")
        private int leaveDte;//一般病房出院日期
        @FieldName(name = "一般病房住院天数", type = DroolsConstants.FieldType.BASE)
        @NotNull(message = "一般病房住院天数不能为空")
        private int hsDays;//一般病房住院天数
        @FieldName(name = "加护病房住院日期", type = DroolsConstants.FieldType.BASE)
        @NotNull(message = "加护病房住院日期不能为空")
        private int icuHsDte;//加护病房住院日期
        @FieldName(name = "加护病房出院日期", type = DroolsConstants.FieldType.BASE)
        @NotNull(message = "加护病房出院日期不能为空")
        private int icuLeaveDte;//加护病房出院日期
        @FieldName(name = "加护病房住院天数", type = DroolsConstants.FieldType.BASE)
        @NotNull(message = "加护病房住院天数不能为空")
        private int icuDays;//加护病房住院天数
        @Valid
        @FieldName(name = "手术信息", type = DroolsConstants.FieldType.COLLECT)
        private List<ConfirmReq.Surgery> surgeryList;

        @Valid
        @FieldName(name = "伤病代码信息", type = DroolsConstants.FieldType.COLLECT)
        private List<ConfirmReq.IllCode> illcodeList;
    }

    @Data
    public static class Surgery {
        @FieldName(name = "手术代码", type = DroolsConstants.FieldType.BASE)
        private String surgCode;//手术代码
        @FieldName(name = "手术日期", type = DroolsConstants.FieldType.BASE)
        private int surgDte;//手术日期
        @FieldName(name = "同一次手术序号", type = DroolsConstants.FieldType.BASE)
        private String surgSeq;//同一次手术序号
        @FieldName(name = "手术比例", type = DroolsConstants.FieldType.BASE)
        private String surgPct;//手术比例
    }

    @Data
    public static class Receiver {
        @FieldName(name = "受款人ID", type = DroolsConstants.FieldType.BASE)
        public String clntNum = "";          // LCAM.CLNTNUM 受款人ID
        @FieldName(name = "账户类别", type = DroolsConstants.FieldType.BASE, remark = "1-续期账户/2-其他账户")
        public String countType = "";        // LCAM.FLAG01 账户类别 1-续期账户/2-其他账户
        @FieldName(name = "给付方式", type = DroolsConstants.FieldType.BASE, remark = "5-自动转账，0-现金，2-人工支票，6-电汇")
        public String reqnType = "";         // LCAM.REQNTYPE 给付方式 5-自动转账，0-现金，2-人工支票，6-电汇
        @FieldName(name = "账户复核状态", type = DroolsConstants.FieldType.BASE)
        public String procInd = "";
    }

}
