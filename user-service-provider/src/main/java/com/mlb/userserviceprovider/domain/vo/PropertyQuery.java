package com.mlb.userserviceprovider.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mlb.userserviceprovider.common.JsonLongSerializer;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mlb
 */
@Data
public class PropertyQuery implements Serializable {

    /** 用户名 */
    private String username;

    /** 用户类型，1物业管理员，2员工 */
    private String userType;

    /**  性别，1男，2女  */
    private String gender;

    /**  注册时间起始   */
    private String startTime;

    /**  注册时间结束   */
    private String endTime;

    /**  联系方式  */
    private String phone;
}
