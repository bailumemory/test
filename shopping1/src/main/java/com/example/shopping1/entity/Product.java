package com.example.shopping1.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("productss")
public class Product {
    @TableId(value = "productId",type= IdType.AUTO)
    private Integer productId;
    private String pname;
    private Integer pnum;
    private Integer valuse;
    private String type;
    private String information;
    private String path;

}
