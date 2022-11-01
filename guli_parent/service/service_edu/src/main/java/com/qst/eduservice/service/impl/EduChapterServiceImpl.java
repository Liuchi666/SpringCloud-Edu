package com.qst.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qst.commonutils.CustomException;
import com.qst.eduservice.dao.EduChapterDao;
import com.qst.eduservice.entity.EduChapter;
import com.qst.eduservice.entity.EduVideo;
import com.qst.eduservice.entity.chapter.ChapterVo;
import com.qst.eduservice.entity.chapter.VideoVo;
import com.qst.eduservice.service.EduChapterService;
import com.qst.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程(EduChapter)表服务实现类
 *
 * @author 记住吾名梦寒
 * @since 2022-09-09 10:22:20
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterDao, EduChapter> implements EduChapterService {

    //注入EduVideoService，方便操作video表
    @Autowired
    private EduVideoService eduVideoService;

    /**
     *   课程大纲列表(树形结构):根据课程id查询课程下的所有章节和小节
     */
    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //1 根据课程id查询课程里面所有的章节
        LambdaQueryWrapper<EduChapter> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(EduChapter::getCourseId,courseId);
        List<EduChapter> chapterList = this.list(queryWrapper1);

        //2 根据课程id查询课程里面所有的小节
        LambdaQueryWrapper<EduVideo> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(EduVideo::getCourseId,courseId);
        List<EduVideo> videoList = eduVideoService.list(queryWrapper2);

        //声明一个最终返回的list集合
        List<ChapterVo> finalList = new ArrayList<>();
        //3 遍历章节list集合进行封装
        for (EduChapter eduChapter : chapterList) {
            ChapterVo chapterVo = new ChapterVo();
            //将EduChapter转为ChapterVo
            BeanUtils.copyProperties(eduChapter,chapterVo);
            //章节id
            String chapterId = eduChapter.getId();

            //声明list封装小节信息
            List<VideoVo> childrenList = new ArrayList<>();
            //4 遍历小节list集合进行封装
            for (EduVideo eduVideo : videoList) {
                if(chapterId.equals(eduVideo.getChapterId())){  //判断是否是当前章节下的小节
                    VideoVo videoVo = new VideoVo();
                    //将EduVideo转为VideoVo
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    childrenList.add(videoVo);
                }
            }
            chapterVo.setChildren(childrenList);
            //将封装了此次循环章节和章节下的小节的chapterVo对象存进finalList
            finalList.add(chapterVo);
        }
        //返回封装了该课程全部章节和小节信息的list集合
        return finalList;
    }

    /**   删除章节的方法
     *      1.判断章节下是否有小节
     *      2.有小节则删除失败，提示章节下还有小节未删除，无小节则直接删除
     *
     * @return
     */
    @Override
    public boolean removeChapter(String chapterId) {
        //查询章节下是否有小节
        LambdaQueryWrapper<EduVideo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduVideo::getChapterId,chapterId);
        //count()是用来查询符合条件的数据有多少条
        int count = eduVideoService.count(wrapper);
        if(count == 0){
            //没有小节了，直接删除
            return this.removeById(chapterId);
        }else {
            //下面还有小节，直接抛出自定义异常
            throw new CustomException("删除失败！该章节下还有小节未删除");
        }
    }

    /**   根据课程id删除章节  */
    @Override
    public void removeChapterByCourseId(String courseId) {
        LambdaQueryWrapper<EduChapter> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduChapter::getCourseId,courseId);
        boolean flag = this.remove(queryWrapper);
        if(!flag){
            throw new CustomException("删除章节失败");
        }
    }
}

