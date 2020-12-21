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
@Table(name = "event_date")
public class EventDate {

    @Id
    @Column(name = "id")
    private Long id;//主键
    private String crtable;//'险种代码',
    private String startTime;//'版本起期',
    private String cltdobCon;//'出险年龄条件1：出险年龄大于65',
    private String clmType;//'理赔形态',
    private String acdtDteCon;//'意外事故日\r\n1：等于0\r\n，2：不等于0\r\n',
    private String earlyHsDte;//'入院日期是否为空\r\n1.是，\r\n2.不是',
    private String eventDate;//'事故认定日',
    private String creTime;

    @Transient
    List<EventDate> list;

}
