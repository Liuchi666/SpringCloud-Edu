package com.qst.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qst.commonutils.CustomException;
import com.qst.commonutils.R;
import com.qst.eduservice.dao.EduCourseDao;
import com.qst.eduservice.entity.EduCourse;
import com.qst.eduservice.entity.EduCourseDescription;
import com.qst.eduservice.entity.dto.CoursePublishDto;
import com.qst.eduservice.entity.frontvo.CourseFrontVo;
import com.qst.eduservice.entity.frontvo.CourseWebVo;
import com.qst.eduservice.entity.vo.CourseInfoVo;
import com.qst.eduservice.service.EduChapterService;
import com.qst.eduservice.service.EduCourseDescriptionService;
import com.qst.eduservice.service.EduCourseService;
import com.qst.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 课程(EduCourse)表服务实现类
 *
 * @author 记住吾名梦寒
 * @since 2022-09-09 10:22:18
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseDao, EduCourse> implements EduCourseService {

    //mapper层对象
    @Autowired
    private EduCourseDao eduCourseDao;

    //注入EduCourseDescriptionService用于操作课程描述表
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;
    //注入EduChapterService
    @Autowired
    private EduChapterService eduChapterService;
    //注入EduVideoService
    @Autowired
    private EduVideoService eduVideoService;

    /**
     *  添加课程基本信息
     *  1.向课程表(edu_course)中插入课程基本信息
     *  2.向课程描述表(edu_course_description)中插入课程简介
     * @return
     */
    @Override
    @Transactional  //涉及两张表的操作，要加事务
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {

        //1.向课程表(edu_course)中插入课程基本信息
        //将CourseInfoVo转换成EduCourse
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse); //将CourseInfoVo中的数据拷贝到EduCourse中(只拷贝共有的字段)
        //插入数据
        boolean flag = this.save(eduCourse);
        if(!flag){
            //插入数据失败就抛出自定义异常
           throw new CustomException("添加课程基本信息失败");
        }
        //在MP中执行完save等插入操作之后会根据雪花算法自动生成id，并将id自动回设到实体类属性中
        String courseId = eduCourse.getId();  //课程表id

        //2.向课程描述表(edu_course_description)中插入课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        //将前端收集到的课程描述封装进eduCourseDescription对象
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        //因为课程表和课程描述表是一对一的关系，所以是共享主键   另外注意：别忘了将课程描述表实体类的主键生成策略改成手动输入
        eduCourseDescription.setId(courseId);
        //插入数据
        boolean flag2 = eduCourseDescriptionService.save(eduCourseDescription);
        if(!flag2){
            throw new CustomException("插入课程简介失败");
        }
        return courseId;
    }

    /**   根据课程id查询课程信息  */
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //1.根据课程id查询课程基本信息表(edu_course表)
        EduCourse eduCourse = this.getById(courseId);
        //声明一个CourseInfoVo对象
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //2.根据课程id查询课程简介表(edu_course_description)
        EduCourseDescription eduCourseDescription = eduCourseDescriptionService.getById(courseId);
        courseInfoVo.setDescription(eduCourseDescription.getDescription());
        return courseInfoVo;
    }

    /**   修改课程信息 */
    @Override
    @Transactional  //涉及两张表的操作，要加事务
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1.修改课程基本信息表(edu_course表)
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        boolean flag = this.updateById(eduCourse);
        if(!flag){
            throw new CustomException("修改课程信息失败");
        }
        //2.修改课程描述表(edu_course_description)
        EduCourseDescription description = new EduCourseDescription();
        description.setId(courseInfoVo.getId());
        description.setDescription(courseInfoVo.getDescription());
        eduCourseDescriptionService.updateById(description);
    }

    /**
     *  根据课程id查询最终发布课程时的回显数据(通过sql多表联查)
     */
    @Override
    public CoursePublishDto getPublishCourseInfo(String courseId) {
        CoursePublishDto coursePublishDto = eduCourseDao.getPublishCourseInfo(courseId);
        return coursePublishDto;
    }

    /**
     *  根据课程id删除课程，连同删除课程下的章节、小节和课程描述
     */
    @Override
    @Transactional  //涉及多张表的操作，添加事务支持
    public void removeCourse(String courseId) {
        //1.根据课程id删除小节
        eduVideoService.removeVideoByCourseId(courseId);
        //2.根据课程id删除章节
        eduChapterService.removeChapterByCourseId(courseId);
        //3.根据课程id删除课程描述
        eduCourseDescriptionService.removeById(courseId);
        //4.最后删除课程
        boolean flag = this.removeById(courseId);
        if(!flag){
            throw new CustomException("删除课程失败");
        }
    }

    /**
     *   多条件查询+ 分页
     */
    @Override
    public R getCourseFrontList(Page<EduCourse> coursePage, CourseFrontVo courseFrontVo) {
        LambdaQueryWrapper<EduCourse> queryWrapper = null;
        //判断是否有查询条件
        if(courseFrontVo != null){
            //如果有，则组装查询条件
            queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper
                    .eq(StringUtils.hasText(courseFrontVo.getSubjectParentId()),  //一级分类
                            EduCourse::getSubjectParentId,
                            courseFrontVo.getSubjectParentId())
                    .eq(StringUtils.hasText(courseFrontVo.getSubjectId()),    //二级分类
                            EduCourse::getSubjectId,
                            courseFrontVo.getSubjectId())
                    .orderByDesc(StringUtils.hasText(courseFrontVo.getBuyCountSort()),  //按照销量降序
                            EduCourse::getBuyCount)
                    .orderByDesc(StringUtils.hasText(courseFrontVo.getGmtCreateSort()),   //按照上架时间降序
                            EduCourse::getGmtCreate)
                    .orderByDesc(StringUtils.hasText(courseFrontVo.getPriceSort()),    //按照价格降序
                            EduCourse::getPrice);
        }
        this.page(coursePage,queryWrapper);
        boolean hasNext = coursePage.hasNext();
        boolean hasPrevious = coursePage.hasPrevious();
        //创建一个map来封装完整的分页信息(因为MP返回的分页信息里面没有是否有上一页，是否有下一页)
        Map<String, Object> coursePageMap = new HashMap<>();
        coursePageMap.put("coursePage",coursePage);
        coursePageMap.put("hasPrevious",hasPrevious);
        coursePageMap.put("hasNext",hasNext);
        return R.ok().data("coursePageMap",coursePageMap);
    }

    /**
     *  编写sql语句，根据课程id联表查询课程基本信息
     */
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        return eduCourseDao.getCourseBaseInfo(courseId);
    }
}

