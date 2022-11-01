package com.qst.eduservice.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/17
 * @description     dto  数据传输对象  后端多表联查的结果用普通实体类封装不下
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoursePublishDto {
    private String id;  //课程id
    private String title;   //课程标题
    private String cover;   //课程封面图片路径
    private Integer lessonNum;  //课程总课时数
    private String subjectLevelOne;  //一级分类课程
    private String subjectLevelTwo;   //二级分类课程
    private String teacherName;  //讲师姓名
    private String price;//  课程价格  只用于显示
}
