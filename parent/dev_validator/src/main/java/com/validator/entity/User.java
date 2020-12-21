package com.validator.entity;

import com.validator.common.PasswordEqual;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@PasswordEqual
public class User {
    private Long userId;

    @NotNull(message = "不能为空")
    @Size(max = 16, min = 6, message = "字符串长度需要在6-16之间")
    private String username;
    @Max(value = 100, message = "最大100")
    @Min(value = 6, message = "最小6")
    private String password;
}
