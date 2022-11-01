package com.qst.eduservice.feign.impl;

import com.qst.commonutils.R;
import com.qst.eduservice.feign.EduVodFeign;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/20
 * @description
 */
/**
 *   创建一个实现类(放在feign包下的hystrix包下，类名以Hystrix结尾)实现feign接口，
 *   重写接口中的方法，当出现服务雪崩时就执行该方法(即此重写的方法为备选方案)
 */
@Component  //注入容器
public class EduVodFeignHystrix implements EduVodFeign {

    @Override
    public R removeAliyunVideo(String videoId) {
        return R.error().message("删除视频出错啦");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错啦");
    }
}
