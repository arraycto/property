package com.mlb.userserviceprovider.domain;

import java.math.BigDecimal;
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
 * @since 2020-02-24
 */
@Data
public class Bill extends Model<Bill> {

    private static final long serialVersionUID=1L;

    /**
     * 用户id
     */
    @TableId(value = "user_id")
    private Long userId;

    /**
     * 房产信息id
     */
    private Long homeId;

    /**
     * 金额
     */
    private BigDecimal money;

    /**
     * 账单信息
     */
    private String remark;

    /**
     * 是否缴纳，0未缴纳，1缴纳
     */
    private Integer payed;

    /**
     * 缴纳最后截止日期
     */
    private LocalDateTime deadline;

    /**
     * 缴纳日期
     */
    private LocalDateTime payTime;

    /**
     * 账单生成日期
     */
    private LocalDateTime createTime;
}
