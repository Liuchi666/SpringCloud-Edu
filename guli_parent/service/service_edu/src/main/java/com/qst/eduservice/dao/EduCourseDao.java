package com.qst.eduservice.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qst.eduservice.entity.EduCourse;
import com.qst.eduservice.entity.dto.CoursePublishDto;
import com.qst.eduservice.entity.frontvo.CourseWebVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 课程(EduCourse)表数据库访问层
 *
 * @author 记住吾名梦寒
 * @since 2022-09-09 10:22:18
 */
@Mapper
public interface EduCourseDao extends BaseMapper<EduCourse> {
    //根据课程id查询课程确认信息
    CoursePublishDto getPublishCourseInfo(String courseId);
   //编写sql语句，根据课程id联表查询课程基本信息
    CourseWebVo getCourseBaseInfo(String courseId);
}

