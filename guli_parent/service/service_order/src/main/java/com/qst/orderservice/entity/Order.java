package com.qst.orderservice.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单(Order)表实体类
 *
 * @author 记住吾名梦寒
 * @since 2022-09-26 18:41:48
 */
@SuppressWarnings("serial")
@TableName("t_order")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order extends Model<Order> {

    /**
     *      注意： 以下的@TableId注解中的type = IdType.ID_WORKER_STR属性值在mp新版中没有了
     *      mp3.3.0版本以后就没有ID_WORKER_STR了
     */
    @TableId(value = "id",type = IdType.ID_WORKER_STR)
    private String id;
    //订单号
    private String orderNo;
    //课程id
    private String courseId;
    //课程名称
    private String courseTitle;
    //课程封面
    private String courseCover;
    //讲师名称
    private String teacherName;
    //会员id
    private String memberId;
    //会员昵称
    private String nickname;
    //会员手机
    private String mobile;
    //订单金额（分）
    private BigDecimal totalFee;
    //支付类型（1：微信 2：支付宝）
    private Integer payType;
    //订单状态（0：未支付 1：已支付）
    private Integer status;
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

