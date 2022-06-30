package com.example.shopping1.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shopping1.comman.Result;
import com.example.shopping1.entity.Product;
import com.example.shopping1.entity.ProductType;
import com.example.shopping1.mapper.Productmapper;
import com.example.shopping1.mapper.Typemapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/type")
public class ProductTypeController {

    @Resource
    Typemapper typemapper;
    @Resource
    Productmapper productmapper;

    @GetMapping("/searchType")
    public Result<?> findType(@RequestParam(defaultValue = " ") String limit){
        LambdaQueryWrapper<ProductType> wrapper= Wrappers.<ProductType>lambdaQuery();
        if(limit.equals("启用")){
            wrapper.eq(ProductType::getTlimit,limit);
        }
        List<ProductType> res=typemapper.selectList(wrapper);
        return Result.success(res);
    }

    @GetMapping("/searchAll")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search){
        LambdaQueryWrapper<ProductType> wrapper=Wrappers.lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(ProductType::getTname,search);
        }
        Page<ProductType> res=typemapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.success(res);
    }

    @PostMapping("/addType")
    public Result<?> save(@RequestBody ProductType type){
        LambdaQueryWrapper<ProductType> wrapper=Wrappers.lambdaQuery();
        ProductType res=typemapper.selectOne(wrapper.eq(ProductType::getTname,type.getTname()));
        if(res!=null){
            return Result.error("-1","该类型已经存在");
        }
        typemapper.insert(type);
        return Result.success();
    }

    @PutMapping("/updateType")
    public Result<?> update(@RequestBody ProductType type){
        LambdaQueryWrapper<ProductType> wrapper=Wrappers.lambdaQuery();
        LambdaQueryWrapper<Product> wrapper1=Wrappers.lambdaQuery();
        wrapper1.eq(Product::getType,type.getTname());
        List<Product> list=productmapper.selectList(wrapper1);
        if(list!=null)
        {
            return Result.error("-1",type.getTname()+"类型存在商品，不能修改");
        }
        typemapper.update(type,wrapper.eq(ProductType::getTid,type.getTid()));
        return Result.success();
    }


    //删除类型
    @DeleteMapping("/deleteType")
    public Result<?> delete(@RequestParam String tname){
        LambdaQueryWrapper<Product> wrapper=Wrappers.lambdaQuery();
        LambdaQueryWrapper<ProductType> wrapper1=Wrappers.lambdaQuery();
        wrapper.eq(Product::getType,tname);
        List<Product> list=productmapper.selectList(wrapper);
        if(list!=null){
            return Result.error("-1",tname+"类型存在商品，不能删除");
        }
        wrapper1.eq(ProductType::getTname,tname);
        typemapper.delete(wrapper1);
        return Result.success();
    }


}
