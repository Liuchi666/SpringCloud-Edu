package com.qst.orderservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import com.qst.commonutils.CustomException;
import com.qst.orderservice.dao.PayLogDao;
import com.qst.orderservice.entity.Order;
import com.qst.orderservice.entity.PayLog;
import com.qst.orderservice.service.OrderService;
import com.qst.orderservice.service.PayLogService;
import com.qst.orderservice.utils.HttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 支付日志表(PayLog)表服务实现类
 *
 * @author 记住吾名梦寒
 * @since 2022-09-26 18:42:12
 *      微信支付流程：
 *          传入一个订单号，先根据订单号查询订单信息，再定义一个Map<String,String>类型来设置生成支付二维码所需的参数，
 *          再把该map转换成xml格式，发送httpClient请求微信支付提供的固定地址,再得到返回的xml格式的结果，
 *          先把返回的xml格式转换为map,然后定义一个Map<String,Object>类型，用于封装最终返回的数据(订单信息等，用于前端二维码页面的数据回显)
 *
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogDao, PayLog> implements PayLogService {

    @Autowired
    private OrderService orderService;

    /**
     *   生成微信支付二维码
     */
    @Override
    public Map createCode(String orderNo) {
        try {
            //1.根据订单号查询订单信息
            LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Order::getOrderNo,orderNo);
            Order order = orderService.getOne(queryWrapper);

            //2.使用map设置生成二维码所需的参数(这些参数名字是固定的，微信定义好的,不可改变)
            Map<String, String> map = new HashMap<>();
            map.put("appid","wx74862e0dfcf69954");  //关联的公众号appid
            map.put("mch_id", "1558950191");   //商户号
            map.put("nonce_str", WXPayUtil.generateNonceStr());  //生成随机的支付二维码
            map.put("body", order.getCourseTitle()); //订单内容(这里用课程标题了)
            map.put("out_trade_no", orderNo); //订单号
            map.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+""); //金额，因为是bigDecimal，所以需要进行转成字符串处理
            map.put("spbill_create_ip", "127.0.0.1");
            map.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");  //回调地址
            map.put("trade_type", "NATIVE");  //类型

            //3.发送httpClient请求微信支付提供的固定地址，注意传递参数是xml格式
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            //设置xml格式的参数(注意：微信支付依赖包提供的WXPayUtil中的generateSignedXml只支持泛型为Map<String,String>)
            httpClient.setXmlParam(WXPayUtil.generateSignedXml(map,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));  //商户key
            httpClient.setHttps(true);  //允许https

            //执行post请求发送
            httpClient.post();

            //4.得到发送请求返回结果
            //返回内容，是使用xml格式返回
            String xml = httpClient.getContent();

            //把xml格式转换map集合，把map集合返回(如果直接返回xml格式，前端解析不了)
            Map<String,String> resultMap = WXPayUtil.xmlToMap(xml);

            //最终返回数据 的封装(这里不用上面那个map是因为它的泛型为Map<String,String>，而这里封装的最终返回的数据适合用Map<String,Object>)
            Map<String,Object> m = new HashMap();  //这些数据是返回前端微信支付二维码页面用于数据回显
            m.put("out_trade_no", orderNo);
            m.put("course_id", order.getCourseId());
            m.put("total_fee", order.getTotalFee());
            m.put("result_code", resultMap.get("result_code"));  //返回二维码操作状态码
            m.put("code_url", resultMap.get("code_url"));        //二维码地址

            return m;
        } catch (Exception e) {
            throw new CustomException("生成支付二维码失败");
        }
    }

    /**
     *  根据订单号查询订单支付状态
     */
    @Override
    public Map<String, String> queryPayStatus(String orderNo) {
        try {
            //1、封装生成微信支付二维码的参数
            Map m = new HashMap<>();
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            //2 发送httpclient
            HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();  //发送请求

            //3 得到请求返回内容
            String xml = client.getContent();
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);  //将返回的xml格式转换成map
            //6、转成Map再返回
            return resultMap;
        }catch(Exception e) {
            throw new CustomException("查询订单状态失败");
        }
    }

    /**
     *    向支付日志表添加记录，更新订单表支付状态
     */
    @Override
    public void updateOrderStatus(Map<String, String> map) {
        //从map获取订单号
        String orderNo = map.get("out_trade_no");
        //根据订单号查询订单信息
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        Order order = orderService.getOne(wrapper);

        //更新订单表订单状态
        if(order.getStatus().intValue() == 1) { return; }
        order.setStatus(1);//1代表已经支付
        orderService.updateById(order);

        //向支付表添加支付记录
        PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo);  //订单号
        payLog.setPayTime(new Date()); //订单完成时间
        payLog.setPayType(1);//支付类型 1微信
        payLog.setTotalFee(order.getTotalFee());//总金额(分)

        payLog.setTradeState(map.get("trade_state"));//支付状态
        payLog.setTransactionId(map.get("transaction_id")); //流水号
        payLog.setAttr(JSONObject.toJSONString(map));

        this.save(payLog);
    }

}

