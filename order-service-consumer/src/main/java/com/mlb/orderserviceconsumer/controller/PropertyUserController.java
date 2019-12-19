package com.mlb.orderserviceconsumer.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mlb.userserviceprovider.common.JsonResult;
import com.mlb.userserviceprovider.common.SnowFlakeIdUtils;
import com.mlb.userserviceprovider.common.TokenUse;
import com.mlb.userserviceprovider.common.UserTypeEnum;
import com.mlb.userserviceprovider.domain.PropertyUser;
import com.mlb.userserviceprovider.domain.form.LoginUser;
import com.mlb.userserviceprovider.domain.form.PropertyUserForm;
import com.mlb.userserviceprovider.domain.vo.PropertyUserVo;
import com.mlb.userserviceprovider.service.PropertyUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p>
 * 物业用户表 前端控制器
 * </p>
 *
 * @author mlb
 * @since 2019-11-19
 */
@Controller
@RequestMapping("/propertyUser")
public class PropertyUserController {

    @Reference
    private PropertyUserService propertyUserService;

    private Logger log = LoggerFactory.getLogger(PropertyUserController.class);

    /**
     * 添加用户
     * @param form
     * @return
     */
    @ResponseBody
    @RequestMapping("/save")
    public JsonResult addUser(@Validated PropertyUserForm form, HttpServletRequest request){
        JsonResult jsonResult = this.loginUser(request);
        if(jsonResult.getCode().equals(JsonResult.FAIL)){
            return jsonResult;
        }
        PropertyUser propertyUser = new PropertyUser();
        BeanUtil.copyProperties(form, propertyUser);
        Date date = new Date();
        propertyUser.setCreateTime(date);
        //运用雪花算法生成用户ID（唯一）
        SnowFlakeIdUtils snowFlakeIdUtils = new SnowFlakeIdUtils(9,1);
        propertyUser.setUserId(snowFlakeIdUtils.nextId());
        if (!propertyUserService.save(propertyUser)) {
            return JsonResult.builder().code(JsonResult.FAIL).msg(JsonResult.FAIL_MSG).build();
        }
        return JsonResult.builder().code(JsonResult.SUCCESS).msg(JsonResult.SUCCESS_MSG).data(propertyUser).build();
    }

    /**
     * 查看用户列表
     * @param page
     * @param propertyUserVo
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/list")
    public JsonResult userList(Page page, PropertyUserVo propertyUserVo,HttpServletRequest request){
        JsonResult jsonResult = this.loginUser(request);
        if(jsonResult.getCode().equals(JsonResult.FAIL)){
            return jsonResult;
        }
        IPage<PropertyUser> propertyUserIPage = propertyUserService.selectPageList(page,propertyUserVo);
        if(ObjectUtil.isNull(propertyUserIPage)){
            return JsonResult.builder().code(JsonResult.FAIL).msg(JsonResult.FAIL_MSG).build();
        }
        return JsonResult.builder().code(JsonResult.SUCCESS).msg(JsonResult.SUCCESS_MSG).data(propertyUserIPage).build();
    }

    /**
     * 修改用户
     * @param propertyUserVo
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/update")
    public JsonResult updateUser(PropertyUserVo propertyUserVo,HttpServletRequest request){
        JsonResult jsonResult = this.loginUser(request);
        if(jsonResult.getCode().equals(JsonResult.FAIL)){
            return jsonResult;
        }
        PropertyUser propertyUser = propertyUserService.getById(propertyUserVo.getUserId());
        if(ObjectUtil.isNull(propertyUser)){
            return JsonResult.builder().code(JsonResult.FAIL).msg("查不到该用户或该用户已经离开").build();
        }
        BeanUtil.copyProperties(propertyUserVo,propertyUser);
        if(!propertyUserService.updateById(propertyUser)){
            return JsonResult.builder().code(JsonResult.FAIL).msg(JsonResult.FAIL_MSG).build();
        }
        return JsonResult.builder().code(JsonResult.SUCCESS).msg(JsonResult.SUCCESS_MSG).data(propertyUser).build();
    }

    /**
     * 删除用户
     * @param userId
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/delete")
    public JsonResult delUser(Long userId,HttpServletRequest request){
        JsonResult jsonResult = this.loginUser(request);
        if(jsonResult.getCode().equals(JsonResult.FAIL)){
            return jsonResult;
        }
        PropertyUser propertyUser = propertyUserService.getById(userId);
        if(ObjectUtil.isNull(propertyUser)){
            return JsonResult.builder().code(JsonResult.FAIL).msg("查不到该用户或该用户已经离开").build();
        }
        if(propertyUser.getUserType().equals(UserTypeEnum.ADMIN.getCode())){
            return JsonResult.builder().code(JsonResult.FAIL).msg("您没有权限删除该用户").build();
        }
        if(!propertyUserService.removeById(userId)){
            return JsonResult.builder().code(JsonResult.FAIL).msg("删除失败").build();
        }
        return JsonResult.builder().code(JsonResult.SUCCESS).msg(JsonResult.SUCCESS_MSG).build();
    }

    /**
     * 登陆获取Token
     * @param loginUser
     * @return
     */
    @CrossOrigin
    @ResponseBody
    @PostMapping("/login")
    public JsonResult login(@RequestBody LoginUser loginUser){
        PropertyUser propertyUser = propertyUserService.propertyUserLogin(loginUser.getPhone(),loginUser.getPassword());
        if(ObjectUtil.isNull(propertyUser)){
            log.info("用户名或密码错误");
        }else{
           String token = TokenUse.sign(propertyUser.getPhone(),propertyUser.getUserId());
           if(token != null){
               return JsonResult.builder().code(JsonResult.SUCCESS).msg(JsonResult.SUCCESS_MSG).data(token).build();
           }
        }
        return JsonResult.builder().code(JsonResult.FAIL).msg("登陆失败").build();
    }

    /**
     * 判断登陆用户
     * @param request
     * @return
     */
    public JsonResult loginUser(HttpServletRequest request){
        String Token = request.getHeader("accessToken");
        Long userId = TokenUse.getUserID(Token);
        PropertyUser propertyUser = propertyUserService.getById(userId);
        if(ObjectUtil.isNull(propertyUser)){
            return JsonResult.builder().code(JsonResult.FAIL).msg("登陆失败").build();
        }
        if(!propertyUser.getUserType().equals(UserTypeEnum.ADMIN.getCode())){
            return JsonResult.builder().code(JsonResult.FAIL).msg("对不起，您没有这个权限").build();
        }
        return JsonResult.builder().code(JsonResult.SUCCESS).build();
    }
}

