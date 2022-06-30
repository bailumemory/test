package com.example.shopping1.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shopping1.comman.Result;
import com.example.shopping1.entity.User;
import com.example.shopping1.mapper.Usermapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    Usermapper usermapper;

    //新增用户
    @PostMapping
    public Result<?> save(@RequestBody  User use){
        usermapper.insert(use);
        return Result.success();
    }

    //更新
    @PutMapping("/updateUser")
    public Result<?> update(@RequestBody  User use){
        usermapper.updateById(use);
        return Result.success();
    }


    @GetMapping("/searchUser")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search,
                              @RequestParam(defaultValue = "") String degrees){
        LambdaQueryWrapper<User> wrapper=Wrappers.<User>lambdaQuery();
        Page<User> userPage=null;
        //营销经理
        if(degrees.equals("营销经理")){
            if(StrUtil.isNotBlank(search)){
                wrapper.like(User::getUsername,search).eq(User::getDegrees,4);
            }
            else{
                wrapper.eq(User::getDegrees,"普通用户");
            }
            userPage=usermapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        }
        //系统管理员
        if(degrees.equals("管理员")){
            if(StrUtil.isNotBlank(search)){
                wrapper.like(User::getUsername,search).ne(User::getDegrees,1);
            }
            else{
                wrapper.ne(User::getDegrees,"管理员");
            }
            userPage=usermapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        }
        return Result.success(userPage);
    }

    @DeleteMapping("/deleteUser/{id}")
    public Result<?> delete(@PathVariable long id){
        usermapper.deleteById(id);
        return Result.success();
    }

    @PostMapping("/login")
    public Result<?> login(@RequestBody  User user){
        User res=usermapper.selectOne(Wrappers.<User>lambdaQuery()
                .eq(User::getUsername,user.getUsername())
                .eq(User::getPassword,user.getPassword())
                .eq(User::getDegrees,user.getDegrees()));
        if(res==null){
            return Result.error("-1","用户名,密码或用户类型错误");
        }
        if(res.getLimits().equals("禁用")){
            return Result.error("-1","该用户被禁用了");
        }
        return Result.success(res);
    }

    @PostMapping("/register")
    public Result<?> register(@RequestBody  User user){
        User res=usermapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,user.getUsername()));
        if(res!=null)
        {
            return Result.error("-1","用户名已经存在");
        }
        user.setDegrees("普通用户");
        user.setLimits("启用");
        user.setSex("男");
        user.setMoneys(0);
        usermapper.insert(user);
        return Result.success();
    }

}
