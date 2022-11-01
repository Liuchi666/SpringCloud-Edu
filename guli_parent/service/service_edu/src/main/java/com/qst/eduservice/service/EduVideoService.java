package com.qst.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qst.eduservice.entity.EduVideo;

/**
 * 课程视频(EduVideo)表服务接口
 *
 * @author 记住吾名梦寒
 * @since 2022-09-09 10:22:21
 */
public interface EduVideoService extends IService<EduVideo> {

    void removeVideoByCourseId(String courseId);
}

