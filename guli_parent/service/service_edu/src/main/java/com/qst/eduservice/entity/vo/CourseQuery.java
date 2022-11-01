package com.qst.eduservice.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/17
 * @description 封装课程列表条件查询信息的vo对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseQuery {
    //课程名称
    private String title;
    //课程状态
    private String status;
}
