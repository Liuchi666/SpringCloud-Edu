package com.qst.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.qst.commonutils.R;
import com.qst.eduservice.entity.EduCourse;
import com.qst.eduservice.entity.dto.CoursePublishDto;
import com.qst.eduservice.entity.frontvo.CourseFrontVo;
import com.qst.eduservice.entity.frontvo.CourseWebVo;
import com.qst.eduservice.entity.vo.CourseInfoVo;

/**
 * 课程(EduCourse)表服务接口
 *
 * @author 记住吾名梦寒
 * @since 2022-09-09 10:22:18
 */
public interface EduCourseService extends IService<EduCourse> {

    String saveCourseInfo(CourseInfoVo courseInfoVo);

    CourseInfoVo getCourseInfo(String courseId);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishDto getPublishCourseInfo(String courseId);

    void removeCourse(String courseId);

    R getCourseFrontList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo);

    CourseWebVo getBaseCourseInfo(String courseId);
}

