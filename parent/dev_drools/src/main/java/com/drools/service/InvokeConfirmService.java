package com.drools.service;

import com.drools.common.inters.confirmapi.ConfirmReq;
import com.drools.common.inters.confirmapi.ConfirmRes;

public interface InvokeConfirmService {
    /**
     * 资料确认调用规则
     */
    public ConfirmRes invokeConfirm(ConfirmReq confirmReq);
    /**
     * 资料确认动态调用规则
     */
    public ConfirmRes invokeConfirmByInterfaceId(ConfirmReq confirmReq);
}
