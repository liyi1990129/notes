package com.drools.common.inters.createapi;

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
 * 建档请求对象 
 * @author ly
 * @modifyTime 2020/11/20 14:25:00
 */
@Data
public class CreateReq extends BaseReq {
    @Valid
    @FieldName(name = "头部信息",type = DroolsConstants.FieldType.OBJECT)
    private CreateReqHead head;
    @Valid
    @FieldName(name = "报文主体信息",type = DroolsConstants.FieldType.OBJECT)
    private CreateReqBody body;

    @Data
    public static class CreateReqHead {
        @NotBlank(message = "流水号不能为空")
        @FieldName(name = "流水号",type = DroolsConstants.FieldType.BASE,remark="交易流水号，用于唯一标识")
        private String transactionNo;//流水号  交易流水号，用于唯一标识
        @NotBlank(message = "请求时间不能为空")
        @FieldName(name = "流水号",type = DroolsConstants.FieldType.BASE,remark="YYYY-MM-DD HH:MM:SS")
        private String timeStamp;//请求时间   YYYY-MM-DD HH:MM:SS
        @NotBlank(message = "接口选择不能为空")
        @FieldName(name = "流水号",type = DroolsConstants.FieldType.BASE,remark="invokeCreate")
        @IsCode(code="invokeCreate",message = "接口选择不正确")
        private String visitCode;//接口选择  "invokeCreate"
        @FieldName(name = "数据来源",type = DroolsConstants.FieldType.BASE)
        @NotBlank(message = "数据来源不能为空")
        private String requestFrom;//数据来源
    }

    @Data
    public static class CreateReqBody{
        @Valid
        @FieldName(name = "基本信息",type = DroolsConstants.FieldType.OBJECT)
        private BaseInfo baseInfo;
        @Valid
        @FieldName(name = "营销员信息",type = DroolsConstants.FieldType.OBJECT)
        private AgentInfo agentInfo;
        @Valid
        @FieldName(name = "医生信息",type = DroolsConstants.FieldType.COLLECT)
        private List<Doctor> doctorList;
        @Valid
        @FieldName(name = "伤病代码信息",type = DroolsConstants.FieldType.COLLECT)
        private List<IllCode> illcodeList;

        @Valid
        @FieldName(name = "受款人信息",type = DroolsConstants.FieldType.COLLECT)
        private List<Receiver> receiverList;

    }

    @Data
    public static class Receiver{
        @FieldName(name="受款人ID",type = DroolsConstants.FieldType.BASE)
        public String clntNum = "";          // LCAM.CLNTNUM 受款人ID
        @FieldName(name="账户类别",type = DroolsConstants.FieldType.BASE,remark="1-续期账户/2-其他账户")
        public String countType = "";        // LCAM.FLAG01 账户类别 1-续期账户/2-其他账户
        @FieldName(name="续期账户关联保单号",type = DroolsConstants.FieldType.BASE)
        public String chdrNum01 = "";        // LCAM.CHDRNUM01  续期账户关联保单号
        @FieldName(name="给付方式",type = DroolsConstants.FieldType.BASE,remark="5-自动转账，0-现金，2-人工支票，6-电汇")
        public String reqnType = "";         // LCAM.REQNTYPE 给付方式 5-自动转账，0-现金，2-人工支票，6-电汇
        @FieldName(name="银行账号",type = DroolsConstants.FieldType.BASE)
        public String bankCount = "";   // LCAM.BANKACOUNT  银行账号，通过PAS判断是否复核
        @FieldName(name="账户在PAS中复核状态",type = DroolsConstants.FieldType.BASE)
        public String procInd = "";
    }
    @Data
    public static class IllCode{
        @FieldName(name = "伤病代码id",type = DroolsConstants.FieldType.BASE)
        private String illCode;//伤病代码id
    }

    @Data
    public static class BaseInfo {
        @FieldName(name = "理赔业务号",type = DroolsConstants.FieldType.BASE)
        @NotBlank(message = "理赔业务号不能为空")
        private String clmnum;//理赔业务号
        @FieldName(name = "分公司",type = DroolsConstants.FieldType.BASE)
        @NotBlank(message = "分公司不能为空")
        private String clmcoy;//分公司
        @FieldName(name = "分支机构",type = DroolsConstants.FieldType.BASE)
        @NotBlank(message = "分支机构不能为空")
        private String branch;//分支机构
        @FieldName(name = "事故人ID",type = DroolsConstants.FieldType.BASE)
        @NotBlank(message = "事故人ID不能为空")
        private String secuityNo;//事故人ID
        @FieldName(name = "事故人客户号",type = DroolsConstants.FieldType.BASE)
        @NotBlank(message = "事故人客户号不能为空")
        private String clntNum;//事故人客户号
        @FieldName(name = "事故人性别",type = DroolsConstants.FieldType.BASE)
        @NotBlank(message = "事故人性别不能为空")
        private String cltSex;//事故人性别
        @FieldName(name = "事故人出生日期",type = DroolsConstants.FieldType.BASE)
        @NotNull(message = "事故人出生日期不能为空")
        private long cltDob;//事故人出生日期
        @FieldName(name = "受理日期",type = DroolsConstants.FieldType.BASE)
        @NotNull(message = "受理日期不能为空")
        private long submitDte;//受理日期
        @FieldName(name = "报备日期",type = DroolsConstants.FieldType.BASE)
        @NotNull(message = "报备日期不能为空")
        private long reportDte;//报备日期
        @FieldName(name = "理赔处理日期",type = DroolsConstants.FieldType.BASE)
        @NotNull(message = "理赔处理日期不能为空")
        private long proceDate;//理赔处理日期
        @FieldName(name = "事故认定日",type = DroolsConstants.FieldType.BASE)
        @NotNull(message = "事故认定日不能为空")
        private long eventDte;//事故认定日
        @FieldName(name = "意外事故日",type = DroolsConstants.FieldType.BASE)
        private long acdtDte;//意外事故日
        @FieldName(name = "身故/残疾/重大疾病认定日",type = DroolsConstants.FieldType.BASE)
        private long criticalDte;//身故/残疾/重大疾病认定日
        @FieldName(name = "意外事故代码",type = DroolsConstants.FieldType.BASE)
        private String acdtCode;//意外事故代码
        @FieldName(name = "意外发生地",type = DroolsConstants.FieldType.BASE)
        private String eventPlace;//意外发生地
        @FieldName(name = "除外责任",type = DroolsConstants.FieldType.BASE)
        private String excludeBenefit;//除外责任
        @FieldName(name = "人工修改手术比例标志",type = DroolsConstants.FieldType.BASE)
        @NotBlank(message = "人工修改手术比例标志不能为空")
        private String manualSurgeryFlag;//人工修改手术比例标志
        @NotBlank(message = "理赔型态不能为空")
        @FieldName(name = "理赔型态",type = DroolsConstants.FieldType.BASE)
        private String claimType;//理赔型态
        @FieldName(name = "案件型态",type = DroolsConstants.FieldType.BASE)
        @NotBlank(message = "案件型态不能为空")
        private String caseType;//案件型态
    }

    @Data
    public static class AgentInfo {
        @FieldName(name = "营销员ID",type = DroolsConstants.FieldType.BASE)
        @NotBlank(message = "营销员ID不能为空")
        private String agtId;//营销员ID
        @FieldName(name = "营销员入职日期",type = DroolsConstants.FieldType.BASE)
        private long agtFromDate;//营销员入职日期
        @FieldName(name = "营销员离职日期",type = DroolsConstants.FieldType.BASE)
        private long agtToDate;//营销员离职日期
    }

    @Data
    public static class Doctor {
        @FieldName(name = "就医序号",type = DroolsConstants.FieldType.BASE)
        @NotNull(message = "就医序号不能为空")
        private int recId;//就医序号
        @FieldName(name = "医院代码",type = DroolsConstants.FieldType.BASE)
        @NotBlank(message = "医院代码不能为空")
        private String hsCode;//医院代码
        @FieldName(name = "医院类型",type = DroolsConstants.FieldType.BASE)
        private String trecTyp;//医院类型
        @FieldName(name = "医院有效标记",type = DroolsConstants.FieldType.BASE)
        private String validFlag;//医院有效标记
        @FieldName(name = "医院等级",type = DroolsConstants.FieldType.BASE)
        private String hclass;//医院等级
        @FieldName(name = "赔付范围",type = DroolsConstants.FieldType.BASE)
        private String sumind;//赔付范围
        @FieldName(name = "医院品质",type = DroolsConstants.FieldType.BASE)
        private String hqind;//医院品质
        @FieldName(name = "就医日期",type = DroolsConstants.FieldType.BASE)
        @NotNull(message = "就医日期不能为空")
        private int consultDte;//就医日期
        @FieldName(name = "一般病房住院日期",type = DroolsConstants.FieldType.BASE)
        @NotNull(message = "一般病房住院日期不能为空")
        private int hsDte;//一般病房住院日期
        @FieldName(name = "一般病房出院日期",type = DroolsConstants.FieldType.BASE)
        @NotNull(message = "一般病房出院日期不能为空")
        private int leaveDte;//一般病房出院日期
        @FieldName(name = "一般病房住院天数",type = DroolsConstants.FieldType.BASE)
        @NotNull(message = "一般病房住院天数不能为空")
        private int hsDays;//一般病房住院天数
        @FieldName(name = "加护病房住院日期",type = DroolsConstants.FieldType.BASE)
        @NotNull(message = "加护病房住院日期不能为空")
        private int icuHsDte;//加护病房住院日期
        @FieldName(name = "加护病房出院日期",type = DroolsConstants.FieldType.BASE)
        @NotNull(message = "加护病房出院日期不能为空")
        private int icuLeaveDte;//加护病房出院日期
        @FieldName(name = "加护病房住院天数",type = DroolsConstants.FieldType.BASE)
        @NotNull(message = "加护病房住院天数不能为空")
        private int icuDays;//加护病房住院天数
        @Valid
        @FieldName(name = "手术信息",type = DroolsConstants.FieldType.COLLECT)
        private List<Surgery> surgeryList;
    }

    @Data
    public static class Surgery {
        @FieldName(name = "手术代码",type = DroolsConstants.FieldType.BASE)
        private String surgCode;//手术代码
        @FieldName(name = "手术日期",type = DroolsConstants.FieldType.BASE)
        private int surgDte;//手术日期
        @FieldName(name = "同一次手术序号",type = DroolsConstants.FieldType.BASE)
        private String surgSeq;//同一次手术序号
        @FieldName(name = "手术比例",type = DroolsConstants.FieldType.BASE)
        private String surgPct;//手术比例
    }
}

