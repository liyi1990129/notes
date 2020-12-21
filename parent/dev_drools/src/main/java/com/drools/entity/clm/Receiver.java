package com.drools.entity.clm;

import com.drools.common.DroolsConstants;
import com.drools.common.annotation.EntityName;
import com.drools.common.annotation.FieldName;
import lombok.Data;

@Data
@EntityName(name="受款人")
public class Receiver {
    @FieldName(name="受款人ID",type = DroolsConstants.FieldType.BASE)
    public String clntNum = "";          // LCAM.CLNTNUM 受款人ID
    @FieldName(name="账户类别",type = DroolsConstants.FieldType.BASE)
    public String countType = "";        // LCAM.FLAG01 账户类别 1-续期账户/2-其他账户
    @FieldName(name="续期账户关联保单号",type = DroolsConstants.FieldType.BASE)
    public String chdrNum01 = "";        // LCAM.CHDRNUM01  续期账户关联保单号
    @FieldName(name="给付方式",type = DroolsConstants.FieldType.BASE)
    public String reqnType = "";         // LCAM.REQNTYPE 给付方式 5-自动转账，0-现金，2-人工支票，6-电汇
    @FieldName(name="银行账号",type = DroolsConstants.FieldType.BASE)
    public String bankCount = "";   // LCAM.BANKACOUNT  银行账号，通过PAS判断是否复核
    @FieldName(name="账户在PAS中复核状态",type = DroolsConstants.FieldType.BASE)
    public String procInd = "";          // CLRN.PROCIND 账户在PAS中复核状态  01:审核中/02:使用中/04:审核不通过/07:审核不通过/05:使用中/06:新建/08:审核中

    // 扩展属性
    public String ext1;
    public String ext2;
    public String ext3;
}
