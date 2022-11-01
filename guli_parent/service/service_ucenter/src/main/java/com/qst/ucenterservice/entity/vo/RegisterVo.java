package com.qst.ucenterservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/22
 * @description     vo对象，用来接收前端收集的用户注册时输入的信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterVo {

    //用户昵称
    private String nickname;
    //手机号
    private String mobile;
    //密码
    private String password;
    //验证码
    private String code;

}
