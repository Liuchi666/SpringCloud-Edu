package com.qst.eduservice.feign;

import com.qst.commonutils.R;
import com.qst.eduservice.feign.impl.EduVodFeignHystrix;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/20
 * @description
 */
/**
 *  @FeignClient(value = "service-vod")注解： 用于指明想要调用的服务
 *      value属性的值就写想要调用的服务的应用名称
 */
@FeignClient(value = "service-vod",fallback = EduVodFeignHystrix.class)
public interface EduVodFeign {
    /**
     *   你需要调用目标服务的哪个controller方法，就写它的方法签名
     *   方法签名: 包含方法的注解、返回值、方法名、形参列表、访问修饰符(因为这是在一个接口中，
     *                所以访问修饰符默认都是public,所以可以不用写,而且接口中方法不能有方法体
     *   注意：只要是feign的调用，controller中方法的形参必须加相应的注解，这是要求(以前我们有些情况可以不用加，feign中比较严格)
     */

    //调用service-vod服务中的删除云端视频功能
    @DeleteMapping("/eduVod/video/removeAliYunVideo/{videoId}")
    public R removeAliyunVideo(@PathVariable String videoId);

    //删除多个阿里云视频的方法
    @DeleteMapping("/eduVod/video/deleteBatch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);


}
