package com.mlb.userserviceprovider.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RespPageBean implements Serializable {
    private long total;
    private List<?> data;
}
