package com.mlb.userserviceprovider.domain.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

/**
 * @author mlb
 */
@Data
public class MemberViolationForm implements Serializable {
    @JsonProperty(value = "userId")
    private Long userId;
    @JsonProperty(value = "username")
    private String username;
    @JsonProperty(value = "punish")
    private Integer punish;
}
