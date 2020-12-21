package com.drools.common.inters.createapi;

import com.drools.common.DroolsConstants;
import com.drools.common.annotation.FieldName;
import com.drools.util.DateUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/* *
 * 建档返回对象 
 * @author ly
 * @modifyTime 2020/11/20 14:25:00
 */
@Data
public class CreateRes implements Serializable {
    @FieldName(name = "头部信息",type = DroolsConstants.FieldType.OBJECT)
    public CreateResHead head;
    @FieldName(name = "主体信息",type = DroolsConstants.FieldType.OBJECT)
    public CreateResBody body;


    @Data
    public static class CreateResHead {
        @FieldName(name = "接口响应代码",type = DroolsConstants.FieldType.BASE)
        private String code;//接口响应代码 错误代码；0成功，其它失败
        @FieldName(name = "接口返回信息",type = DroolsConstants.FieldType.BASE)
        private String message;//接口返回信息 错误信息
        @FieldName(name = "流水号",type = DroolsConstants.FieldType.BASE)
        private String transactionNo;//流水号
        @FieldName(name = "响应时间",type = DroolsConstants.FieldType.BASE)
        private String timeStamp;//响应时间 格式：yyyy-MM-dd HH:mm:ss

        public CreateResHead(){}
        public CreateResHead(String code,String message,String transactionNo){
            this.code = code;
            this.message = message;
            this.transactionNo = transactionNo;
            this.timeStamp = DateUtil.get4yMdHms(new Date());
        }
    }

    @Data
    public static class CreateResBody {
        @FieldName(name = "理赔业务号",type = DroolsConstants.FieldType.BASE)
        private String clmnum;//理赔业务号
        @FieldName(name = "照会",type = DroolsConstants.FieldType.COLLECT)
        private List<Note> noteList;//照会
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



