package com.qst.eduservice.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 讲师(EduTeacher)表实体类
 *
 * @author 记住吾名梦寒
 * @since 2022-09-03 11:01:04
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EduTeacher extends Model<EduTeacher>{

    /**
     *      注意： 以下的@TableId注解中的type = IdType.ID_WORKER_STR属性值在mp新版中没有了
     *      mp3.3.0版本以后就没有ID_WORKER_STR了
      */
    //讲师ID
    @TableId(value = "id",type = IdType.ID_WORKER_STR)
    private String id;
    //讲师姓名
    private String name;
    //讲师简介
    @ApiModelProperty("讲师简介")
    private String intro;
    //讲师资历,一句话说明讲师
    @ApiModelProperty("讲师资历,一句话说明讲师")
    private String career;
    //头衔 1高级讲师 2首席讲师
    @ApiModelProperty(value = "1高级讲师 2首席讲师")
    private Integer level;
    //讲师头像
    @ApiModelProperty("讲师头像")
    private String avatar;
    //排序
    private Integer sort;

    //逻辑删除 1（true）已删除， 0（false）未删除
    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    @TableLogic
    private Boolean isDeleted;

    //创建时间
    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)  //在插入时自动填充字段
    private Date gmtCreate;
    //更新时间
    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)  //在插入和更新时自动填充
    private Date gmtModified;


    /**
     * 获取主键值
     *
     * @return 主键值
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}

