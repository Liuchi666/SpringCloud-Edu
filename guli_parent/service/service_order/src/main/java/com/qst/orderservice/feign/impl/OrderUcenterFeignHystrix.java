package com.qst.orderservice.feign.impl;

import com.qst.commonutils.ordervo.UcenterMemberOrder;
import com.qst.orderservice.feign.OrderUcenterFeign;
import org.springframework.stereotype.Component;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/26
 * @description
 */
@Component
public class OrderUcenterFeignHystrix implements OrderUcenterFeign {
    /**
     *  根据用户id获取用户信息(用于生成订单时远程调用)
     */
    @Override
    public UcenterMemberOrder getUserInfoOrder(String id) {
        return null;
    }

}
