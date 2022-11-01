package com.qst.commonutils.ordervo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员表
 * @TableName ucenter_member
 */
@TableName(value ="ucenter_member")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UcenterMemberOrder implements Serializable {
    /**
     *      注意： 以下的@TableId注解中的type = IdType.ID_WORKER_STR属性值在mp新版中没有了
     *      mp3.3.0版本以后就没有ID_WORKER_STR了
     */
    @TableId(value = "id",type = IdType.ID_WORKER_STR)
    private String id;

    /** 微信openid  */
    private String openid;

    /** 手机号  */
    private String mobile;

    /** 密码  */
    private String password;

    /** 昵称  */
    private String nickname;

    /** 性别 1 女，2 男  */
    private Integer sex;

    /** 年龄  */
    private Integer age;

    /** 用户头像  */
    private String avatar;

    /** 用户签名  */
    private String sign;

    /** 是否禁用 1（true）已禁用，  0（false）未禁用  */
    private Integer isDisabled;

    /** 逻辑删除 1（true）已删除， 0（false）未删除  */
    @TableLogic
    private Integer isDeleted;

    /** 创建时间  */
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreate;

    /** 更新时间  */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;
}
