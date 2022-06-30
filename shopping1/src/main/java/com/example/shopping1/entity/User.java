package com.example.shopping1.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user")
@Data //简化版get set
public class User {

    @TableId(type= IdType.AUTO)
    private Integer id;
    private String username;
    private String password;
    private String sex;
    private String degrees;
    private String limits;
    private Integer moneys;

}
