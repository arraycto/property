package com.mlb.userserviceprovider.domain.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author mlb
 */
@Data
public class PropertyTaskForm implements Serializable {

    @NotBlank(message = "请求内容不能为空")
    @Size(max = 50,message = "请求内容不能超过50个字")
    private String taskOutline;

    @NotNull(message = "几幢楼不能为空")
    private Integer block;

    @NotNull(message = "单元号不能为空")
    private Integer unit;

    @NotNull(message = "房间号不能为空")
    private Integer room;

}
