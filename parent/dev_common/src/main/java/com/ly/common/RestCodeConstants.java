/********************************************************************************
 * <b>Summary: </b>
 * 文件名：RestCodeConstants.java
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
public class RestCodeConstants {
    /** 请求成功返回值 - 0  **/
    public static final int RESULT_SUCCESS = 0;
    
    /** 参数校验异常 400-40001 **/
    public static final int EX_PARAM_VALIDATE_CODE = 40001;
    
    /** 用户  401-40101 **/
    public static final int EX_USER_INVALID_CODE = 40101;
    /** 用户  401-40102 **/
    public static final int EX_USER_PASS_INVALID_CODE = 40102;
    /** 用户  401-40103 **/
    public static final int EX_USER_ID_INVALID_CODE = 40103;
    
    /** 客户端 402-40201 **/
    public static final int EX_CLIENT_INVALID_CODE = 40201;
    /** 客户端 402-40202 **/
    public static final int EX_CLIENT_FORBIDDEN_CODE = 40202;
    
    /** Token异常  403-40301 **/
    public static final int TOKEN_FORBIDDEN_CODE = 40301;
    /** Token异常  403-40302 **/
    public static final int TOKEN_ERROR_CODE = 40302;
    
    /** 其他未知异常 500 **/
    public static final int EX_OTHER_CODE = 500;
}
