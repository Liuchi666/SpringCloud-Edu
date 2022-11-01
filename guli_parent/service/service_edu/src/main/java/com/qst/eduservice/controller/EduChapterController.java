package com.qst.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.qst.commonutils.R;
import com.qst.eduservice.entity.EduChapter;
import com.qst.eduservice.entity.chapter.ChapterVo;
import com.qst.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * 课程(EduChapter)表控制层
 *
 * @author 记住吾名梦寒
 * @since 2022-09-09 10:22:19
 */
@Api(tags = "课程章节")
@RestController
@RequestMapping("/eduService/eduChapter")
public class EduChapterController extends ApiController {

    @Autowired
    private EduChapterService eduChapterService;

    /**
     *   课程大纲列表(树形结构)，根据课程id查询
     */
    @GetMapping("/getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId){
        List<ChapterVo> list = eduChapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo",list);
    }

    /**
     *  添加章节
     */
    @PostMapping("/addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.save(eduChapter);
        return R.ok();
    }

    /**
     *  根据章节id查询章节信息
     */
    @GetMapping("/getChapterInfo/{chapterId}")
    public R getChapterById(@PathVariable String chapterId){
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.ok().data("eduChapter",eduChapter);
    }

    /**
     *  修改章节信息
     */
    @PutMapping("/updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter){
        eduChapterService.updateById(eduChapter);
        return R.ok();
    }

    /**
     *  删除章节信息
     */
    @DeleteMapping("/{chapterId}")
    public R deleteChapter(@PathVariable String chapterId){
        boolean flag = eduChapterService.removeChapter(chapterId);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

}

