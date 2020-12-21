package com.drools.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/**
 * 描述：
 */
@Data
@Table(name = "benefit_formula")
public class BenefitFormula {

    @Id
    @Column(name = "id")
    private Long id;//主键
    private String crtable;//'险种代码',
    private String benCode;// '责任代码',
    private String startTime;// '版本起期',
    private String endTime;//'版本止期',
    private String clmType;//'理赔形态',
    private String dateType;//'日期条件',
    private String ageType;//'年龄条件',
    private String arrivalAgeType;//'到达年龄',
    private String paieSaveType;//'领取生存金条件',
    private String illType;//'伤病条件',
    private String illCode;//'伤病代码',
    private String placeType;//'事故地点条件',
    private String placeCode;//'事故地点代码',
    private String surgeryType;//'手术代码条件',
    private String surgeryCode;//'手术代码',
    private String surgeryTimeType;//'手术时间条件',
    private String formula;//'公式',
    private String remark;//'公式说明',
    private String agePct;//'年龄系数',
    private String illPct;//'伤病系数',
    private String creUserId;
    private String creTime;

    @Transient
    private List<BenefitFormula> list;

}
