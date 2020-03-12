package com.mlb.userserviceprovider.domain.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author mlb
 */
@Data
public class MemberForm implements Serializable {

    @JsonProperty(value = "username")
    @NotBlank(message = "姓名不能为空")
    private String username;

    @JsonProperty(value = "phone")
    @NotBlank(message = "联系方式不能为空")
    @Size(min = 11,max = 11,message = "手机号码格式有误")
    private String phone;

    @JsonProperty(value = "userType")
    @NotNull(message = "请选择用户类型")
    private Integer userType;

    @JsonProperty(value = "unit")
    private Integer unit;

    @JsonProperty(value = "floor")
    private Integer floor;

    @JsonProperty(value = "room")
    private Integer room;

    /**  租赁时长 */
    @JsonProperty(value = "leaseDuration")
    private Integer leaseDuration;
}
