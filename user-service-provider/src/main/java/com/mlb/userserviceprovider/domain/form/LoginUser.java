package com.mlb.userserviceprovider.domain.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mlb
 */
@Data
public class LoginUser implements Serializable {
    /**既可以用用户名，也可以用手机号登陆 */
    @JsonProperty(value = "username")
    private String username;
    @JsonProperty(value = "password")
    private String password;
    @JsonProperty(value = "identity")
    private Integer identity;
}
