package com.mlb.userserviceprovider.domain.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PasswordForm implements Serializable {

    @JsonProperty(value = "userId")
    private String userId;
    @JsonProperty(value = "password")
    private String password;
    @JsonProperty(value = "checkPassword")
    private String checkPassword;
}
