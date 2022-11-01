package com.qst.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/27
 * @description
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.qst"})
@MapperScan("com.qst.staservice.dao")
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling  //开启定时任务的注解
public class StaApplication {
    public static void main(String[] args) {
        SpringApplication.run(StaApplication.class,args);
    }
}
