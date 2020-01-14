package com.mlb.userserviceprovider.domain.vo;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mlb.userserviceprovider.common.JsonLongSerializer;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author mlb
 */
@Data
public class PropertyVo implements Serializable {
    /** 用户id */
    @JsonSerialize(using = JsonLongSerializer.class)
    private Long userId;

    /** 用户名 */
    private String username;

    /** 用户类型，1业主，2员工，3物业管理员，4租户 */
    private String userType;

    /**  性别，1男，2女  */
    private String gender;

    /**  注册时间   */
    private String createTime;

    /**  家庭住址   */
    private String address;

    /**  联系方式  */
    private String phone;
}
