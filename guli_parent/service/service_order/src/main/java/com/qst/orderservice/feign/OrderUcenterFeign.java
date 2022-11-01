package com.qst.orderservice.feign;

import com.qst.commonutils.ordervo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/26
 * @description
 */
@FeignClient(value = "service-uCenter")
public interface OrderUcenterFeign {

    /**
     *  根据用户id获取用户信息(用于生成订单时远程调用)
     */
    @PostMapping("/ucenter/member/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable("id") String id);


}
