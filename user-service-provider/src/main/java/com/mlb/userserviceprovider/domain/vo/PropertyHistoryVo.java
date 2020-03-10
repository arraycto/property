package com.mlb.userserviceprovider.domain.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author mlb
 */
@Data
public class PropertyHistoryVo implements Serializable {
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
     * 用户类型
     */
    private String userType;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 离开时间
     */
    private String removeTime;
}
