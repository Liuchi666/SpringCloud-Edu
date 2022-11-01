package com.qst.vodtest;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadVideoRequest;
import com.aliyun.vod.upload.resp.UploadVideoResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoRequest;
import com.aliyuncs.vod.model.v20170321.GetPlayInfoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.sun.org.apache.xml.internal.security.Init;

import java.util.List;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/19
 * @description     测试阿里云本地文件上传
 */
public class TestVod {
    public static void main(String[] args) throws Exception {
        //阿里云密钥
        String accessKeyId = "LTAI5tGzZc6GtGi4N3FfSFik";
        String accessKeySecret = "YCP3YoQX685vAdQneeSYVG9vbUd19r";

        String title = "6 - What If I Want to Move Faster---test";   //上传之后文件名称
        String fileName = "F:/6 - What If I Want to Move Faster.mp4";  //本地文件路径和名称
        //上传视频的方法
        UploadVideoRequest request = new UploadVideoRequest(accessKeyId, accessKeySecret, title, fileName);
        /* 可指定分片上传时每个分片的大小，默认为2M字节 */
        request.setPartSize(2 * 1024 * 1024L);
        /* 可指定分片上传时的并发线程数，默认为1，(注：该配置会占用服务器CPU资源，需根据服务器情况指定）*/
        request.setTaskNum(1);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadVideoResponse response = uploader.uploadVideo(request);

        if (response.isSuccess()) {
            System.out.print("VideoId=" + response.getVideoId() + "\n");
        } else {
            /* 如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因 */
            System.out.print("VideoId=" + response.getVideoId() + "\n");
            System.out.print("ErrorCode=" + response.getCode() + "\n");
            System.out.print("ErrorMessage=" + response.getMessage() + "\n");
        }
    }

    /**
     *     根据存在阿里云的视频的id，获取视频播放地址
     */
    public static void getPlayUrlById() throws Exception{
        //阿里云密钥
        String accessKeyId = "LTAI5tGzZc6GtGi4N3FfSFik";
        String accessKeySecret = "YCP3YoQX685vAdQneeSYVG9vbUd19r";

        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient(accessKeyId, accessKeySecret);

        //创建获取视频地址的request和response对象
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        GetPlayInfoResponse response = new GetPlayInfoResponse();

        //向request对象中设置存在阿里云中的视频的id
        request.setVideoId("abc1889a154842d9b0e0e6d23d458123");

        //调用初始化对象里的方法，传递request,获取数据(数据封装在response中)
        response = client.getAcsResponse(request);
        List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
        //播放地址
        for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
            System.out.println("PlayInfo.PlayURL + " + playInfo.getPlayURL());
        }
        //Base信息
        System.out.println("VideoBase.Title = " + response.getVideoBase().getTitle());
    }

    /**
     *  根据存在阿里云的视频的id，获取视频播放凭证
     */
    public static void getPlayAuthById() throws ClientException {
        //创建初始化对象
        DefaultAcsClient client = InitObject.initVodClient("LTAI5tGzZc6GtGi4N3FfSFik", "YCP3YoQX685vAdQneeSYVG9vbUd19r");

        //创建获取视频凭证的request和response对象
        GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();

        //向request对象中设置存在阿里云中的视频的id
        request.setVideoId("abc1889a154842d9b0e0e6d23d458123");

        //调用初始化对象的方法获取视频凭证
        response = client.getAcsResponse(request);
        System.out.println("playAuth:" + response.getPlayAuth());
    }
}
