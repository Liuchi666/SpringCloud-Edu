package com.qst.eduservice.controller;


import com.qst.commonutils.R;
import com.qst.eduservice.entity.subject.OneSubject;
import com.qst.eduservice.service.EduSubjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 课程科目(EduSubject)表控制层
 *
 * @author 记住吾名梦寒
 * @since 2022-09-08 12:00:47
 */
@Api(tags = "课程分类相关接口")
@RestController
@RequestMapping("/eduService/subject")
public class EduSubjectController{
    @Autowired
    private EduSubjectService eduSubjectService;

    /**
     *  添加课程分类
     *   获取上传过来的文件，把文件内容读取出来
     */
    @ApiOperation("导入课程分类excel文件")
    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file){
        eduSubjectService.saveSubject(file);
        return R.ok();
    }

    /**
     *  课程分类列表(树形)
     */
    @GetMapping("/getAllSubject")
    public R getAllSubject(){
        List<OneSubject> list =  eduSubjectService.getOneTwoSubject();
        return R.ok().data("list",list);
    }

}

