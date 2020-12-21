package com.security.entity;

//import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
public class User {
//    @TableId
    private Long userId;
    private String username;
    private String password;
}
