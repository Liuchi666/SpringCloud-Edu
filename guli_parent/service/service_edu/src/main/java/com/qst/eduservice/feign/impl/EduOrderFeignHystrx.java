package com.qst.eduservice.feign.impl;

import com.qst.commonutils.CustomException;
import com.qst.eduservice.feign.EduOrderFeign;
import org.springframework.stereotype.Component;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/27
 * @description
 */
/**
 *   创建一个实现类(放在feign包下的hystrix包下，类名以Hystrix结尾)实现feign接口，
 *   重写接口中的方法，当出现服务雪崩时就执行该方法(即此重写的方法为备选方案)
 */
@Component
public class EduOrderFeignHystrx implements EduOrderFeign {
    @Override
    public boolean isBuyCourse(String courseId, String memberId) {
        throw new CustomException("查询订单支付状态失败");
    }
}
