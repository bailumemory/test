package com.example.shopping1.entity;


import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import java.sql.Date;

@Data
@TableName("orderform")
public class OrderForm {

    @TableId(value="oid",type= IdType.AUTO)
    private Integer oid;
    private String username;
    private Integer pid;
    private String pname;
    private Integer valuse;
    private Integer totals;
    private Integer nums;
    private String path;
    private String times;

}
