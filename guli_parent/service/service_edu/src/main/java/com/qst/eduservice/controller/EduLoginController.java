package com.qst.eduservice.controller;

import com.qst.commonutils.R;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/6
 * @description
 */
@Api(tags = "用户相关接口")
@RestController
@RequestMapping("/eduService/user")
//@CrossOrigin  //解决跨域问题
public class EduLoginController {

    /**
     *  用户登录
     */
    @PostMapping("/login")
    public R login(){
        return R.ok().data("token","admin");
    }

    /**
     *  用户信息
     */
    @GetMapping("/info")
    public R info(){
        return R.ok().data("name","zhangsan").data("avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }

    /**
     *  退出登录
     */
    @GetMapping("/logout")
    public R logout(){
        return R.ok();
    }

}
