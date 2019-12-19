package com.mlb.userserviceprovider.domain.form;

import com.baomidou.mybatisplus.core.injector.methods.Insert;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author mlb
 */
@Data
public class PropertyUserForm implements Serializable {

    @NotBlank(message = "姓名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "联系方式不能为空")
    @Size(min = 11,max = 11,message = "手机号码格式有误")
    private String phone;

    @NotNull(message = "请选择用户类型")
    private Integer userType;

    @NotBlank(message = "地址不能为空")
    private String address;
}
