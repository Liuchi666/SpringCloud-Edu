package com.qst.canal;

import com.qst.canal.client.CanalClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/28
 * @description     在启动类进行修改(用于canal数据同步)
 */
@SpringBootApplication
public class CanalApplication implements CommandLineRunner {  //完成数据同步需要在springboot启动类实现CommandLineRunner接口
    @Resource
    private CanalClient canalClient;

    public static void main(String[] args) {
        SpringApplication.run(CanalApplication.class,args);
    }

    /**
     *  实现CommandLineRunner接口，重写run()，这个方法的特点是：
     *      项目一启动，就会执行此方法，而且只要程序在启动状态，这个方法就一直在执行
     */
    @Override
    public void run(String... args) throws Exception {
        //执行canal客户端监听远程数据库,当监听到远程数据库中的数据发生变化时，就更新本地数据库中的数据
        canalClient.run();
    }
}
