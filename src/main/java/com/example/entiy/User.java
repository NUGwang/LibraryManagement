package com.example.entiy;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
@TableName("user")
@Data
public class User {
    private long id;
    private String name;
    private String pwd;
}
