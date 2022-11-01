package com.qst.vodservice.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.qst.commonutils.CustomException;
import com.qst.vodservice.service.VodService;
import com.qst.vodservice.utils.ConstantVodUtils;
import com.qst.vodservice.utils.InitVodClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/19
 * @description     采用文件流的方式上传文件到阿里云
 */
@Service
public class VodServiceImpl implements VodService {
    /**
     *  上传视频到阿里云
     */
    @Override
    public String updateVideoAliYun(MultipartFile file) {
        try {
            //阿里云密钥accessKeyId, accessKeySecret
            //fileName：上传文件的原始名称
            // 例如01.03.09.mp4
            String fileName = file.getOriginalFilename();
            //title：上传之后在阿里云显示的名称
            String title = fileName.substring(0, fileName.lastIndexOf("."));
            //inputStream：上传文件输入流
            InputStream inputStream = file.getInputStream();
            UploadStreamRequest request =
                    new UploadStreamRequest(ConstantVodUtils.KEY_ID, ConstantVodUtils.KEY_SECRET, title, fileName, inputStream);

            UploadVideoImpl uploader = new UploadVideoImpl();
            UploadStreamResponse response = uploader.uploadStream(request);

            String videoId = null;
            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *  删除阿里云中的视频
     */
    @Override
    public void deleteAliYunVideo(String videoId) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.KEY_ID, ConstantVodUtils.KEY_SECRET);
            //创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();
            //向request对象中设置视频id(参数是复数，即一次可以删除多个视频)
            request.setVideoIds(videoId);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new CustomException("删除视频失败");
        }
    }

    /**
     *  删除多个阿里云视频的方法
     */
    @Override
    public void removeMoreAliYunVideo(List<String> videoIdList) {
        try {
            //初始化对象
            DefaultAcsClient client = InitVodClient.initVodClient(ConstantVodUtils.KEY_ID, ConstantVodUtils.KEY_SECRET);
            //创建删除视频的request对象
            DeleteVideoRequest request = new DeleteVideoRequest();

            //把videoIdList转换成1,2,3的格式(注意StringUtils不是spring包下的，而是org.apache.commons.lang.StringUtils)
            String videoIds = StringUtils.join(videoIdList, ",");

            //向request对象中设置视频id(参数是复数，即一次可以删除多个视频如 1,2,3)
            request.setVideoIds(videoIds);
            //调用初始化对象的方法实现删除
            client.getAcsResponse(request);
        } catch (ClientException e) {
            e.printStackTrace();
            throw new CustomException("删除视频失败");
        }
    }
}
