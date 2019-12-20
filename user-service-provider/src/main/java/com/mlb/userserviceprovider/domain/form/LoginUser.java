package com.mlb.userserviceprovider.domain.form;

import lombok.Data;

import java.io.Serializable;

/**
 * @author mlb
 */
@Data
public class LoginUser implements Serializable {
    private String username;

    private String password;
}
