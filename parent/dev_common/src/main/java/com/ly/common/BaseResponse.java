/********************************************************************************
 * <b>Summary: </b>
 * 文件名：BaseResponse.java
 * 版本信息：$Rev: 1.0.0 $
 * 日期：2019-09-08
 * Copyright (c) 2005, 2019 www.sunline.cn  
 * 版权所有
 * Licensed under the SUNLINE, Version 20190616 (the "License");
 * [History]
 * Version   Date       Author     				Content
 * -------- --------- ---------- ----------------------------
 * 1.0.0   2019-09-08   蒋万勇(QQ:272389138)    $1.0.0$
 ********************************************************************************/

package com.ly.common;


/**
 */
public class BaseResponse {
    private int resultCode = RestCodeConstants.RESULT_SUCCESS;
    private String resultMsg="成功";

    public BaseResponse(int resultCode, String resultMsg) {
        this.resultCode = resultCode;
        this.resultMsg = resultMsg;
    }

    public BaseResponse() {
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }


}
