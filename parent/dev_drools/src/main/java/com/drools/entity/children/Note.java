package com.drools.entity.children;

import com.drools.common.annotation.EntityName;
import lombok.Data;

@Data
@EntityName(name="照会")
public class Note {
    public String chdrNum = "";     // LCAL.CHDRNUM   保单号
    public String pnCode = "";      // LCAL.PNCODE      照会代码
    public int    pnDate;           // LCAL.PNDATE     照会日期
    public String reslvCde = "";    // LCAL.RESLVCDE  解决代码
    public String reslvUsr = "";    // LCAL.RESLVUSR  解决人
    public int    reslvDte;         // LCAL.RESLVDTE  解决日期
    public String trecTyp;          // LCAL.TRECTYP 照会来源  1-资料建文件/2-文件审核/3-使用者输入/4-审核/5-给付运算/6-VS数据转换/7-交付调查/8
    public int    crtDate;          //  LCAL.CRTDATE 创建日期
    public int    endDate;          //  LCAL.ENDDATE 止期 从LCCG读取INTERVAL+today
    public int    remindDate;       //  LCAL.REMINDDATE 催缴期 从LCCG读取REMINDDAY+today
    public String remark01 = "";    //  LCAL.REMARK01 补充说明01
    public String remark02 = "";    //  LCAL.REMARK02 补充说明02
    public int    letterPrintDate;  //  LCAL.LETTER_PRINT_DATE 打印日期
    public int    jobNo;            //  LCAL.JOBNO
    public int    prnDate01;        //  LCAL.PRNDATE01
    public int    prnDate02;        //  LCAL.PRNDATE02
    public int    prnDate03;        //  LCAL.PRNDATE03
    public int    prnDate04;        //  LCAL.PRNDATE04
    public int    prnDate05;        //  LCAL.PRNDATE05
    public int    prnDate06;        //  LCAL.PRNDATE06
    public String prtOpt = "";      //  LCAL.PRTOPT 是否列印 Y/N，来自LCCG.PRTOPT
    public String status = "N";     //  更新状态 Y有修改/N无修改
}
