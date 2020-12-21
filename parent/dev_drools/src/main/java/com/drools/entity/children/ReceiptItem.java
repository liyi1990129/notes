package com.drools.entity.children;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/* *
 * 赔案收据项目信息
 * @author ly
 * @modifyTime 2020/11/18 16:55:00
 */
@Data
public class ReceiptItem {
    public String  reptCode = "";     // LCAF.REPTCODE  收据
    public String  benCode = "";     // LCCD.BENCODE  收据号与SubBen对应
    public int  seqNo;     // LCAF.SEQNO  细项序号

    public double  xAmt01;     // LCAF.XAMT01 项目金额

    public double  tempAmt;  // 临时金额
    public double  orginalAmt;  // 项目原始金额

    public double  tempF07;  // 针对F07分摊的而专门设置

    public double  reqAmt;  // 申请金额，原始xAmt01金额

    public int  timesUse;      // LCAF.TIMESUSE

    public Map benefitAmt = new HashMap();  // 用责任做KEY，记录收据项对每个责任的赔付金额
}
