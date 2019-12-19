package com.mlb.userserviceprovider.domain.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author mlb
 */
@Data
public class PropertyUserVo implements Serializable {

    /** 用户ID*/
    private Long userId;

    /** 用户名*/
    private String username;

    /** 联系电话*/
    private String phone;

    /** 用户类型*/
    private Integer userType;

    /** 开始时间*/
    private Date startTime;

    /** 截止时间*/
    private Date endTime;
}
