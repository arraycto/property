package com.mlb.userserviceprovider.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mlb
 */
@Data
@Builder
public  class JsonResult<T> implements Serializable {
    private static final long serialVersionUID = 8357896456507912185L;

    @Builder.Default
    public static final Integer SUCCESS = 0;
    @Builder.Default
    public static final Integer FAIL = 1;

    @Builder.Default
    public static final Integer WARN = 2;
    @Builder.Default
    public static final String SUCCESS_MSG = "success";
    @Builder.Default
    public static final String FAIL_MSG = "fail";

    @Builder.Default
    private Integer code = SUCCESS;
    @Builder.Default
    private String msg = SUCCESS_MSG;

    private T data;
}
