package com.qst.osssevice.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.qst.osssevice.service.OSSService;
import com.qst.osssevice.utils.ConstantPropertiesUtils;
import org.apache.tomcat.util.bcel.Const;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * @author 记住吾名梦寒
 * @version 1.0
 * @date 2022/9/7
 * @description
 */
@Service
public class OSSServiceImpl implements OSSService {
    @Override
    public String uploadOssFile(MultipartFile file) {

        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtils.KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.KEY_SECRET;
        // 填写Bucket名称，例如examplebucket。
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        //获取源文件名称
        String filename = file.getOriginalFilename();
        // 上传到OSS中的文件路径和文件名称(我这里直接上传到OSS的根路径下，没有放到子目录下)，完整路径中不能包含Bucket名称
        // 通过截取UUID字符串的前八位防止文件名相同而覆盖
        String fileName2 = UUID.randomUUID().toString().replace("-","").substring(0,8) + "-" + filename;
        //优化:根据当前日期，建立子目录，将文件上传到OSS的当前日期目录下
        String datePath = new DateTime().toString("yyyy/MM/dd");
        String objectName = datePath + "/" + fileName2;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            //获取上传文件输入流
            InputStream inputStream = file.getInputStream();
            // 调用方法实现上传
            ossClient.putObject(bucketName, objectName, inputStream);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }

        //拼接出文件在OSS中的路径并返回   示例格式：https://springcloud-guli-edu.oss-cn-guangzhou.aliyuncs.com/1.jpg
        String url = "https://" + bucketName + "." + endpoint + "/" + objectName;
        return url;
    }
}
