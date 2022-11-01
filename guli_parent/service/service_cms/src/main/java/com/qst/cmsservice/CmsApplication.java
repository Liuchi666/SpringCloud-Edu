package com.qst.cmsservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/21
 * @description
 *
 *  为什么加 @ComponentScan(basePackages = {"com.qst"})
 *  因为swagger的配置类在common模块中的service_base子模块中，而不在当前模块，
 *  所以父模块service中引进了service_base模块的依赖,这样父模块的所有子模块中也会有service_base模块的依赖
 *  当本模块中要用到swagger时，当项目启动时，就会先去扫描本模块中com.qst包下，但是本模块com.qst包下没有swagger配置类，
 *  所以会再去依赖中找，然后就找到了引入的service_base模块中的com.qst包下的baseservice.config子包中有SwaggerConfig配置类
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.qst"})
@MapperScan("com.qst.cmsservice.dao")   //扫描dao包
@EnableDiscoveryClient  //开启服务发现客户端
public class CmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class,args);
    }
}
