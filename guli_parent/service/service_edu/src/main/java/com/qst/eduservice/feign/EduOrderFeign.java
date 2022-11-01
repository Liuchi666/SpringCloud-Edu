package com.qst.eduservice.feign;

import com.qst.eduservice.feign.impl.EduOrderFeignHystrx;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/27
 * @description
 */
@FeignClient(value = "service-order",fallback = EduOrderFeignHystrx.class)
public interface EduOrderFeign {

    /**
     *  根据课程id和用户id查询订单支付状态
     */
    @GetMapping("/orderService/order/isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);
}
