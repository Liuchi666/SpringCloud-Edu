package com.qst.orderservice.feign.impl;

import com.qst.commonutils.R;
import com.qst.commonutils.ordervo.CourseWebVoOrder;
import com.qst.orderservice.feign.OrderEduFeign;
import org.springframework.stereotype.Component;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/26
 * @description
 */
/**
 *   创建一个实现类(放在feign包下的hystrix包下，类名以Hystrix结尾)实现feign接口，
 *   重写接口中的方法，当出现服务雪崩时就执行该方法(即此重写的方法为备选方案)
 */
@Component
public class OrderEduFeignHystrix implements OrderEduFeign {
    /**
     *  根据课程id查询课程信息
     */
    @Override
    public CourseWebVoOrder getCourseInfoOrder(String id) {
        return null;
    }

}
