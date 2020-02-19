package com.mlb.userserviceprovider.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class MemberQueryVo implements Serializable
{
    /** 用户名 */
    private String username;
    /** 联系电话 */
    private String phone;
    /** 用户类型，1业主，2租户*/
    private Integer userType;
    /** 入住开始时间 */
    private String startTime;
    /** 入住结束时间 */
    private String endTime;
}
