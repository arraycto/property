package com.mlb.userserviceprovider.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 物业用户表
 * </p>
 *
 * @author mlb
 * @since 2019-11-19
 */
@Data
public class PropertyUser extends Model<PropertyUser> {

    private static final long serialVersionUID=1L;

    /**
     * 用户Id
     */
    @TableId(value = "user_id")
    private Long userId;

    /**
     * 用户名称
     */
    private String username;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 登陆密码
     */
    private String password;

    /**
     * 用户类型，1住户，2维修工，3管理员
     */
    private Integer userType;

    /**
     * 注册时间
     */
    private Date createTime;

    /**
     * 地址
     */
    private String address;
}
