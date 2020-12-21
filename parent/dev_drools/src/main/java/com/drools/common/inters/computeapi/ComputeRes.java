package com.drools.common.inters.computeapi;

import com.drools.common.DroolsConstants;
import com.drools.common.annotation.FieldName;
import com.drools.util.DateUtil;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ComputeRes {
    @FieldName(name = "头部信息", type = DroolsConstants.FieldType.OBJECT)
    public ComputeResHead head;
    @FieldName(name = "主体信息", type = DroolsConstants.FieldType.OBJECT)
    public ComputeResBody body;


    @Data
    public static class ComputeResHead {
        @FieldName(name = "接口响应代码", type = DroolsConstants.FieldType.BASE)
        private String code;//接口响应代码 错误代码；0成功，其它失败
        @FieldName(name = "接口返回信息", type = DroolsConstants.FieldType.BASE)
        private String message;//接口返回信息 错误信息
        @FieldName(name = "流水号", type = DroolsConstants.FieldType.BASE)
        private String transactionNo;//流水号
        @FieldName(name = "响应时间", type = DroolsConstants.FieldType.BASE)
        private String timeStamp;//响应时间 格式：yyyy-MM-dd HH:mm:ss

        public ComputeResHead() {
        }

        public ComputeResHead(String code, String message, String transactionNo) {
            this.code = code;
            this.message = message;
            this.transactionNo = transactionNo;
            this.timeStamp = DateUtil.get4yMdHms(new Date());
        }
    }

    @Data
    public static class ComputeResBody {
        @FieldName(name = "理赔业务号", type = DroolsConstants.FieldType.BASE)
        private String clmnum;//理赔业务号
        @FieldName(name = "理赔审核状态", type = DroolsConstants.FieldType.BASE)
        private String examStatus;//理赔审核状态
        @FieldName(name = "理赔给付金额", type = DroolsConstants.FieldType.BASE)
        private double clamAmt;//理赔给付金额
        @FieldName(name = "taxAmt", type = DroolsConstants.FieldType.BASE)
        private double taxAmt;//
        @FieldName(name = "延滞利息", type = DroolsConstants.FieldType.BASE)
        private double definter;//延滞利息
        @FieldName(name = "延滞利息之代扣税额", type = DroolsConstants.FieldType.BASE)
        private double xAmt;//延滞利息之代扣税额
        @FieldName(name = "代扣项目", type = DroolsConstants.FieldType.BASE)
        private double debitAmout;//代扣项目

        @FieldName(name = "保单信息", type = DroolsConstants.FieldType.COLLECT)
        private List<PolicyInfo> policyInfoList;
        @FieldName(name = "历史赔案险种信息", type = DroolsConstants.FieldType.COLLECT)
        private List<HistoryPlan> historyPlanList;
    }

    @Data
    public static class HistoryPlan {
        @FieldName(name = "险种代码", type = DroolsConstants.FieldType.BASE)
        private String crtable;
        @FieldName(name = "是否续赔标记", type = DroolsConstants.FieldType.BASE)
        private String sysInd;
        @FieldName(name = "险种非续赔但要是否写入LCAH标记", type = DroolsConstants.FieldType.BASE)
        private String saveInd;
        @FieldName(name = "收据赔付明细信息", type = DroolsConstants.FieldType.BASE)
        private List<Receipt> receiptList;
    }

    @Data
    public static class PolicyInfo {
        @FieldName(name = "保单号码", type = DroolsConstants.FieldType.BASE)
        private String chdrNum;
        @FieldName(name = "理赔审核状态", type = DroolsConstants.FieldType.BASE)
        private String examStatus;
        @FieldName(name = "给付金额", type = DroolsConstants.FieldType.BASE)
        private double reimbursed;
        @FieldName(name = "险种信息", type = DroolsConstants.FieldType.BASE)
        private List<Plan> planList;
    }

    @Data
    public static class Plan {
        @FieldName(name = "险种代码", type = DroolsConstants.FieldType.BASE)
        private String crtable;
        @FieldName(name = "理赔审核状态", type = DroolsConstants.FieldType.BASE)
        private String examStatus;
        @FieldName(name = "险种给付金额", type = DroolsConstants.FieldType.BASE)
        private double clmAmt;
        @FieldName(name = "责任信息", type = DroolsConstants.FieldType.BASE)
        private List<Benifit> benifitList;
    }

    @Data
    public static class Benifit {
        @FieldName(name = "责任代码", type = DroolsConstants.FieldType.BASE)
        private String benCode;
        @FieldName(name = "责任赔付金额", type = DroolsConstants.FieldType.BASE)
        private Double payAmt;//责任赔付金额
        @FieldName(name = "责任总实际赔付天数", type = DroolsConstants.FieldType.BASE)
        private Integer payDay;//责任总实际赔付天数
        @FieldName(name = "实际手术比例", type = DroolsConstants.FieldType.BASE)
        private Double allocPct;//实际手术比例
        @FieldName(name = "双倍给付标识", type = DroolsConstants.FieldType.BASE)
        private String doubleInd;//双倍给付标识
        @FieldName(name = "责任申请金额", type = DroolsConstants.FieldType.BASE)
        private Double requestAmt;//责任申请金额
        @FieldName(name = "责任总申请赔付天数", type = DroolsConstants.FieldType.BASE)
        private Double reqDay;//责任总申请赔付天数
        @FieldName(name = "责任申请手术比例", type = DroolsConstants.FieldType.BASE)
        private Integer presPct;//责任申请手术比例
        @FieldName(name = "免赔额", type = DroolsConstants.FieldType.BASE)
        private Double notPay;//免赔额
        @FieldName(name = "customPay", type = DroolsConstants.FieldType.BASE)
        private Double customPay;//
        @FieldName(name = "扣除累计", type = DroolsConstants.FieldType.BASE)
        private Double receiptDeductAmt;//扣除累计
        @FieldName(name = "本次算费比例", type = DroolsConstants.FieldType.BASE)
        private Double payPct;//本次算费比例
    }


    @Data
    public static class Receipt {
        @FieldName(name = "保单号", type = DroolsConstants.FieldType.BASE)
        private String chdrNum;//保单号
        @FieldName(name = "险种代码", type = DroolsConstants.FieldType.BASE)
        private String crtable;//险种代码
        @FieldName(name = "责任代码", type = DroolsConstants.FieldType.BASE)
        private String benCode;//责任代码
        @FieldName(name = "收据序号", type = DroolsConstants.FieldType.BASE)
        private String clmSeq;//收据序号
        @FieldName(name = "收据就医方式", type = DroolsConstants.FieldType.BASE)
        private String hsMesh;//收据就医方式
        @FieldName(name = "收据类型", type = DroolsConstants.FieldType.BASE)
        private long trecTyp;//收据类型
        @FieldName(name = "收据号", type = DroolsConstants.FieldType.BASE)
        private String reptCode;//收据号
        @FieldName(name = "申请金额", type = DroolsConstants.FieldType.BASE)
        private Double reqAmt;//申请金额
        @FieldName(name = "赔付金额", type = DroolsConstants.FieldType.BASE)
        private Double benefitAmt;//赔付金额
        @FieldName(name = "剩余年度/事故保额", type = DroolsConstants.FieldType.BASE)
        private Double amtPaied;//剩余年度/事故保额
        @FieldName(name = "保单年度余额", type = DroolsConstants.FieldType.BASE)
        private Double yearRemainder;//保单年度余额
        @FieldName(name = "原始年度/事故保额", type = DroolsConstants.FieldType.BASE)
        private Double orginalAmtYear;//原始年度/事故保额
        @FieldName(name = "总保额", type = DroolsConstants.FieldType.BASE)
        private Double totalAmt;//总保额
    }
}
