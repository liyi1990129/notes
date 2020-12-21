package com.drools.common.inters.auditapi;

import com.drools.common.DroolsConstants;
import com.drools.common.annotation.FieldName;
import com.drools.util.DateUtil;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AuditRes {
    @FieldName(name = "头部信息",type = DroolsConstants.FieldType.OBJECT)
    public AuditResHead head;
    @FieldName(name = "主体信息",type = DroolsConstants.FieldType.OBJECT)
    public AuditResBody body;


    @Data
    public static class AuditResHead {
        @FieldName(name = "接口响应代码",type = DroolsConstants.FieldType.BASE)
        private String code;//接口响应代码 错误代码；0成功，其它失败
        @FieldName(name = "接口返回信息",type = DroolsConstants.FieldType.BASE)
        private String message;//接口返回信息 错误信息
        @FieldName(name = "流水号",type = DroolsConstants.FieldType.BASE)
        private String transactionNo;//流水号
        @FieldName(name = "响应时间",type = DroolsConstants.FieldType.BASE)
        private String timeStamp;//响应时间 格式：yyyy-MM-dd HH:mm:ss

        public AuditResHead(){}
        public AuditResHead(String code,String message,String transactionNo){
            this.code = code;
            this.message = message;
            this.transactionNo = transactionNo;
            this.timeStamp = DateUtil.get4yMdHms(new Date());
        }
    }

    @Data
    public static class AuditResBody {
        @FieldName(name = "理赔业务号",type = DroolsConstants.FieldType.BASE)
        private String clmnum;//理赔业务号
        @FieldName(name = "理赔审核状态", type = DroolsConstants.FieldType.BASE)
        private String examStatus;//理赔审核状态

        @FieldName(name = "照会",type = DroolsConstants.FieldType.COLLECT)
        private List<Note> noteList;//照会
        @FieldName(name = "保单信息",type = DroolsConstants.FieldType.COLLECT)
        private List<PolicyInfo> policyInfoList;
        @FieldName(name = "历史赔案险种信息",type = DroolsConstants.FieldType.COLLECT)
        private List<HistoryPlan> historyPlanList;
    }

    @Data
    public static class HistoryPlan {
        @FieldName(name = "险种代码",type = DroolsConstants.FieldType.BASE)
        private String crtable;
        @FieldName(name = "是否续赔标记",type = DroolsConstants.FieldType.BASE)
        private String sysInd;
        @FieldName(name = "险种非续赔但要是否写入LCAH标记",type = DroolsConstants.FieldType.BASE)
        private String saveInd;
        @FieldName(name = "历史赔案责任信息",type = DroolsConstants.FieldType.BASE)
        private List<HistoryBenifit> historyBenifitList;
    }
    @Data
    public static class PolicyInfo {
        @FieldName(name = "保单号码",type = DroolsConstants.FieldType.BASE)
        private String chdrNum;
        @FieldName(name = "理赔审核状态",type = DroolsConstants.FieldType.BASE)
        private String examStatus;
        @FieldName(name = "险种信息",type = DroolsConstants.FieldType.BASE)
        private List<Plan> planList;
    }
    @Data
    public static class Plan {
        @FieldName(name = "险种代码",type = DroolsConstants.FieldType.BASE)
        private String crtable;
        @FieldName(name = "理赔审核状态",type = DroolsConstants.FieldType.BASE)
        private String examStatus;
        @FieldName(name = "事故认定日",type = DroolsConstants.FieldType.BASE)
        private long eventDte;
        @FieldName(name = "责任信息",type = DroolsConstants.FieldType.BASE)
        private List<Benifit> benifitList;
    }
    @Data
    public static class Benifit {
        @FieldName(name = "责任代码",type = DroolsConstants.FieldType.BASE)
        private String benCode;
        @FieldName(name = "责任赔付公式",type = DroolsConstants.FieldType.BASE)
        private String formulaStr;
        @FieldName(name = "责任赔付公式备注",type = DroolsConstants.FieldType.BASE)
        private String calDesc;
        @FieldName(name = "本次年龄系数",type = DroolsConstants.FieldType.BASE)
        private Double agePct;
        @FieldName(name = "本次伤病系数",type = DroolsConstants.FieldType.BASE)
        private Double illPct;
    }
    @Data
    public static class HistoryBenifit {
        @FieldName(name = "责任代码",type = DroolsConstants.FieldType.BASE)
        private String benCode;
        @FieldName(name = "保额续赔",type = DroolsConstants.FieldType.BASE)
        private String joinAmt;
        @FieldName(name = "年度续赔",type = DroolsConstants.FieldType.BASE)
        private String joinYear;
        @FieldName(name = "同事故续赔",type = DroolsConstants.FieldType.BASE)
        private String joinAccident;
        @FieldName(name = "次数续赔",type = DroolsConstants.FieldType.BASE)
        private String joinTime;
        @FieldName(name = "其他续赔",type = DroolsConstants.FieldType.BASE)
        private String joinOther;
    }
    @Data
    public static class Note {
        @FieldName(name = "照会代码",type = DroolsConstants.FieldType.BASE)
        private String pnCode;//照会代码
        @FieldName(name = "照会来源",type = DroolsConstants.FieldType.BASE)
        private String trecTyp;//照会来源   1-资料建文件/2-文件审核/3-使用者输入/4-审核/5-给付运算/6-VS数据转换/7-交付调查/8
        @FieldName(name = "照会说明",type = DroolsConstants.FieldType.BASE)
        private String pndesc;//照会说明
    }
}
