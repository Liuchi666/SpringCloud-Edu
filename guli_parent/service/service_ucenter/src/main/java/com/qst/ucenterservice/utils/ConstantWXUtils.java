package com.qst.ucenterservice.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/23  学习本类的设计思想(仔细琢磨)
 * @description     定义一个常量类，方便获取配置文件中的属性值
 *           用@Value("${XXX}")获取配置文件内容固然方便，但是如果配置文件改了，所有的@Value都得改，
 *           所以可以直接定义一个常量类，将来配置文件改了就只要修改该类注解中的属性值
 *          本类实现 InitializingBean接口的作用：
 *           在项目启动时，spring完成加载之后，就会执行该接口中的afterPropertiesSet方法，
 *           而我们可以通过重写该方法，就能实现本类中的属性在spring加载后再赋值
 */
@Component
public class ConstantWXUtils implements InitializingBean {
    //将配置文件中的属性注入
    @Value("${wx.open.app_id}")
    private String appId;
    @Value("${wx.open.app_secret}")
    private String appSecret;
    @Value("${wx.open.redirect_url}")
    private String redirectUrl;

    //定义公开静态属性
    public static String APP_ID;  //微信开放平台appid
    public static String APP_SECRET;  //微信开放平台app密钥
    public static String REDIRECT_URL;  //微信开放平台重定向url(域名)

    /**
     *  该方法会在spring加载完之后再执行,可以用来给本类中的静态属性赋值，
     *    注意：不直接给静态属性赋值是因为静态属性初始化时endpoint等属性值还没有被spring注入
     *    不直接给endpoint等属性加static是因为spring不能给静态变量注入值
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        APP_ID = appId;
        APP_SECRET = appSecret;
        REDIRECT_URL = redirectUrl;
    }
}
