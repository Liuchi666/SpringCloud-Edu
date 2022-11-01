package com.qst.vodservice.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/19
 * @description
 */
public interface VodService {

    String updateVideoAliYun(MultipartFile file);

    void deleteAliYunVideo(String videoId);

    void removeMoreAliYunVideo(List<String> videoIdList);
}
