package com.qst.orderservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qst.commonutils.JwtUtils;
import com.qst.commonutils.R;
import com.qst.orderservice.entity.Order;
import com.qst.orderservice.feign.OrderEduFeign;
import com.qst.orderservice.feign.OrderUcenterFeign;
import com.qst.orderservice.service.OrderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.List;

/**
 * 订单(Order)表控制层
 *
 * @author 记住吾名梦寒
 * @since 2022-09-26 18:41:48
 */
@Api(tags = "订单管理")
@RestController
@RequestMapping("/orderService/order")
public class OrderController extends ApiController {
    @Autowired
    private OrderService orderService;

    /**
     *  生成订单
     *  1.根据课程id返回课程信息
     *  2.根据用户id(在前端请求拦截器中设置在请求头里面了)，查询用户信息
     */
    @PostMapping("/createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request){
        //创建订单，返回订单号
        String orderNo = orderService.createOrders(courseId,JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderNo",orderNo);
    }

    /**
     *  根据订单号(注意是orderNo不是orderId)查询订单信息
     */
    @GetMapping("/getOrderInfo/{orderNo}")
    public R getOrderInfo(@PathVariable String orderNo){
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getOrderNo,orderNo);
        Order orderInfo = orderService.getOne(wrapper);
        return R.ok().data("orderInfo",orderInfo);
    }

    /**
     *  根据课程id和用户id查询订单支付状态
     */
    @GetMapping("/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable String courseId,@PathVariable String memberId){
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Order::getCourseId,courseId)
        .eq(Order::getMemberId,memberId)
        .eq(Order::getStatus,1);  //订单支付状态 1已支付
        int count = orderService.count(queryWrapper);
        if(count > 0){
            return true;
        }else {
            return false;
        }
    }

}

