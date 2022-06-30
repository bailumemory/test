package com.example.shopping1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shopping1.entity.Product;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface Productmapper extends BaseMapper<Product> {
}
