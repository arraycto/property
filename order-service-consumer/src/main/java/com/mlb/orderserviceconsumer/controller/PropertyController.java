package com.mlb.orderserviceconsumer.controller;


import com.alibaba.dubbo.config.annotation.Reference;
import com.mlb.userserviceprovider.common.JsonResult;
import com.mlb.userserviceprovider.domain.form.LoginUser;
import com.mlb.userserviceprovider.service.PropertyService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mlb
 * @since 2019-12-20
 */
@Controller
@RequestMapping("/property")
public class PropertyController {

    @Reference
    private PropertyService propertyService;

    @PostMapping("/login")
    @ResponseBody
    public JsonResult loginByUser(LoginUser loginUser){
        return propertyService.loginByUser(loginUser);
    }
}

