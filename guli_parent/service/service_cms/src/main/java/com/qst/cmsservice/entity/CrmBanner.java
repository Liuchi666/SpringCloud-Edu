package com.qst.cmsservice.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 首页banner表(CrmBanner)表实体类
 *
 * @author 记住吾名梦寒
 * @since 2022-09-21 16:04:46
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrmBanner extends Model<CrmBanner> {
    /**
     *      注意： 以下的@TableId注解中的type = IdType.ID_WORKER_STR属性值在mp新版中没有了
     *      mp3.3.0版本以后就没有ID_WORKER_STR了
     */
    @TableId(value = "id",type = IdType.ID_WORKER_STR)
    private String id;
    //标题
    private String title;
    //图片地址
    private String imageUrl;
    //链接地址
    private String linkUrl;
    //排序
    private Integer sort;
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

