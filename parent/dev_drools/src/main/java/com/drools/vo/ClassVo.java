package com.drools.vo;

import lombok.Data;

import java.util.List;

@Data
public class ClassVo {
    private String className;
    private String name;
    private String type;
    private String desc;
    private List<String> props;
    private List<ClassVo> params;
}
