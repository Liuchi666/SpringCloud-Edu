package com.qst.eduservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qst.eduservice.entity.EduChapter;
import com.qst.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * 课程(EduChapter)表服务接口
 *
 * @author 记住吾名梦寒
 * @since 2022-09-09 10:22:20
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    boolean removeChapter(String chapterId);

    void removeChapterByCourseId(String courseId);
}

