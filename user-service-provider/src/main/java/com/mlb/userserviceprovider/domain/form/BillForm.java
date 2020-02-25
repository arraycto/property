package com.mlb.userserviceprovider.domain.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author mlb
 */
@Data
public class BillForm implements Serializable {
    @JsonProperty(value = "userId")
    @NotBlank(message = "用户不能为空")
    private String userId;
    @JsonProperty(value = "homeId")
    @NotBlank(message = "请选择房产信息")
    private String homeId;
    @JsonProperty(value = "money")
    @NotNull(message = "金额不能为空")
    private BigDecimal money;
}
