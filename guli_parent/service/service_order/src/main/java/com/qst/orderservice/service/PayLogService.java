package com.qst.orderservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qst.orderservice.entity.PayLog;

import java.util.Map;

/**
 * 支付日志表(PayLog)表服务接口
 *
 * @author 记住吾名梦寒
 * @since 2022-09-26 18:42:12
 */
public interface PayLogService extends IService<PayLog> {

    Map createCode(String orderNo);

    Map<String, String> queryPayStatus(String orderNo);

    void updateOrderStatus(Map<String, String> map);
}

