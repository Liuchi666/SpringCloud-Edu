package com.qst.msmservice.controller;

import com.qst.commonutils.R;
import com.qst.msmservice.service.MsmService;
import com.qst.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/22
 * @description
 */
@RestController
@RequestMapping("/msmService/msm")
public class MsmController {
    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     *  通过腾讯云SMS服务发送短信
     */
    @GetMapping("/send/{phone}")
    public R send(@PathVariable String phone){
        //1.先从redis获取验证码，如果取到了直接返回(这是为了防止用户上一个验证码还有效就再次发送验证码，防止重复发短信)
        String code = (String) redisTemplate.opsForValue().get(phone);
        if(StringUtils.hasText(code)){
            return R.ok();
        }

        //2.如果redis中没有验证码，则说明过期了，用户可以重新发送短信验证码了
        //生成6位验证码,传给腾讯云进行发送
        code = RandomUtil.getSixBitRandom();
        //调用service发送短信
        boolean isSend = msmService.send(code,phone);
        if(isSend){
            //如果发送成功，就把发送成功的验证码存进redis中,并设置2分钟有效期
            redisTemplate.opsForValue().set(phone,code,2, TimeUnit.MINUTES);
            return R.ok();
        }else {
            return R.error().message("短信发送失败");
        }
    }


}
