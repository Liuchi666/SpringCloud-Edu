package com.qst.eduservice.controller;


import com.baomidou.mybatisplus.extension.api.ApiController;
import com.qst.commonutils.CustomException;
import com.qst.commonutils.R;
import com.qst.eduservice.entity.EduVideo;
import com.qst.eduservice.feign.EduVodFeign;
import com.qst.eduservice.service.EduVideoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 课程视频(EduVideo)表控制层
 *
 * @author 记住吾名梦寒
 * @since 2022-09-09 10:22:20
 */
@Api(tags = "课程小节")
@RestController
@RequestMapping("/eduService/eduVideo")
public class EduVideoController extends ApiController {
    @Autowired
    private EduVideoService eduVideoService;
    //注入feign接口，用于服务调用
    @Autowired
    private EduVodFeign eduVodFeign;

    /**
     * 添加小节
     */
    @PostMapping("/addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.save(eduVideo);
        return R.ok();
    }

    /**
     * 修改小节
     */
    @PutMapping("/updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo) {
        eduVideoService.updateById(eduVideo);
        return R.ok();
    }

    /**
     * 根据id查询小节信息
     */
    @GetMapping("/{videoId}")
    public R getVideoById(@PathVariable String videoId) {
        EduVideo eduVideo = eduVideoService.getById(videoId);
        return R.ok().data("eduVideo", eduVideo);
    }

    /**
     * 根据id删除小节信息
     * 先查该小节中是否有云端视频，如果有，则先删除云端视频，没有则直接删除小节信息
     */
    @DeleteMapping("/{videoId}")
    public R deleteVideoById(@PathVariable String videoId) {
        //根据小节id查询该小节是否有云端视频
        EduVideo eduVideo = eduVideoService.getById(videoId);
        String videoSourceId = eduVideo.getVideoSourceId();  //获取云端视频id
        //判断该小节是否有云端视频id
        if (StringUtils.hasText(videoSourceId)) {
            //有云端视频，则先通过openfeign远程调用service-vod服务的根据云端视频id删除视频功能,再删除小节信息
            R result = eduVodFeign.removeAliyunVideo(videoSourceId);
            if(result.getCode() == 20001){
                throw new CustomException("删除视频失败，熔断器...");
            }
        }
        //没有云端视频，直接删除小节信息
        eduVideoService.removeById(videoId);
        return R.ok();
    }

}

