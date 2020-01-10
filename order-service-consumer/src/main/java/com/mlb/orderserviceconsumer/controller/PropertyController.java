package com.mlb.orderserviceconsumer.controller;


import cn.hutool.core.util.ObjectUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.mlb.userserviceprovider.common.JsonResult;
import com.mlb.userserviceprovider.common.TokenUse;
import com.mlb.userserviceprovider.domain.Property;
import com.mlb.userserviceprovider.domain.form.LoginUser;
import com.mlb.userserviceprovider.domain.vo.PropertyVo;
import com.mlb.userserviceprovider.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;
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
        if (ObjectUtil.isNull(property.getUserId())) {
            return JsonResult.builder().code(JsonResult.FAIL).msg(JsonResult.FAIL_MSG).build();
        }
        String token = "Bearer:" + TokenUse.sign(property.getPhone(), property.getUserId());
        //将token塞入缓存，设置30分钟过期
        redisTemplate.opsForValue().set("tokenKey", token, 30, TimeUnit.MINUTES);
        return JsonResult.builder().code(JsonResult.SUCCESS).msg(JsonResult.SUCCESS_MSG).data(token).build();
    }

//    @CrossOrigin
    @PostMapping("/propertyList")
    @ResponseBody
    public JsonResult propertyList(PropertyVo propertyVo){
        System.out.println(propertyVo);
        List<Property> propertyList = propertyService.propertyList(propertyVo);
        return JsonResult.builder().data(propertyList).build();
    }

    /**
     * redisTemplate存值序列化
     * @param redisTemplate
     */
    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }
}



