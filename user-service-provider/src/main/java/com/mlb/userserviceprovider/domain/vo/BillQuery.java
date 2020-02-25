package com.mlb.userserviceprovider.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mlb
 */
@Data
public class BillQuery implements Serializable {
    /** 联系电话 */
    private String phone;
    /** 月份 */
    private String month;
    /** 缴纳开始时间 */
    private String payStartTime;
    /** 缴纳结束时间 */
    private String payEndTime;
    /** 用户id，要根据phone，不能手动输入 */
    private String userId;
}
