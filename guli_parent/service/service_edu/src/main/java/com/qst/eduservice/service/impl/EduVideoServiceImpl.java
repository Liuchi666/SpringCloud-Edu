package com.qst.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qst.commonutils.CustomException;
import com.qst.eduservice.dao.EduVideoDao;
import com.qst.eduservice.entity.EduChapter;
import com.qst.eduservice.entity.EduVideo;
import com.qst.eduservice.feign.EduVodFeign;
import com.qst.eduservice.service.EduVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程视频(EduVideo)表服务实现类
 *
 * @author 记住吾名梦寒
 * @since 2022-09-09 10:22:21
 */
@Service
public class EduVideoServiceImpl extends ServiceImpl<EduVideoDao, EduVideo> implements EduVideoService {
    @Autowired
    private EduVodFeign eduVodFeign;

    /**   根据课程id删除小节 对应的视频也应该删除 */
    @Override
    public void removeVideoByCourseId(String courseId) {
        //删除所有小节之前先删除所有小节对应的的视频
        //组装查询条件
        LambdaQueryWrapper<EduVideo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(EduVideo::getCourseId,courseId);
        wrapper.select(EduVideo::getVideoSourceId);
        List<EduVideo> eduVideos = this.list(wrapper);

        //将List<EduVideo>转换成List<String>:
        //遍历list获取所有小节对应的云端视频的ids集合
        List<String> videoIds = new ArrayList<>();
        for (EduVideo eduVideo : eduVideos) {
            String videoSourceId = eduVideo.getVideoSourceId();
            if(StringUtils.hasText(videoSourceId)){
                //如果有云端视频id
                videoIds.add(videoSourceId);
            }
        }
        //list集合中有数据才删除
        if(videoIds.size() > 0){
            //调用service-vod服务中的批量删除云端视频的方法
            eduVodFeign.deleteBatch(videoIds);
        }
        //组装查询条件，根据课程id删除小节
        LambdaQueryWrapper<EduVideo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduVideo::getCourseId,courseId);
        boolean flag = this.remove(queryWrapper);
        if(!flag){
            throw new CustomException("删除小节失败");
        }
    }
}

