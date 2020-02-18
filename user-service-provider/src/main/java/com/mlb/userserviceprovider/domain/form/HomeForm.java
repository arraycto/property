package com.mlb.userserviceprovider.domain.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class HomeForm implements Serializable {

    @JsonProperty(value = "unit")
    @NotNull(message = "单元号不能为空")
    private Integer unit;
    @NotNull(message = "楼层不能为空")
    @JsonProperty(value = "floor")
    private Integer floor;
    @JsonProperty(value = "room")
    @NotNull(message = "房间号不能为空")
    private Integer room;

}
