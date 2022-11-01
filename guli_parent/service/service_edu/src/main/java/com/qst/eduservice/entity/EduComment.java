package com.qst.eduservice.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 评论(EduComment)表实体类
 *
 * @author 记住吾名梦寒
 * @since 2022-09-26 15:22:08
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EduComment extends Model<EduComment> {
    /**
     *      注意： 以下的@TableId注解中的type = IdType.ID_WORKER_STR属性值在mp新版中没有了
     *      mp3.3.0版本以后就没有ID_WORKER_STR了
     */
    @TableId(value = "id",type = IdType.ID_WORKER_STR)
    private String id;
    //课程id
    private String courseId;
    //讲师id
    private String teacherId;
    //会员id
    private String memberId;
    //会员昵称
    private String nickname;
    //会员头像
    private String avatar;
    //评论内容
    private String content;
    //逻辑删除 1（true）已删除， 0（false）未删除
    @TableLogic
    private Boolean isDeleted;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;

}

