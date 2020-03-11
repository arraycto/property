package com.mlb.userserviceprovider.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author mlb
 * @since 2020-03-11
 */
@Data
public class PropertyCountQuit extends Model<PropertyCountQuit> {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 日期
     */
    private String month;

    /**
     * 人数
     */
    private Integer num;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
