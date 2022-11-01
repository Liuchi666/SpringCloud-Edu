package com.qst.ucenterservice.controller;

import com.google.gson.Gson;
import com.qst.commonutils.CustomException;
import com.qst.commonutils.JwtUtils;
import com.qst.commonutils.R;
import com.qst.ucenterservice.entity.UcenterMember;
import com.qst.ucenterservice.service.UcenterMemberService;
import com.qst.ucenterservice.utils.ConstantWXUtils;
import com.qst.ucenterservice.utils.HttpClientUtils;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLEncoder;
import java.util.HashMap;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/23
 * @description     微信扫码登录(相当于注册+登录)
 *      调用本Controller中的login接口，重定向到拼接相关参数后的微信开放平台的url，前端返回一个微信开放平台的二维码；
 *      当用户扫描二维码后，就会重定向到application配置文件中的wx.open.redirect_url的地址，并携带code和state参数(微信帮我们拼接的);
 *      我们编写wx.open.redirect_url对应的接口，并获取接口中的code(临时票据)参数,请求微信提供的固定地址，获取到access_token(访问凭证)和openid(每个微信号唯一标识)
 *      再携带access_token和openid请求微信提供的固定地址，最终可以获取到扫码人的微信信息(微信头像，微信名等)
 */
@Controller     //只是请求地址，不需要返回数据，所以不用@RestController
@RequestMapping("/api/ucenter/wx")  //因为域名映射的地址是这个，所以我们只能用这个
public class WXApiController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    /**
     * 生成微信登录二维码
     */
    @GetMapping("/login")
    public String getWXCode() {
        //微信开放平台提供的固定地址，后面拼接参数,这里需要拼接的字符串比较多，我们没用传统的写法，而是使用占位符%s写法
            /*  传统字符串拼接地址:
                String url = "https://open.weixin.qq.com/connect/qrconnect?appid="
                    + ConstantWXUtils.APP_ID+"&response_type=code" + ...;*/
        //占位符%s写法，相当于?占位符
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        //微信开放平台推荐对redirect_url进行URLEncoder编码
        String redirectUrl = ConstantWXUtils.REDIRECT_URL;  //取到redirect_url
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //设置%s里面值
        String url = String.format(baseUrl,
                ConstantWXUtils.APP_ID,
                redirectUrl,
                "qst_GuLi");
        //重定向到请求微信地址里面
        return "redirect:" + url;
    }

    /**
     *   扫描微信二维码之后,会重定向到微信开放平台的wx.open.redirect_url:
     *      http://localhost:8160/api/ucenter/wx/callback?code=031jQB100ybhAO1QoV300nPyyj3jQB1m&state=qst_GuLi
     *      所以我们要写一个接口(名为callback)接收路径中的参数code和state
     */
    @GetMapping("/callback")
    public String callback(String code,String state){
        try {
            //1.获取code值(临时票据,类似于手机验证码)  直接在第2步中用了
            //2.拿着code请求微信固定的地址(需要拼接参数)，得到两个值access_token和openid
            String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                    "?appid=%s" +
                    "&secret=%s" +
                    "&code=%s" +
                    "&grant_type=authorization_code";
            //拼接好参数的url
            String accessTokenUrl = String.format(
                    baseAccessTokenUrl,
                    ConstantWXUtils.APP_ID,
                    ConstantWXUtils.APP_SECRET,
                    code
            );
            //使用httpclient发送请求，得到返回结果是json格式字符串，里面包含access_token和openid
            String accessTokenInfo = HttpClientUtils.get(accessTokenUrl);
            //使用gson将accessTokenInfo字符串转换为map,再从map中根据key取数据
            Gson gson = new Gson();
            HashMap accessTokenMap = gson.fromJson(accessTokenInfo, HashMap.class);
            String access_token = (String) accessTokenMap.get("access_token");
            String openid = (String) accessTokenMap.get("openid");

            //将扫描人的微信信息添加到数据库中(微信扫码就是注册+登录)
            //先进行判断数据库是否已经注册过了(根据openid，因为不同的微信号openid是唯一的)
            UcenterMember member = ucenterMemberService.getById(openid);
            String nickname = "";
            if(member == null){
                //还没有注册过，先进行注册
                //3.携带access_token和openid请求微信提供的固定地址最终可以获取到扫码人的微信信息(微信头像，微信名等)
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                //拼接参数
                String userInfoUrl = String.format(
                        baseUserInfoUrl,
                        access_token,
                        openid
                );
                //发送请求，获取扫码人的微信信息
                String userWXInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println(userWXInfo);  //{"openid":"o3_SC5-s-fyZCoBfMS1WYYewLf64","nickname":"可能出错的地方，就一定会出错！",
                // "sex":0,"language":"","city":"","province":"","country":"","headimgurl":"https:\/\/thirdw.......
                //将含有微信信息的json格式字符串转为map
                HashMap userWXInfoMap = gson.fromJson(userWXInfo, HashMap.class);
                nickname = (String) userWXInfoMap.get("nickname");  //获取微信名
                String headimgurl = (String) userWXInfoMap.get("headimgurl");  //获取微信头像
                member = UcenterMember.builder().openid(openid).nickname(nickname).avatar(headimgurl).build();
                //向数据库中插入数据
                ucenterMemberService.save(member);
            }
            //使用jwt根据member对象生成token字符串,通过路径url传递token字符串给前端，用于登录人信息回显
            String jwtToken = JwtUtils.getJwtToken(member.getId(), nickname);
            //跳转到首页,通过路径传递token字符串
            return "redirect:http://localhost:3000?token=" + jwtToken;
        } catch (Exception e) {
            e.printStackTrace();
            throw new CustomException("登录失败");
        }
    }

}
