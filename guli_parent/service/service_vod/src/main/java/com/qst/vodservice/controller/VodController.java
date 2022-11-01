package com.qst.vodservice.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.qst.commonutils.CustomException;
import com.qst.commonutils.R;
import com.qst.vodservice.service.VodService;
import com.qst.vodservice.utils.ConstantVodUtils;
import com.qst.vodservice.utils.InitVodClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/19
 * @description
 */
@RestController
@RequestMapping("/eduVod/video")
public class VodController {
    @Autowired
    private VodService vodService;

    /**
     *   上传视频到阿里云
     */
    @PostMapping("/updateAliYunVod")
    public R updateAliyunVod(MultipartFile file){
        //返回视频上传后在阿里云的视频id
        String videoId = vodService.updateVideoAliYun(file);
        return R.ok().data("videoId",videoId);
    }

    /**
     *  删除阿里云中的视频
     */
    @DeleteMapping("/removeAliYunVideo/{videoId}")
    public R removeAliyunVideo(@PathVariable String videoId){
        //调用方法
        vodService.deleteAliYunVideo(videoId);
        return R.ok();
    }

    /**
     *  删除多个阿里云视频的方法
     */
    @DeleteMapping("/deleteBatch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList){
        vodService.removeMoreAliYunVideo(videoIdList);
        return R.ok();
    }

    /**
     *    根据阿里云视频id获取视频凭证
     */
    @GetMapping("/getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id){
        try {
            //创建初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.KEY_ID, ConstantVodUtils.KEY_SECRET);

            //创建获取视频凭证的request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

            //向request对象中设置存在阿里云中的视频的id
            request.setVideoId(id);

            //调用初始化对象的方法获取视频凭证
            response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth",playAuth);
        } catch (Exception e) {
            throw new CustomException("获取凭证失败");
        }
    }

}
