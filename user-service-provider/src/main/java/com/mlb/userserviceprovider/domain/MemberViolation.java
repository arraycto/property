package com.mlb.userserviceprovider.domain;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author mlb
 * @since 2020-03-13
 */
@Data
public class MemberViolation extends Model<MemberViolation> {

    private static final long serialVersionUID=1L;

    /**
     * 处罚id
     */
    @TableId(value = "violation_id")
    private Long violationId;

    /**
     * 住户id
     */
    private Long userId;

    /**
     * 住户姓名
     */
    private String username;

    /**
     * 处罚选择，1停水，2停电，3停电+停水
     */
    private Integer punish;

    /**
     * 处罚时间
     */
    private LocalDateTime createTime;

    /**
     * 是否撤销，1未撤销，2撤销
     */
    private Integer deleted;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}
