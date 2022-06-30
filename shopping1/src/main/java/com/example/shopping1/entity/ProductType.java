package com.example.shopping1.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("producttype")
public class ProductType {

    @TableId(value = "tid",type= IdType.AUTO)
    private Integer tid;
    private String tname;
    private String tlimit;
}
