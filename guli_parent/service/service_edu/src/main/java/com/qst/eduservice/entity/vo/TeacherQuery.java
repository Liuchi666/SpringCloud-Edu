package com.qst.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/3
 * @description    接收前端提交的数据
 *      VO:  View Object 视图对象
 *      有时候，往往前端传过来的有些数据我们用跟数据库表映射的实体类接收不了，
 *      这时候就需要定义一个 vo对象来接受这些数据了
 */
@Data
public class TeacherQuery {
    @ApiModelProperty(value = "教师名称，多条件查询")
    private String name;

    @ApiModelProperty("教师头衔  1高级讲师 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "讲师注册的开始时间",example = "2019-10-30 14:18:46")  //example属性译为示例，起说明作用
    private String begin;  //注意：这里可以直接用String类型接收日期，因为前端传来的就是json字符串

    @ApiModelProperty(value = "讲师注册的结束时间",example = "2019-10-30 14:18:46")
    private String end;
}
