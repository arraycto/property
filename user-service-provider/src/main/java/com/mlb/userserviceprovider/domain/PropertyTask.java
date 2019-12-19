package com.mlb.userserviceprovider.domain;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author mlb
 * @since 2019-11-20
 */
@Data
public class PropertyTask extends Model<PropertyTask> {

    private static final long serialVersionUID=1L;

    /**
     * 需求Id
     */
    @TableId(value = "task_id")
    private Long taskId;

    /**
     * 需求内容
     */
    private String taskOutline;

    /**
     * 幢
     */
    private Integer block;

    /**
     * 单元
     */
    private Integer unit;

    /**
     * 房间号
     */
    private Integer room;

    /**
     * 提出需求者Id
     */
    private Long createId;

    /**
     * 提出需求者用户名
     */
    private String createName;

    /**
     * 提出时间
     */
    private Date createTime;

    /**
     * 任务状态，0待处理，1处理中，2待验收，3完成
     */
    private Integer taskStatus;

    /**
     * 处理需求者Id
     */
    private Long acceptId;

    /**
     * 确认时间
     */
    private Date acceptTime;

    /**
     * 验收时间
     */
    private Date acceeptanceTime;

    /**
     * 完成时间
     */
    private Date completeTime;

}
