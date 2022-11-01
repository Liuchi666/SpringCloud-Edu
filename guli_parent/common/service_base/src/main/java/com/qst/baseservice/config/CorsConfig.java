package com.qst.baseservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/7
 * @description     后端解决跨域问题的相关配置
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    /**  在springboot中设置允许跨域   */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 设置允许跨域的路径
        registry.addMapping("/**")
                //允许的源头
                .allowedOrigins("*")
                // 设置允许的请求方式
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS")
                // 是否允许cookie
                .allowCredentials(true)
                // 跨域允许时间
                .maxAge(3600)
                // 设置允许的header属性
                .allowedHeaders("*");
    }
}
