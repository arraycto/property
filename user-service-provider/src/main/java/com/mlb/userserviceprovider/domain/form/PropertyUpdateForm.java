package com.mlb.userserviceprovider.domain.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PropertyUpdateForm implements Serializable {
    @JsonProperty(value = "userId")
    private String userId;
    @JsonProperty(value = "username")
    private String username;
    @JsonProperty(value = "phone")
    private String phone;
    @JsonProperty(value = "address")
    private String address;
}
