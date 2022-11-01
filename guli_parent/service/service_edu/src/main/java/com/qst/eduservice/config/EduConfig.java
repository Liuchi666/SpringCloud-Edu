package com.qst.eduservice.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/3
 * @description
 */
@Configuration
@MapperScan("com.qst.eduservice.dao")  //加此注解直接扫描包下所有mapper接口，就不用每个接口上都写@Mapper，
//并为该接口创建代理对象，并将该代理对象注入容器
public class EduConfig {

    /**
     * 逻辑删除插件  注意：在mybatis-plus3.1.1版本以上就不用这一步了，
     * 但是此项目使用的3.0.5版本，所以需要单独配置插件
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    /**
     *  mybatis-plus分页插件
     *  注意：因为本项目使用的mp的旧版本(3.0.5)，所以使用以下方式配置mp分页插件，
     *  在3.4.0版本以后引入了MybatisPlusInterceptor功能更加强大，
     *  PaginationInterceptor也被PaginationInnerInterceptor取代
     */
    @Bean  //放在方法上面，把方法的返回值对象，注入到spring容器中
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }

    /**
     * SQL 执行性能分析插件
     * 开发环境使用，线上不推荐。 maxTime 指的是 sql 最大执行时长
     */
    /*@Bean
    @Profile({"dev", "test"})// 设置 dev test 环境开启
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        performanceInterceptor.setMaxTime(1000);//ms，超过此处设置的ms则sql不执行
        performanceInterceptor.setFormat(true);
        return performanceInterceptor;
    }*/


}
