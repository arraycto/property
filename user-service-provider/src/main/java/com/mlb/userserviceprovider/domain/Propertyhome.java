package com.mlb.userserviceprovider.domain;

import java.time.LocalDateTime;
import java.util.Date;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户房产信息关联表
 * </p>
 *
 * @author mlb
 * @since 2020-02-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Propertyhome extends Model<Propertyhome> {

    private static final long serialVersionUID = 1L;

    /**
     * 房间id
     */
    @TableId(value = "home_id")
    private Long homeId;
    /**
     * 用户id
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 0房间所有者，1房间租赁者
     */
    private Integer type;
    /**
     * 业主购买时间/租客租赁时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;
    /**
     * 0未删除，1删除
     */
    private Integer deleted;
    /**
     * 租赁时长
     */
    @TableField("lease_duration")
    private Integer leaseDuration;
    /**
     * 业主出售时间/租客退租时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;
    /**
     * 用户联系电话
     */
    private String phone;

}
