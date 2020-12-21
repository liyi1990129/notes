package com.drools.mapper.sys;

import com.drools.model.sys.SysDictMain;
import tk.mybatis.mapper.common.Mapper;

public interface SysDictMainMapper extends Mapper<SysDictMain> {
    void add(SysDictMain info);
}
