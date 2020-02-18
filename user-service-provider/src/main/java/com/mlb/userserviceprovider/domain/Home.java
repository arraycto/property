package com.mlb.userserviceprovider.domain;


import java.io.Serializable;
import java.time.LocalDateTime;


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
 * @since 2020-02-17
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Home extends Model<Home> {

    private static final long serialVersionUID = 1L;

    /**
     * 房间id
     */
    @TableId(value = "home_id")
    private Long homeId;
    /**
     * 单元
     */
    private Integer unit;
    /**
     * 楼层
     */
    private Integer floor;
    /**
     * 房间号
     */
    private Integer room;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 是否删除，0否，1是
     */
    private Integer deleted;

    /**
     * 删除时间，默认为空
     */
    private LocalDateTime deleteTime;
}
