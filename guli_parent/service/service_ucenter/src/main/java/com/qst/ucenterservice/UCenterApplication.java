package com.qst.ucenterservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

import javax.swing.*;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/22
 * @description
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.qst.ucenterservice.dao")  //扫描dao包
@ComponentScan(basePackages = {"com.qst"})
public class UCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UCenterApplication.class,args);
    }
}
