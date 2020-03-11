package com.mlb.orderserviceconsumer.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.mlb.orderserviceconsumer.util.DateUtil;
import com.mlb.userserviceprovider.common.JsonResult;
import com.mlb.userserviceprovider.common.RespPageBean;
import com.mlb.userserviceprovider.common.SnowFlakeIdUtils;
import com.mlb.userserviceprovider.common.TokenUse;
import com.mlb.userserviceprovider.domain.Property;
import com.mlb.userserviceprovider.domain.PropertyHistory;
import com.mlb.userserviceprovider.domain.form.LoginUser;
import com.mlb.userserviceprovider.domain.form.PasswordForm;
import com.mlb.userserviceprovider.domain.form.PropertyUpdateForm;
import com.mlb.userserviceprovider.domain.form.PropertyUserForm;
import com.mlb.userserviceprovider.domain.vo.PropertyHistoryVo;
import com.mlb.userserviceprovider.domain.vo.PropertyQuery;
import com.mlb.userserviceprovider.service.HomeService;
import com.mlb.userserviceprovider.service.PropertyHistoryService;
import com.mlb.userserviceprovider.service.PropertyService;
import com.mlb.userserviceprovider.service.PropertyhomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
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

    @Reference
    private PropertyHistoryService propertyHistoryService;

    @Reference
    private PropertyhomeService propertyhomeService;

    @Reference
    private HomeService homeService;

    @Autowired
    private RedisTemplate redisTemplate;

    @CrossOrigin
    @PostMapping("/login")
    @ResponseBody
    public JsonResult loginByUser(@RequestBody LoginUser loginUser){
        System.out.println(loginUser);
        Property property = propertyService.loginByUser(loginUser);
        if (ObjectUtil.isNull(property)) {
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
    public JsonResult propertyList(@RequestParam(defaultValue = "1") String page, @RequestParam(defaultValue = "10") String  size, @RequestBody(required = false)PropertyQuery propertyVo){
        RespPageBean pageBean = propertyService.propertyList(propertyVo,Integer.valueOf(page),Integer.valueOf(size));
        return JsonResult.builder().data(pageBean).build();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping("/saveProperty")
    public JsonResult addProperty(@RequestBody PropertyUserForm propertyUserForm){//
        Property property = new Property();
        BeanUtil.copyProperties(propertyUserForm,property);
        LocalDateTime date = LocalDateTime.now();
        property.setCreateTime(date);
        //运用雪花算法生成用户ID（唯一）
        SnowFlakeIdUtils snowFlakeIdUtils = new SnowFlakeIdUtils(9,1);
        property.setUserId(snowFlakeIdUtils.nextId());
        if(!propertyService.save(property)){
            return JsonResult.builder().code(JsonResult.FAIL).msg(JsonResult.FAIL_MSG).build();
        }
        return JsonResult.builder().data(property).build();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping("/updateProperty")
    public JsonResult updateProperty(@RequestBody PropertyUpdateForm propertyUpdateForm){
        Property property = propertyService.getById(Long.valueOf(propertyUpdateForm.getUserId()));
        if(ObjectUtil.isNull(property)){
            return JsonResult.builder().code(JsonResult.FAIL).msg("查无该用户").build();
        }
        property.setUsername(propertyUpdateForm.getUsername());
        property.setPhone(propertyUpdateForm.getPhone());
        property.setAddress(propertyUpdateForm.getAddress());
        if(!propertyService.updateById(property)){
            return JsonResult.builder().code(JsonResult.FAIL).msg("修改失败，请稍后重试").build();
        }
        return JsonResult.builder().build();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping("/deleteProperty")
    public JsonResult removeProperty(@RequestBody String userId){//
        System.out.println(userId);
        long propertyId = Long.valueOf(userId.replaceAll("=", ""));
        Property property = propertyService.getById(propertyId);
        if (ObjectUtil.isNull(property)) {
            return JsonResult.builder().code(JsonResult.FAIL).msg("该用户已经被删除").build();
        }
        PropertyHistory propertyHistory = new PropertyHistory();
        BeanUtil.copyProperties(property, propertyHistory);
        propertyHistory.setRemoveTime(LocalDateTime.now());
        if (!propertyService.removeById(property.getUserId())) {
            return JsonResult.builder().code(JsonResult.FAIL).msg("删除用户失败").build();
        }
        if (!propertyHistoryService.save(propertyHistory)) {
            return JsonResult.builder().code(JsonResult.FAIL).msg("保存失效用户信息失败").build();
        }
        return JsonResult.builder().build();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping("/changePassword")
    public JsonResult changePassword(@RequestBody PasswordForm passwordForm){
        if(this.checkPasswordForm(passwordForm).getCode().equals(JsonResult.FAIL)){
            return this.checkPasswordForm(passwordForm);
        }
        Property property = propertyService.getById(passwordForm.getUserId());
        if(ObjectUtil.isNull(property)){
            return JsonResult.builder().code(JsonResult.FAIL).msg("查找不到该用户").build();
        }
        if(property.getPassword().equals(passwordForm.getPassword())){
            return JsonResult.builder().code(JsonResult.FAIL).msg("新密码不能和旧密码一致").build();
        }
        property.setPassword(passwordForm.getPassword());
        if(!propertyService.updateById(property)){
            JsonResult.builder().code(JsonResult.FAIL).msg("修改密码失败").build();
        }
        return JsonResult.builder().build();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping("/quitList")
    public JsonResult quitList(){
        List<PropertyHistory> propertyHistoryList = propertyHistoryService.QuitList();
        List<PropertyHistoryVo> historyVos = new ArrayList<>();
        propertyHistoryList.stream().forEach(item->{
            PropertyHistoryVo propertyHistoryVo = new PropertyHistoryVo();
            BeanUtil.copyProperties(item,propertyHistoryVo);
            if (item.getUserType().equals(1)) {
                propertyHistoryVo.setUserType("物业管理员");
            }else{
                propertyHistoryVo.setUserType("普通员工");
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            propertyHistoryVo.setCreateTime(formatter.format(item.getCreateTime()));
            propertyHistoryVo.setRemoveTime(formatter.format(item.getRemoveTime()));
            historyVos.add(propertyHistoryVo);
        });
        return JsonResult.builder().data(historyVos).build();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping("/countQuitMonth")
    public JsonResult countQuitMonthList(){
        Date startTime = DateUtil.getStartDayOfMonth(new Date());
        String monthKey = "count:quitMonth";
        List<String> monthList = new ArrayList<>();
        if(redisTemplate.hasKey(monthKey)){
             monthList = (List<String>)redisTemplate.opsForList().range(monthKey,0,-1).get(0);
        }
        return JsonResult.builder().data(monthList).build();
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping("/countQuitNum")
    public JsonResult countQuitNumList(){
        Date startTime = DateUtil.getStartDayOfMonth(new Date());
        String numKey = "count:quitNum";
        List<Integer> numList = new ArrayList<>();
        if( redisTemplate.hasKey(numKey)){
              numList =  (List<Integer>)redisTemplate.opsForList().range(numKey,0,-1).get(0);
        }
        return JsonResult.builder().data(numList).build();
    }

    /**
     * 修改密码前置判断
     * @param passwordForm
     * @return
     */
    public JsonResult checkPasswordForm(PasswordForm passwordForm){
        if(passwordForm.getPassword() == ""){
            return JsonResult.builder().code(JsonResult.FAIL).msg("密码不能为空").build();
        }
        if(!passwordForm.getPassword().equals(passwordForm.getCheckPassword())){
            return JsonResult.builder().code(JsonResult.FAIL).msg("两次密码不相同").build();
        }
        return JsonResult.builder().build();
    }
}



