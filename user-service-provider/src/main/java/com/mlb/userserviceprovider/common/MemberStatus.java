package com.mlb.userserviceprovider.common;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author mlb
 * 住户状态
 */

public enum MemberStatus {

    NORMAL(1,"正常"),
    LEAVE(2,"离开"),
    WATER_CUT_OFF(3,"停水"),
    POWER_FAILURE(4,"停电"),
    STOP_WATER_POWER(5,"停水停电");


    MemberStatus(Integer code,String detail){
        this.code = code;
        this.detail = detail;
    };

    @EnumValue
    private final Integer code;
    private final String detail;

    public Integer getCode() {
        return code;
    }

    public String getDetail() { return detail;}


    /**
     * 获得枚举
     * @param code
     * @return
     */
    public static MemberStatus getStatus(Integer code){
        if(null != code){
            for(MemberStatus memberStatus : MemberStatus.values()){
                if(code.intValue() == memberStatus.getCode()){
                    return memberStatus;
                }
            }
        }
        return null;
    }
}
