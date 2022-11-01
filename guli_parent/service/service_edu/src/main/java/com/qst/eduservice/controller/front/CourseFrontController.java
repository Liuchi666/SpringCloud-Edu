package com.qst.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qst.commonutils.JwtUtils;
import com.qst.commonutils.R;
import com.qst.commonutils.ordervo.CourseWebVoOrder;
import com.qst.eduservice.entity.EduCourse;
import com.qst.eduservice.entity.EduTeacher;
import com.qst.eduservice.entity.chapter.ChapterVo;
import com.qst.eduservice.entity.frontvo.CourseFrontVo;
import com.qst.eduservice.entity.frontvo.CourseWebVo;
import com.qst.eduservice.feign.EduOrderFeign;
import com.qst.eduservice.service.EduChapterService;
import com.qst.eduservice.service.EduCourseService;
import com.qst.eduservice.service.EduTeacherService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.JstlUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/24
 * @description
 */
@RestController
@RequestMapping("/eduService/courseFront")
public class CourseFrontController {
    @Autowired
    private EduCourseService eduCourseService;
    @Autowired
    private EduChapterService eduChapterService;

    @Autowired
    private EduOrderFeign eduOrderFeign;

    /**
     *   多条件查询+ 分页
     */
    @ApiOperation("多条件查询+分页")
    @PostMapping("/conditions/{current}/{pageSize}")
    public R conditions(@PathVariable long current,
                        @PathVariable long pageSize,
                        @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<EduCourse> coursePage = new Page<>(current, pageSize);
        R coursePageList = eduCourseService.getCourseFrontList(coursePage,courseFrontVo);
        return coursePageList;
    }

    /**
     *  查看课程详情
     *  1.查询课程基本信息
     *  2.查询课程大纲(即课程下的所有章节和小节)
     */
    @GetMapping("/getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request){
        //1.编写sql语句，联表查询课程基本信息
        CourseWebVo courseWebVo = eduCourseService.getBaseCourseInfo(courseId);
        //2.根据课程id查询所有章节和小节
        List<ChapterVo> chapterAndVideoList = eduChapterService.getChapterVideoByCourseId(courseId);
        //3.根据课程id和用户id查询订单支付状态
        boolean isBuy = eduOrderFeign.isBuyCourse(courseId, JwtUtils.getMemberIdByJwtToken(request));
        //返回数据
        return R.ok().data("courseWebVo",courseWebVo).data("chapterAndVideoList",chapterAndVideoList).data("isBuy",isBuy);
    }

    /**
     *   根据课程id查询课程信息
     */
    @GetMapping("/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id){
        CourseWebVo courseInfo = eduCourseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }
}
