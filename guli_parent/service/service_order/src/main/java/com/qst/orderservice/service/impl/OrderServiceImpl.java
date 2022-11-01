package com.qst.orderservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qst.commonutils.R;
import com.qst.commonutils.ordervo.CourseWebVoOrder;
import com.qst.commonutils.ordervo.UcenterMemberOrder;
import com.qst.orderservice.dao.OrderDao;
import com.qst.orderservice.entity.Order;
import com.qst.orderservice.feign.OrderEduFeign;
import com.qst.orderservice.feign.OrderUcenterFeign;
import com.qst.orderservice.service.OrderService;
import com.qst.orderservice.utils.OrderNoUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 订单(Order)表服务实现类
 *
 * @author 记住吾名梦寒
 * @since 2022-09-26 18:41:49
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderDao, Order> implements OrderService {

    //注入feign用于服务远程调用
    @Resource
    private OrderEduFeign orderEduFeign;
    @Resource
    private OrderUcenterFeign orderUcenterFeign;

    /**
     *  生成订单
     *  1.根据课程id返回课程信息
     *  2.根据用户id(在前端请求拦截器中设置在请求头里面了)，查询用户信息
     */
    @Override
    public String createOrders(String courseId, String memberId) {
        //1.通过远程调用根据课程id返回课程信息
        CourseWebVoOrder courseInfoOrder = orderEduFeign.getCourseInfoOrder(courseId);

        //2.根据远程调用根据用户id查询用户信息
        UcenterMemberOrder userInfoOrder = orderUcenterFeign.getUserInfoOrder(memberId);

        //通过lombok的@Builder注解创建Order对象
        Order order = new Order().builder()
                .orderNo(OrderNoUtil.getOrderNo())
                .courseId(courseInfoOrder.getId())
                .courseTitle(courseInfoOrder.getTitle())
                .courseCover(courseInfoOrder.getCover())
                .teacherName(courseInfoOrder.getTeacherName())
                .memberId(userInfoOrder.getId())
                .nickname(userInfoOrder.getNickname())
                .mobile(userInfoOrder.getMobile())
                .totalFee(courseInfoOrder.getPrice())
                .payType(1)  //支付方式 1微信 2支付宝
                .status(0)   //订单状态（0：未支付 1：已支付）
                .build();
        //插入数据
        this.save(order);
        //返回订单编号
        return order.getOrderNo();
    }
}

