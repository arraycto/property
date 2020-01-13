package com.mlb.orderserviceconsumer.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
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
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
        System.out.println(loginUser);
        Property property = propertyService.loginByUser(loginUser);
        if (ObjectUtil.isNull(property.getUserId())) {
            return JsonResult.builder().code(JsonResult.FAIL).msg(JsonResult.FAIL_MSG).build();
        }
        String token = "Bearer:" + TokenUse.sign(property.getPhone(), property.getUserId());
        //将token塞入缓存，设置30分钟过期
        redisTemplate.opsForValue().set("tokenKey", token, 30, TimeUnit.MINUTES);
        return JsonResult.builder().code(JsonResult.SUCCESS).msg(JsonResult.SUCCESS_MSG).data(token).build();
    }

    @CrossOrigin
    @PostMapping("/propertyList")
    @ResponseBody
    public JsonResult propertyList(PropertyVo propertyVo){
        List<Property> propertyList = propertyService.propertyList(propertyVo);
        if(ObjectUtil.isNull(propertyList)){
            return JsonResult.builder().code(JsonResult.FAIL).msg("暂无数据").build();
        }
        List<PropertyVo> propertyVoList = new ArrayList<>();
        propertyList.stream().forEach(item -> {
            PropertyVo property = new PropertyVo();
            BeanUtil.copyProperties(item,property);
            if(item.getGender().equals(1)){
                property.setGender("男");
            }else{
                property.setGender("女");
            }
            switch (item.getUserType()){
                case 1: property.setUserType("业主");break;
                case 2: property.setUserType("员工");break;
                case 3: property.setUserType("物业管理员");break;
                default: property.setUserType("租户");
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-mm-dd HH:MM:SS");
            property.setCreateTime(formatter.format(item.getCreateTime()));
            propertyVoList.add(property);
        });
        return JsonResult.builder().data(propertyVoList).build();
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



