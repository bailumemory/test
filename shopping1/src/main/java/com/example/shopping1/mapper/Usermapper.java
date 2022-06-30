package com.example.shopping1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.shopping1.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface Usermapper extends BaseMapper<User> {

}
