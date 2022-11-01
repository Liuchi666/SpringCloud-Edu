package com.qst.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qst.commonutils.R;
import com.qst.eduservice.entity.EduCourse;
import com.qst.eduservice.entity.dto.CoursePublishDto;
import com.qst.eduservice.entity.vo.CourseInfoVo;
import com.qst.eduservice.entity.vo.CourseQuery;
import com.qst.eduservice.entity.vo.TeacherQuery;
import com.qst.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 课程(EduCourse)表控制层
 *
 * @author 记住吾名梦寒
 * @since 2022-09-09 10:22:17
 */
@Api(tags = "课程管理")
@RestController
@RequestMapping("/eduService/eduCourse")
public class EduCourseController extends ApiController {
    @Autowired
    private EduCourseService eduCourseService;

    /**
     *  查询所有课程列表
     */
    @GetMapping()
    public R getCourseList(){
        List<EduCourse> courseList = eduCourseService.list(null);
        return R.ok().data("courseList",courseList);
    }

    /**
     *  多条件查询+分页
     *  可以用@GetMapping  直接从请求头里面取数据，
     *  也可以用@PostMapping 从请求体里面取数据，但要求前端传过来的数据是json格式的且是在请求体中
     *  用required = false是因为可以不带条件查询,不带条件时就是查询所有
     */
    @ApiOperation("多条件查询+分页")
    @PostMapping("/conditions/{current}/{size}")
    public R conditions(@PathVariable Long current,
                        @PathVariable Long size,
                        @RequestBody(required = false) CourseQuery courseQuery){
        //创建分页对象
        Page<EduCourse> pageInfo = new Page<>(current, size);
        //创建LambdaQueryWrapper对象
        LambdaQueryWrapper<EduCourse> queryWrapper = new LambdaQueryWrapper<>();
        //如果有查询条件，则取出courseQuery封装的查询条件
        if(courseQuery != null){
            //取出courseQuery封装的查询条件
            String title = courseQuery.getTitle();
            String status = courseQuery.getStatus();

            //组装查询条件
            if(StringUtils.hasText(title)){
                queryWrapper.like(EduCourse::getTitle,title);
            }
            if(StringUtils.hasText(status)){
                queryWrapper.eq(EduCourse::getStatus,status);
            }
        }
        //组装排序条件(根据创建时间降序排列，这样新添加的就在前面)
        queryWrapper.orderByDesc(EduCourse::getGmtCreate);

        //查询
        eduCourseService.page(pageInfo,queryWrapper);
        //返回结果
        return R.ok().data("pageInfo",pageInfo);
    }


    /**
     *  添加课程基本信息
     */
    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        String id = eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    /**
     *  根据课程id查询课程信息
     */
    @GetMapping("/getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId){
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    /**
     *  修改课程信息
     */
    @PutMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    /**
     *  根据课程id查询最终发布课程时的回显数据(通过sql多表联查)
     */
    @GetMapping("/getPublishCourseInfo/{courseId}")
    public R getPublishCourseInfo(@PathVariable String courseId){
        CoursePublishDto coursePublishDto = eduCourseService.getPublishCourseInfo(courseId);
        return R.ok().data("coursePublishDto",coursePublishDto);
    }

    /**
     *  课程最终发布
     *  数据库课程表中有个字段status代表课程状态 Draft未发布  Normal已发布，默认Draft未发布
     *  因为我们在这之前的步骤中实际上已经将数据存进相应的表了，所以我们这里点击最终发布之后实际上是将课程发布状态更改为已发布
     */
    @PutMapping("/updatePublishStatus/{courseId}")
    public R updatePublishStatus(@PathVariable String courseId){
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(courseId);
        eduCourse.setStatus("Normal");
        eduCourseService.updateById(eduCourse);
        return R.ok();
    }

    /**
     *  根据id删除课程，连同删除课程下的章节、小节和课程描述
     */
    @DeleteMapping("/{courseId}")
    public R deleteCourse(@PathVariable String courseId){
        eduCourseService.removeCourse(courseId);
        return R.ok();
    }

}

