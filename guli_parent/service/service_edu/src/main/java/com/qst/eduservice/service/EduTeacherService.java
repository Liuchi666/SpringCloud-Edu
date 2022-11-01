package com.qst.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qst.commonutils.R;
import com.qst.eduservice.entity.EduTeacher;

/**
 * 讲师(EduTeacher)表服务接口
 *
 * @author 记住吾名梦寒
 * @since 2022-09-03 11:01:04
 */
public interface EduTeacherService extends IService<EduTeacher> {

    R getTeacherInfo(String teacherId);
}

