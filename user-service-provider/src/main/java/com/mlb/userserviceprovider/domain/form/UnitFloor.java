package com.mlb.userserviceprovider.domain.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author mlb
 */
@Data
public class UnitFloor implements Serializable {
    @JsonProperty(value = "unit")
    private String unit;
    @JsonProperty(value = "floor")
    private String floor;
}
