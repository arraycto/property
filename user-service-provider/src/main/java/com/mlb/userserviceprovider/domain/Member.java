package com.mlb.userserviceprovider.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author mlb
 * @since 2020-02-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Member extends Model<Member> {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "userId")
    private Long userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 联系电话
     */
    private String phone;
    /**
     * 用户类型，1业主，2租户
     */
    @TableField("user_type")
    private Integer userType;
    /**
     * 入住时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
    /**
     * 状态，1正常，2离开，3停水，4停电，5停水+停电
     */
    private Integer removed;
    /**
     * 离开时间
     */
    @TableField("remove_time")
    private LocalDateTime removeTime;

}
