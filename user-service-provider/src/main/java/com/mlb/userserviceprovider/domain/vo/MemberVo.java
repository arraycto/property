package com.mlb.userserviceprovider.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mlb.userserviceprovider.common.JsonLongSerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author mlb
 */
@Data
public class MemberVo implements Serializable {
    /** 用户id */
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long userId;

    /** 用户名 */
    private String username;

    /** 用户类型，1业主，2租户 */
    private String userType;

    /**  入住时间   */
    private String createTime;

    /** 状态，1正常，2离开，3停水，4停电，5停水+停电*/
    private String removed;

    private Long homeId;

    /**  居住房屋,名下有多套默认选择第一套  */
    private String home;

    /**  联系方式  */
    private String phone;
    /** 租赁时长 */
    private String leaseDuration;
}
