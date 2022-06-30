package com.example.shopping1.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("shoppingcar")
public class ShoppingCar {
    @TableId(value = "cid",type= IdType.AUTO)
    private Integer cid;
    private String cname;
    private Integer productid;
    private String pname;
    private Integer nums;
    private Integer valuse;
    private String limits;
    private Integer totals;
    private String path;

}
