package com.stu.drools.entity;

import com.stu.drools.common.FieldName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Claim {
    @FieldName(name="价格")
    private BigDecimal price;
    @FieldName(name="开始时间")
    private Date beginTime;
}
