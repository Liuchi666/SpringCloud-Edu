package com.qst.orderservice.entity;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 支付日志表(PayLog)表实体类
 *
 * @author 记住吾名梦寒
 * @since 2022-09-26 18:42:12
 */
@SuppressWarnings("serial")
@TableName("t_pay_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayLog extends Model<PayLog> {

    /**
     *      注意： 以下的@TableId注解中的type = IdType.ID_WORKER_STR属性值在mp新版中没有了
     *      mp3.3.0版本以后就没有ID_WORKER_STR了
     */
    @TableId(value = "id",type = IdType.ID_WORKER_STR)
    private String id;
    //订单号
    private String orderNo;
    //支付完成时间
    private Date payTime;
    //支付金额（分）
    private BigDecimal totalFee;
    //交易流水号
    private String transactionId;
    //交易状态
    private String tradeState;
    //支付类型（1：微信 2：支付宝）
    private Integer payType;
    //其他属性
    private String attr;
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

