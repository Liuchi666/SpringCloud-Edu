package com.qst.orderservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qst.commonutils.R;
import com.qst.orderservice.entity.PayLog;
import com.qst.orderservice.service.PayLogService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 支付日志表(PayLog)表控制层
 *
 * @author 记住吾名梦寒
 * @since 2022-09-26 18:42:11
 */
@Api(tags = "支付日志")
@RestController
@RequestMapping("/orderService/payLog")
public class PayLogController extends ApiController {
    @Autowired
    private PayLogService payLogService;

    /**
     * 生成微信支付二维码
     * 参数是订单号
     */
    @GetMapping("/createCode/{orderNo}")
    public R createCode(@PathVariable String orderNo) {
        //返回信息，包含二维码地址，还有其他信息，所以用map接收
        Map<String, Object> map = payLogService.createCode(orderNo);
        return R.ok().data(map);
    }

    /**
     * 根据订单号查询订单支付状态
     */
    @GetMapping("/queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable String orderNo) {
        //调用service方法，查询订单信息
        Map<String, String> map = payLogService.queryPayStatus(orderNo);
        System.out.println("***********查询订单状态*************" + map);
        if (map == null) {
            return R.error().message("支付出错了...");
        }
        //如果返回的map中的数据不为空，则通过map来获取订单状态
        if (map.get("trade_state").equals("SUCCESS")) {
            //添加记录到支付日志表，更新订单表订单状态
            payLogService.updateOrderStatus(map);
            System.out.println("***********支付成功了*************");
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中...");
    }


}

