package com.qst.baseservice.config.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/5
 * @description     元数据处理器
 *      MyBatis-Plus公共字段自动填充：
 *     即在插入操作或者更新操作时为指定字段赋予特定的值，
 *     好处就是可以统一对这些字段进行处理，避免了代码重复,例如此实体类中的createTime、updateTime、createUser、updateUser属性
 *     实现步骤：
 *         1.在实体类的属性上加上@TableField注解，通过此注解中的fill属性指定自动填充的策略
 *         2.按照mybatis-plus要求编写元数据对象处理器类并实现 MetaObjectHandler接口，在此类中统一为公共字段赋值
 */
@Component //创建此类对象，放入容器
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("gmtCreate",new Date(),metaObject);
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtModified",new Date(),metaObject);
    }
}
