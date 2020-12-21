package com.drools.model.sys;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 业务流水号表
 *
 * @author YJJ
 * @email 840956069@qq.com
 * @date 2018-06-12 15:54:52
 */
@Data
@Table(name = "sys_sequence")
public class SysSequence   {
    @Id
    @Column(name = "id")
    private Long id;//主键

    //流水号名称
    @Column(name = "seq_name")
    private String seqName;

    //当前流水号
    @Column(name = "current_value")
    private Long currentValue;

    //递增
    @Column(name = "increment")
    private Integer increment;


}
