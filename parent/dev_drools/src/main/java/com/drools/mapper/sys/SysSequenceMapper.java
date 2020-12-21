package com.drools.mapper.sys;

import com.drools.model.sys.SysSequence;
import tk.mybatis.mapper.common.Mapper;

public interface SysSequenceMapper extends Mapper<SysSequence> {
    public Long getPrdSequence(SysSequence sysSequence);
}
