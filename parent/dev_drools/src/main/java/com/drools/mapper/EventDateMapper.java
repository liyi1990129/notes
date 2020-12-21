package com.drools.mapper;

import com.drools.model.EventDate;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 描述：
 */
public interface EventDateMapper extends Mapper<EventDate> {


    List<EventDate> findByCrtable(@Param("crtable") String crtable);

    List<EventDate> list(EventDate eventDate);

}
