package com.qst.eduservice.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qst.commonutils.R;
import com.qst.eduservice.entity.EduTeacher;
import com.qst.eduservice.service.EduTeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/24
 * @description
 */
@RestController
@RequestMapping("/eduService/teacherFront")
public class TeacherFrontController {
    @Autowired
    private EduTeacherService eduTeacherService;

    /**
     *  前台分页查询讲师数据
     */
    @GetMapping("/getFrontTeacherPageList/{current}/{pageSize}")
    public R getFrontTeacherPageList(@PathVariable Long current, @PathVariable Long pageSize){
        Page<EduTeacher> pageInfo = new Page<>(current, pageSize);
        //按讲师id升序排序后再分页
        LambdaQueryWrapper<EduTeacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(EduTeacher::getId);
        eduTeacherService.page(pageInfo,queryWrapper);
        //创建一个map来封装完整的分页信息(因为MP返回的分页信息里面没有是否有上一页，是否有下一页)
        Map<String, Object> pageMap = new HashMap<>();
        pageMap.put("pageInfo",pageInfo);
        pageMap.put("hasPrevious",pageInfo.hasPrevious());
        pageMap.put("hasNext",pageInfo.hasNext());
        return R.ok().data("pageMap",pageMap);
    }

    /**
     *  讲师详情
     */
    @GetMapping("/getFrontTeacherInfo/{teacherId}")
    public R getFrontTeacherInfo(@PathVariable String teacherId){
        R teacherInfo = eduTeacherService.getTeacherInfo(teacherId);
        return teacherInfo;
    }

}
