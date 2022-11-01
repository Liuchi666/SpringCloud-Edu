package com.qst.eduservice.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 课程(EduCourse)表实体类
 *
 * @author 记住吾名梦寒
 * @since 2022-09-09 10:22:18
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EduCourse extends Model<EduCourse> {
    /**
     *      注意： 以下的@TableId注解中的type = IdType.ID_WORKER_STR属性值在mp新版中没有了
     *      mp3.3.0版本以后就没有ID_WORKER_STR了
     */
    @TableId(value = "id",type = IdType.ID_WORKER_STR)
    private String id;
    //课程讲师ID
    private String teacherId;
    //一级分类课程ID
    private String subjectId;
    //二级分类课程ID
    private String subjectParentId;
    //课程标题
    private String title;
    //课程销售价格，设置为0则可免费观看
    private BigDecimal price;
    //总课时
    private Integer lessonNum;
    //课程封面图片路径
    private String cover;
    //销售数量
    private Long buyCount;
    //浏览数量
    private Long viewCount;
    //乐观锁
    private Long version;
    //课程状态 Draft未发布  Normal已发布
    private String status;
    //逻辑删除 1（true）已删除， 0（false）未删除
    @TableLogic
    private Integer isDeleted;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}

