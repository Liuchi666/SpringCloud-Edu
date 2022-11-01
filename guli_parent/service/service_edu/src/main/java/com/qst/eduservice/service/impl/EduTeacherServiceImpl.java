package com.qst.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qst.commonutils.R;
import com.qst.eduservice.dao.EduTeacherDao;
import com.qst.eduservice.entity.EduCourse;
import com.qst.eduservice.entity.EduTeacher;
import com.qst.eduservice.service.EduCourseService;
import com.qst.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 讲师(EduTeacher)表服务实现类
 *
 * @author 记住吾名梦寒
 * @since 2022-09-03 11:01:04
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherDao, EduTeacher> implements EduTeacherService {
    @Autowired
    private EduCourseService eduCourseService;

    /**
     *  讲师详情
     *      1.根据讲师id查询讲师基本信息
     *      2.根据讲师id查询讲师所讲的课程
     * @return
     */
    @Override
    public R getTeacherInfo(String teacherId) {
        //根据讲师id查询讲师基本信息
        EduTeacher eduTeacher = this.getById(teacherId);

        //根据讲师id查询讲师所讲的课程
        LambdaQueryWrapper<EduCourse> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(EduCourse::getTeacherId,teacherId)
                .orderByDesc(EduCourse::getGmtCreate);
        List<EduCourse> courseList = eduCourseService.list(queryWrapper);

        return R.ok().data("eduTeacher",eduTeacher).data("courseList",courseList);
    }
}

