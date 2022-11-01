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
 * 课程简介(EduCourseDescription)表实体类
 *
 * @author 记住吾名梦寒
 * @since 2022-09-09 10:22:19
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EduCourseDescription extends Model<EduCourseDescription> {
    /**
     *      注意： 以下的@TableId注解中的type = IdType.ID_WORKER_STR属性值在mp新版中没有了
     *      mp3.3.0版本以后就没有ID_WORKER_STR了
     */
    @TableId(value = "id",type = IdType.INPUT)  //注意：这里主键生成策略选择INPUT，即由我们手动输入主键
    //课程ID
    private String id;
    //课程简介
    private String description;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

}

