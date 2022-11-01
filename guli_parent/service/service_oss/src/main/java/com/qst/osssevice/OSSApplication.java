package com.qst.osssevice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/7
 * @description
 */
/***    注意一个问题：
 *  因为我们这个项目是专门来做OSS服务的，因此application.XXX配置文件中没有配置数据源，
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
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan(basePackages = {"com.qst"})
@EnableDiscoveryClient  //开启服务发现客户端(用于nacos服务发现)
@EnableFeignClients   //开启openFeign客户端(用于服务调用)
public class OSSApplication {
    public static void main(String[] args) {
        SpringApplication.run(OSSApplication.class,args);
    }
}
