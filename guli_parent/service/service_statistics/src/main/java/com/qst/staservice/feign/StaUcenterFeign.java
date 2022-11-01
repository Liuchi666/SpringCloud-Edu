package com.qst.staservice.feign;

import com.qst.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/27
 * @description
 */
@FeignClient(value = "service-uCenter")
public interface StaUcenterFeign {

    /**
     *  查询某一天的注册人数
     */
    @GetMapping("/ucenter/member/getRegisterCount/{day}")
    public R getRegisterCount(@PathVariable("day") String day);

}
