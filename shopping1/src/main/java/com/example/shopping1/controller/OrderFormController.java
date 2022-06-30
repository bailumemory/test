package com.example.shopping1.controller;


import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.shopping1.comman.Result;
import com.example.shopping1.entity.OrderForm;
import com.example.shopping1.entity.Product;
import com.example.shopping1.entity.ShoppingCar;
import com.example.shopping1.entity.User;
import com.example.shopping1.mapper.OrderFormmapper;
import com.example.shopping1.mapper.Productmapper;
import com.example.shopping1.mapper.Shoppingmapper;
import com.example.shopping1.mapper.Usermapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Wrapper;
import java.util.List;

@RestController
@RequestMapping("/order")

public class OrderFormController {

    @Resource
    OrderFormmapper orderMapper;
    @Resource
    Shoppingmapper shoppingmapper;
    @Resource
    Usermapper usermapper;
    @Resource
    Productmapper productmapper;

    @GetMapping("/getOrder")
    public Result<?> getOrder(@RequestParam(defaultValue = "") String username){
        LambdaQueryWrapper<OrderForm> wrapper= Wrappers.lambdaQuery();
        if(StrUtil.isNotBlank(username))
        {
            wrapper.eq(OrderForm::getUsername,username);
        }
        List<OrderForm> res=orderMapper.selectList(wrapper);
        return Result.success(res);
    }

    @PostMapping("/insertShop")
    public Result<?> insertInformation(@RequestBody List<ShoppingCar> list,@RequestParam Integer id){
        Integer sums=new Integer(0);
        LambdaQueryWrapper<User> wrapper=Wrappers.lambdaQuery();
        LambdaQueryWrapper<ShoppingCar> wrapper1=Wrappers.lambdaQuery();

        for(int i=0;i<list.size();i++)
        {
            if(list.get(i).getLimits()=="失效")
            {
                return Result.error("-1",list.get(i).getPname()+"商品失效，不能结算");
            }
            Product pro=productmapper.selectById(list.get(i).getProductid());
            if(pro.getPnum()<list.get(i).getNums())
            {
                return Result.error("-1",list.get(i).getPname()+"商品库存为"+list.get(i).getNums().toString()+",不够，请减少数量");
            }
            sums+=list.get(i).getTotals();
        }

        User user=usermapper.selectById(id);
        if(sums>user.getMoneys())
        {
            return Result.error("-1","余额不足");
        }
        user.setMoneys(user.getMoneys()-sums);
        for(int i=0;i<list.size();i++)
        {
            String time=new DateTime().toString();
            OrderForm order=new OrderForm();
            order.setUsername(list.get(i).getCname());
            order.setPid(list.get(i).getProductid());
            order.setPname(list.get(i).getPname());
            order.setValuse(list.get(i).getValuse());
            order.setTotals(list.get(i).getTotals());
            order.setNums(list.get(i).getNums());
            order.setPath(list.get(i).getPath());
            order.setTimes(time);
            orderMapper.insert(order);
            wrapper1.eq(ShoppingCar::getCid,list.get(i).getCid());
            shoppingmapper.delete(wrapper1);
            Product pro=productmapper.selectById(list.get(i).getProductid());
            pro.setPnum(pro.getPnum()-list.get(i).getNums());
            productmapper.updateById(pro);
        }
        usermapper.updateById(user);
        return Result.success(user);

    }


}
