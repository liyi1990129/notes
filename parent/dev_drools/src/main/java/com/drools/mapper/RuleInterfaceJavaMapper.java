package com.drools.mapper;

import com.drools.model.RuleInterfaceJava;
import tk.mybatis.mapper.common.Mapper;

public interface RuleInterfaceJavaMapper extends Mapper<RuleInterfaceJava> {

    Long findMaxVersion(Long interfaceId);
}
