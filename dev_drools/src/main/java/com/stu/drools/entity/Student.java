package com.stu.drools.entity;

import com.stu.drools.common.FieldName;
import lombok.Data;

@Data
public class Student {
    @FieldName(name="年龄")
    private Integer age;
    @FieldName(name="性别")
    private String sex;
    @FieldName(name="姓名")
    private String name;
    @FieldName(name="班级")
    private String clsName;
    @FieldName(name="成绩")
    private Integer score;
    @FieldName(name="科目")
    private String subject;
}
