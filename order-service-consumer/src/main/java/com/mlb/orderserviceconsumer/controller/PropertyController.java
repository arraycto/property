package com.mlb.orderserviceconsumer.controller;


import cn.hutool.core.util.ObjectUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.mlb.userserviceprovider.common.JsonResult;
import com.mlb.userserviceprovider.common.TokenUse;
import com.mlb.userserviceprovider.domain.Property;
import com.mlb.userserviceprovider.domain.form.LoginUser;
import com.mlb.userserviceprovider.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.concurrent.TimeUnit;

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

    @Autowired
    private RedisTemplate redisTemplate;

    @CrossOrigin
    @PostMapping("/login")
    @ResponseBody
    public JsonResult loginByUser(@RequestBody LoginUser loginUser){
        Property property = propertyService.loginByUser(loginUser);
        if(ObjectUtil.isNull(property.getUserId())){
            return JsonResult.builder().code(JsonResult.FAIL).msg(JsonResult.FAIL_MSG).build();
        }
        String token = TokenUse.sign(property.getPhone(),property.getUserId());
        //将token塞入缓存，设置30分钟过期
        redisTemplate.opsForValue().set("tokenKey",token,30, TimeUnit.MINUTES);
        return JsonResult.builder().code(JsonResult.SUCCESS).msg(JsonResult.SUCCESS_MSG).data(token).build();
    }
}

