package com.mlb.userserviceprovider.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author mlb
 * @since 2019-12-20
 */
@Data
public class Property extends Model<Property> {

    private static final long serialVersionUID=1L;

    /**
     * 用户id
     */
    @TableId(value = "user_id")
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户类型，1物业管理员，2员工
     */
    private Integer userType;

    /**
     * 性别，1男，2女
     */
    private Integer gender;

    /**
     * 注册时间
     */
    private LocalDateTime createTime;

    /**
     * 家庭住址
     */
    private String address;

    /**
     * 登陆密码
     */
    private String password;

    /**
     * 联系方式
     */
    private String phone;
}
