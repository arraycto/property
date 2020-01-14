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
 * @since 2020-01-14
 */
@Data
public class PropertyHistory extends Model<PropertyHistory> {

    private static final long serialVersionUID=1L;

    /**
     * 用户id
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
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 离开时间
     */
    private LocalDateTime removeTime;
}
