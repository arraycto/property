package com.mlb.userserviceprovider.domain.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mlb
 */
@Data
public class LoginUser implements Serializable {
    @JsonProperty(value = "username")
    private String username;
    @JsonProperty(value = "password")
    private String password;
    @JsonProperty(value = "identity")
    private Integer identity;
}
