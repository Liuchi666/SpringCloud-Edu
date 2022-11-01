package com.qst.eduservice.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 课程视频(EduVideo)表实体类
 *
 * @author 记住吾名梦寒
 * @since 2022-09-09 10:22:21
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EduVideo extends Model<EduVideo> {
    /**
     *      注意： 以下的@TableId注解中的type = IdType.ID_WORKER_STR属性值在mp新版中没有了
     *      mp3.3.0版本以后就没有ID_WORKER_STR了
     */
    @TableId(value = "id",type = IdType.ID_WORKER_STR)
    //视频ID
    private String id;
    //课程ID
    private String courseId;
    //章节ID
    private String chapterId;
    //节点名称
    private String title;
    //云端视频资源
    private String videoSourceId;
    //原始文件名称
    private String videoOriginalName;
    //排序字段
    private Integer sort;
    //播放次数
    private Long playCount;
    //是否可以试听：0收费 1免费
    private Boolean isFree;
    //视频时长（秒）
    private Object duration;
    //Empty未上传 Transcoding转码中  Normal正常
    private String status;
    //视频源文件大小（字节）
    private Long size;
    //乐观锁
    private Long version;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}

