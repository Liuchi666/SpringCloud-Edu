package com.qst.staservice.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 网站统计日数据(StatisticsDaily)表实体类
 *
 * @author 记住吾名梦寒
 * @since 2022-09-27 18:51:46
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticsDaily extends Model<StatisticsDaily> {
    //主键
    @TableId(type = IdType.ID_WORKER_STR)
    private String id;
    //统计日期
    private String dateCalculated;
    //注册人数
    private Integer registerNum;
    //登录人数
    private Integer loginNum;
    //每日播放视频数
    private Integer videoViewNum;
    //每日新增课程数
    private Integer courseNum;
    //创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;
    //更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}

