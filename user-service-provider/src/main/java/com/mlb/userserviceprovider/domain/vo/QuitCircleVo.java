package com.mlb.userserviceprovider.domain.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mlb
 */
@Data
public class QuitCircleVo implements Serializable {
    private String name;
    private Integer value;
}
