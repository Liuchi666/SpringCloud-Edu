package com.qst.osssevice.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/7
 * @description
 */
public interface OSSService {
    String uploadOssFile(MultipartFile file);
}
