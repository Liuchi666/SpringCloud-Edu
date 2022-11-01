package com.qst.vodservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/19
 * @description
 */
/***    注意一个问题：
 *  因为我们这个项目是专门来做 Vod 服务的，因此application.XXX配置文件中没有配置数据源，
 *  而SpringBoot默认在启动时会读取配置文件中的数据源信息，而我们没有进行配置，所以会报错(如下)，
 *  解决办法:
 *    在启动类上面的@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
 *    加一个  exclude 属性，表示在启动时不加载数据源配置
 *
 *  报错信息：
 *  Description:
 *  Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.
 *  Reason: Failed to determine a suitable driver class
 */
/**   还有一个
 * 为什么加 @ComponentScan(basePackages = {"com.qst"})
 *     因为swagger的配置类在common模块中的service_base子模块中，而不在当前模块，
 *     所以父模块service中引进了service_base模块的依赖,这样父模块的所有子模块中也会有service_base模块的依赖
 *
 *     当本模块中要用到swagger时，当项目启动时，就会先去扫描本模块中com.qst包下，但是本模块com.qst包下没有swagger配置类，
 *     所以会再去依赖中找，然后就找到了引入的service_base模块中的com.qst包下的baseservice.config子包中有SwaggerConfig配置类
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)  //启动时不加载数据源配置
@ComponentScan(basePackages = {"com.qst"})
@EnableDiscoveryClient  //开启服务发现客户端(用于nacos服务发现)
@EnableFeignClients   //开启openFeign客户端(用于服务调用)
public class VodApplication {
    public static void main(String[] args) {
        SpringApplication.run(VodApplication.class,args);
    }
}
