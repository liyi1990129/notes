package com.stu.drools.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 描述：
 */
@Data
public class BaseModel implements Serializable {
    //创建人
    private Long creUserId;
    //创建时间
    private Date creTime;
    //是否有效
    private String isEffect;
    //备注
    private String remark;

}
