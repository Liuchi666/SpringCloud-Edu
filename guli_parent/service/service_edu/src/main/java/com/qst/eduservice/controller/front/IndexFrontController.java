package com.qst.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qst.commonutils.R;
import com.qst.eduservice.entity.EduCourse;
import com.qst.eduservice.entity.EduTeacher;
import com.qst.eduservice.service.EduCourseService;
import com.qst.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/21
 * @description
 */
@RestController
@RequestMapping("/eduService/indexFront")
public class IndexFrontController {
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     * 查询前8条热门课程，查询前4条名师信息
     */
    @Cacheable(value = "courseAndTeacherList",key = "'courseAndTeacher'")
    @GetMapping("/getIndex")
    public R getIndex() {
        //查询前8条热门数据
        LambdaQueryWrapper<EduCourse> courseQueryWrapper =
                new LambdaQueryWrapper<EduCourse>()
                        .orderByDesc(EduCourse::getViewCount)
                        .last("limit 8");
        List<EduCourse> courseList8 = eduCourseService.list(courseQueryWrapper);

        //查询前4条名师信息(这里就按照id排序演示了，具体看业务)

        LambdaQueryWrapper<EduTeacher> teacherQueryWrapper =
                new LambdaQueryWrapper<EduTeacher>()
                        .orderByDesc(EduTeacher::getId)
                        .last("limit 4");
        List<EduTeacher> teacherList4 = eduTeacherService.list(teacherQueryWrapper);
        //返回数据
        return R.ok().data("courseList8",courseList8).data("teacherList4",teacherList4);
    }


}
