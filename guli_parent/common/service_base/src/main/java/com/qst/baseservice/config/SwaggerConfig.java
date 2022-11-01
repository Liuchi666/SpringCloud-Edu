package com.qst.baseservice.config;

import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/3
 * @description     swagger配置类
 *     前后端分离开发模式中，api文档是最好的沟通方式。
 *     Swagger 是一个规范和完整的框架，用于生成、描述、调用和可视化 RESTful 风格的 Web 服务。
 *        特点：
 *          及时性 (接口变更后，能够及时准确地通知相关前后端开发人员)
 *          规范性 (并且保证接口的规范性，如接口的地址，请求方式，参数及响应格式和错误信息)
 *          一致性 (接口信息一致，不会出现因开发人员拿到的文档版本不一致，而出现分歧)
 *          可测性 (直接在接口文档上进行测试，以方便理解业务)
 *
 *    Swagger的使用： 在想要使用的项目中，启动项目，再访问路径 http://localhost:项目端口/swagger-ui.html
 *                     就能看到本项目中的所有接口信息了
 *    搭配Swagger的注解使用，让生成的接口文档信息更加直观，以下注解的参数值代表想要描述的信息
 *      @Api注解：加在类上，就可以描述该Controller(加的类通常是controller)的作用了
 *      @ApiOperation注解：加在方法上，对该方法进行描述
 *      @ApiParam注解：加在方法参数上，对该参数进行描述
 *      @ApiModelProperty注解：用在类的属性上，对该属性进行说明
 *      具体使用案例在service_edu模块的com.qst.eduservice.controller.EduTeacherController有
 *      以上只是常用的注解,这些注解不影响功能，只是为了让接口文档描述更加直观
 */
@Configuration
@EnableSwagger2  //开启swagger功能
public class SwaggerConfig {

    @Bean
    public Docket webApiConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")  //名字随意
                .apiInfo(webApiInfo())
                .select()
//                .paths(Predicates.not(PathSelectors.regex("/admin/.*")))  //在文档中不显示接口路径中包含admin的
                .paths(Predicates.not(PathSelectors.regex("/error.*")))  //在文档中不显示接口路径中包含error的
                .build();
    }
    private ApiInfo webApiInfo() {
        return new ApiInfoBuilder()
                .title("swagger测试啦啦啦")   //文档标题
                .description("本文档用于测试swagger的使用啦啦啦")  //文档描述
                .version("1.0")  //文档版本
                .contact(new Contact("zhangsan", "http://bjpowernode.com", "55317332@qq.com"))
                .build();
    }

}
