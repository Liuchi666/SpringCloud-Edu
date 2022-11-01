package com.qst.osssevice.controller;

import com.qst.commonutils.R;
import com.qst.osssevice.service.OSSService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/7
 * @description
 */
@Api("上传头像到OSS")
@RestController
@RequestMapping("/eduOss/fileOss")
public class OSSController {
    @Autowired
    private OSSService ossService;

    /**
     *  上传头像的方法
     */
    @PostMapping
    public R uploadOssFile(MultipartFile file){
        //获取上传文件
        //返回上传到oss的路径
        String url = ossService.uploadOssFile(file);
        return R.ok().data("url",url);
    }


}
