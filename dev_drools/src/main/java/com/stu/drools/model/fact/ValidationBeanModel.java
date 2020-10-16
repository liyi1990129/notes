package com.stu.drools.model.fact;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class ValidationBeanModel {
    @NotEmpty(message="用户id不能为空")
    private String userId;

    @Email(message="非法邮件地址")
    private String email;

    @Pattern(regexp="^(\\d{6})(\\d{4})(\\d{2})(\\d{2})(\\d{3})([0-9]|X)$",message="身份证号不合规")
    private String cardNo;

    @Length(min=1,max=10,message="昵称长度必须在1~10")
    private String nickName;

    @Range(min=0,max=2,message="非法性别")
    private String sex;

    @Max(value=100,message="非法年龄")
    private int age;
}
