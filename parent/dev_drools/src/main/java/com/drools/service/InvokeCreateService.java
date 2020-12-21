package com.drools.service;

import com.drools.common.inters.createapi.CreateReq;
import com.drools.common.inters.createapi.CreateRes;

public interface InvokeCreateService {
    /**
     * 资料建档调用规则
     */
    public CreateRes invokeCreate(CreateReq createReq);

    /**
     * 资料建档动态调用规则
     */
    public CreateRes invokeCreateByInterfaceId(CreateReq createReq);
}
