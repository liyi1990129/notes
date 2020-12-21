package com.drools.entity.children;

import lombok.Data;

@Data
public class DfmtGroup {
    public String  groupNo = "";   //伤残分组组号
    public String[] formulaIllcodes; // 当前分组表对应的伤病代码

    public String examStatus = "";//审核状态
    public String  formulaStr = ""; //  本组赔付公式
    public String  calDesc = "";//  本组赔付公式备注
    public double agePct = 0;  //  本组年龄系数
    public double illPct = 0;  //  本组伤病系数

    public String  illcode = "";   //获取本组最高等级的伤病代码
}
