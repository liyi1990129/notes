
package com.drools.common;

/**
 *
 案例数据：
 {
    "data":[
        {
            "option":"op1",
            "value":"1234"
        },
        {
            "option":"op2",
            "value":"332"
        }
    ],
    "resultCode":0,
    "resultMsg":"success"
}

 */
public class ObjectRestResponse<T> extends BaseResponse {

    T data;

    public ObjectRestResponse( ) {
    }
    
    public ObjectRestResponse data(T data) {
    	this.setData(data);
        return this;
    }
    
    public ObjectRestResponse(int resultCode, String resultMsg, T data) {
        this.setResultCode(resultCode);
        this.setResultMsg(resultMsg);
        this.data = data;
    }
    
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    /**
     * 不推荐使用
     * 
     * 设置错误提示信息，默认返回resultCode=500
     *
     * @param msg 提示信息
     * @author zdh
     * @modifyTime 2019/11/26 16:32:00
     */
    public void setErrorMsg(String msg) {
        this.setResultCode(RestCodeConstants.EX_OTHER_CODE);
        this.setResultMsg(msg);
    }
    /**
     * 不推荐使用
     * 
     * 设置成功信息，默认返回resultCode=0
     *
     * @param msg 提示信息
     * @author lh
     * @modifyTime 2019年12月4日 14:50:01
     */
    public void setSuucessMsg(String msg) {
        this.setResultCode(RestCodeConstants.RESULT_SUCCESS);
        this.setResultMsg(msg);
    }
    
}
