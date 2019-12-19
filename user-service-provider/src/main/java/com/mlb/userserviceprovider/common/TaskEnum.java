package com.mlb.userserviceprovider.common;

/**
 * @author mlb
 */

public enum TaskEnum {

    PENDING_DISPOSAL(0,"待处理"),
    IN_PROCESSING(1,"处理中"),
    PENDING_ACCEPTANCE(2,"待验收"),
    COMPLETED(3,"已完成");

    TaskEnum(Integer code,String detail){
        this.code = code;
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
