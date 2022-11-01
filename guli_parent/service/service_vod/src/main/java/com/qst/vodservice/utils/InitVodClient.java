package com.qst.vodservice.utils;

import com.aliyun.oss.ClientException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/19
 * @description     初始化操作类
 */
public class InitVodClient {

    /**
     *  初始化操作，创建DefaultAcsClient对象(静态方法)
     *
     * @param accessKeyId   阿里云的密钥id
     * @param accessKeySecret  阿里云的密钥
     */
    public static DefaultAcsClient initVodClient(String accessKeyId, String accessKeySecret) throws ClientException {
        String regionId = "cn-shanghai";  // 点播服务接入区域
        DefaultProfile profile = DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

}
