package com.qst.ucenterservice.controller;

import com.qst.commonutils.JwtUtils;
import com.qst.commonutils.R;
import com.qst.commonutils.ordervo.UcenterMemberOrder;
import com.qst.ucenterservice.entity.UcenterMember;
import com.qst.ucenterservice.entity.vo.RegisterVo;
import com.qst.ucenterservice.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/22
 * @description uCenterService
 */
@RestController
@RequestMapping("/ucenter/member")
public class UCenterMemberController {
    @Autowired
    private UcenterMemberService ucenterMemberService;

    /**
     *  用户登录
     *  登录成功就返回一个token
     */
    @PostMapping("/login")
    public R userLogin(@RequestBody UcenterMember ucenterMember){
        //调用service方法实现登录，返回一个token(使用jwt生成)
        String token = ucenterMemberService.login(ucenterMember);
        return R.ok().data("token",token);
    }

    /**
     *  用户注册
     */
    @PostMapping("/register")
    public R userRegister(@RequestBody RegisterVo registerVo){
        ucenterMemberService.register(registerVo);
        return R.ok();
    }

    /**
     *  根据token获取用户信息
     */
    @GetMapping("/getMemberInfo")
    public R getMemberInfo(HttpServletRequest request){
        //调用jwt工具类中的方法，根据request对象获取头信息，解析token,返回用户id
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        //根据用户id查询用户信息
        UcenterMember member = ucenterMemberService.getById(memberId);
        return R.ok().data("member",member);
    }

    /**
     *   根据用户id获取用户信息(用于生成订单时远程调用)
     *   注意: 上面那个是要传request对象的，而在feign远程调用时是不能传request对象的，所以要单独写一个
     */
    @PostMapping("/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable String id){
        UcenterMember member = ucenterMemberService.getById(id);
        UcenterMemberOrder ucenterMemberOrder = new UcenterMemberOrder();
        //把UcenterMember对象中的属性赋给EduMemberOrder对象
        BeanUtils.copyProperties(member, ucenterMemberOrder);
        return ucenterMemberOrder;
    }

    /**
     *  查询某一天的注册人数
     */
    @GetMapping("/getRegisterCount/{day}")
    public R getRegisterCount(@PathVariable String day){
        Integer count = ucenterMemberService.registerCount(day);
        return R.ok().data("registerCount",count);
    }

}
