package com.mlb.userserviceprovider.common;

/**
 * @author mlb
 */

public enum UserTypeEnum {
    HOUSE_HOLD(1,"业主"),
    WORKER(2,"员工"),
    ADMIN(3,"物业管理员"),
    TENANT(4,"租户");

    UserTypeEnum(Integer code, String detail) {
        this.code = code;
        this.detail = detail;
    }

    private Integer code;
    private String detail;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }


}
