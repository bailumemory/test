package com.example.shopping1.controller;


import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.shopping1.comman.Result;
import com.example.shopping1.entity.Product;
import com.example.shopping1.entity.ShoppingCar;
import com.example.shopping1.mapper.Shoppingmapper;
import org.apache.ibatis.annotations.Delete;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/shoppingcar")
public class ShoppingcarController {

    @Resource
    Shoppingmapper shoppingmapper;

    @GetMapping("/searchcar")
    public Result<?> findShoppingcar(@RequestParam(defaultValue = "") String name){
        LambdaQueryWrapper<ShoppingCar> wrapper= Wrappers.<ShoppingCar>lambdaQuery();
        if(StrUtil.isNotBlank(name)){
            wrapper.eq(ShoppingCar::getCname,name);
        }
        List<ShoppingCar> res=shoppingmapper.selectList(wrapper);
        return Result.success(res);

    }

    @PutMapping("/addNums")
    public Result<?> addNums(@RequestBody ShoppingCar shop){
        shop.setNums(shop.getNums()+1);
        shop.setTotals(shop.getValuse()*shop.getNums());
        shoppingmapper.updateById(shop);
        return Result.success();
    }

    @PutMapping("/reduceNums")
    public Result<?> reduceNums(@RequestBody ShoppingCar shop){
        if(shop.getNums()==1){
            return Result.error("-1","商品不能再减少了");
        }
        shop.setNums(shop.getNums()-1);
        shop.setTotals(shop.getValuse()*shop.getNums());
        shoppingmapper.updateById(shop);
        return Result.success();
    }

    @DeleteMapping("/deleteCar")
    public Result<?> delete(@RequestParam Integer id,@RequestParam String username){
        LambdaQueryWrapper<ShoppingCar> wrapper=Wrappers.<ShoppingCar>lambdaQuery();
        wrapper.eq(ShoppingCar::getProductid,id).eq(ShoppingCar::getCname,username);
        shoppingmapper.delete(wrapper);
        return Result.success();
    }

    @PostMapping("/insertcar")
    public Result<?> save(@RequestBody Product product,@RequestParam String username){
        LambdaQueryWrapper<ShoppingCar> wrapper=Wrappers.lambdaQuery();
        wrapper.eq(ShoppingCar::getProductid,product.getProductId()).eq(ShoppingCar::getCname,username);
        ShoppingCar res=shoppingmapper.selectOne(wrapper);
        if(res==null){
            ShoppingCar shop = new ShoppingCar();
            shop.setCname(username);
            shop.setProductid(product.getProductId());
            shop.setPname(product.getPname());
            shop.setNums(1);
            shop.setValuse(product.getValuse());
            shop.setLimits("有效");
            shop.setTotals(product.getValuse());
            shop.setPath(product.getPath());
            shoppingmapper.insert(shop);
        }
        else{
            res.setNums(res.getNums()+1);
            res.setTotals(res.getValuse()*res.getNums());
            shoppingmapper.update(res,wrapper);
        }
        return Result.success();
    }

}
