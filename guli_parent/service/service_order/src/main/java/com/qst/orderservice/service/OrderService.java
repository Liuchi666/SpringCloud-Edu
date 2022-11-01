package com.qst.orderservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qst.orderservice.entity.Order;

/**
 * 订单(Order)表服务接口
 *
 * @author 记住吾名梦寒
 * @since 2022-09-26 18:41:49
 */
public interface OrderService extends IService<Order> {

    String createOrders(String courseId, String memberId);
}

