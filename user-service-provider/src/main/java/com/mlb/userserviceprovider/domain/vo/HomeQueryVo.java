package com.mlb.userserviceprovider.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author mlb
 */
@Data
public class HomeQueryVo implements Serializable {

    /**  单元 */
    private Integer unit;
    /**  楼层 */
    private Integer floor;
    /** 房间号 */
    private Integer room;
    /** 注册开始时间*/
    private String startTime;
    /** 注册结束时间 */
    private String endTime;
}
