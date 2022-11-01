package com.qst.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qst.eduservice.entity.EduSubject;
import com.qst.eduservice.entity.subject.OneSubject;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 课程科目(EduSubject)表服务接口
 *
 * @author 记住吾名梦寒
 * @since 2022-09-08 12:00:47
 */
public interface EduSubjectService extends IService<EduSubject> {

    //添加课程
    void saveSubject(MultipartFile file);

    //课程分类列表(树形)
    List<OneSubject> getOneTwoSubject();
}

