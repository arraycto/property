package com.mlb.userserviceprovider.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mlb
 */
@Data
public class PropertyTaskVo implements Serializable {

    /**查看任务的用户Id*/
    private Long userId;
    /**查看任务的用户类型*/
    private Integer userType;
    /**几幢*/
    private Integer block;
    /**单元*/
    private Integer unit;
    /**房间号*/
    private Integer room;
    /**提出需求用户名*/
    private String createName;
}
