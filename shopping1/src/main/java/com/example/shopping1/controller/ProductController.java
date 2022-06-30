package com.example.shopping1.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.shopping1.comman.Result;
import com.example.shopping1.entity.Product;
import com.example.shopping1.entity.ShoppingCar;
import com.example.shopping1.mapper.Productmapper;
import com.example.shopping1.mapper.Shoppingmapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Resource
    Productmapper productmapper;
    @Resource
    Shoppingmapper shoppingmapper;

    @GetMapping("/searchProducts")
    public Result<?> findProduct(@RequestParam(defaultValue = "") String search,
                                 @RequestParam(defaultValue = "") String type){
        LambdaQueryWrapper<Product> wrapper=Wrappers.<Product>lambdaQuery();
        if(StrUtil.isNotBlank(search))
        {
            wrapper.like(Product::getPname,search);
        }
        if(StrUtil.isNotBlank(type))
        {
            if(!type.equals("all"))
            {
                wrapper.eq(Product::getType,type);
            }
        }
        List<Product> res=productmapper.selectList(wrapper);
        return Result.success(res);
    }

    @GetMapping("/searchProducts2")
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search
                              ){
        LambdaQueryWrapper<Product> wrapper=Wrappers.<Product>lambdaQuery();
        if(StrUtil.isNotBlank(search)){
            wrapper.like(Product::getPname,search);
        }
        Page<Product> productPage=productmapper.selectPage(new Page<>(pageNum,pageSize),wrapper);
        return Result.success(productPage);
    }

    @DeleteMapping("/deleteProduct")
    public Result<?> delete(@RequestParam Integer productId){
        productmapper.deleteById(productId);
        updatecar(productId);
        return Result.success();
    }

    @PutMapping("/updateProduct")
    public Result<?> update(@RequestBody Product product){
        LambdaQueryWrapper<Product> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(Product::getProductId,product.getProductId());
        Product res=productmapper.selectById(product.getProductId());
        if(!res.getPname().equals(product.getPname())
                ||!res.getValuse().equals(product.getValuse())
                ||!res.getPath().equals(product.getPath())
                ||!res.getInformation().equals(product.getInformation())
                ||!res.getType().equals(product.getType()))
        {
            updatecar(product.getProductId());
        }
        productmapper.update(product,wrapper);
        return Result.success();
    }

    public void updatecar(Integer proid){
        LambdaQueryWrapper<ShoppingCar> wrapper1=Wrappers.lambdaQuery();
        wrapper1.eq(ShoppingCar::getProductid,proid);
        List<ShoppingCar> list1=shoppingmapper.selectList(wrapper1);
        for(int i=0;i<list1.size();i++)
        {
            list1.get(i).setLimits("失效");
            shoppingmapper.updateById(list1.get(i));
        }

    }


    @PutMapping("/addProduct")
    public Result<?> add(@RequestBody Product product){
        LambdaQueryWrapper<Product> wrapper=Wrappers.lambdaQuery();
        List<Product> res=productmapper.selectList(wrapper.eq(Product::getPname,product.getPname()));
        if(res!=null){
            return Result.error("-1","该商品已经存在");
        }
        productmapper.update(product,wrapper.eq(Product::getProductId,product.getProductId()));
        return Result.success();
    }

}