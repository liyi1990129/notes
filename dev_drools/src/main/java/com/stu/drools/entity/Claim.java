package com.stu.drools.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class Claim {
    private BigDecimal price;
    private Date beginTime;
}
