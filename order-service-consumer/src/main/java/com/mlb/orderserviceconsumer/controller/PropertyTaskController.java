package com.mlb.orderserviceconsumer.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mlb.userserviceprovider.common.*;
import com.mlb.userserviceprovider.domain.PropertyTask;
import com.mlb.userserviceprovider.domain.PropertyUser;
import com.mlb.userserviceprovider.domain.form.PropertyTaskForm;
import com.mlb.userserviceprovider.domain.vo.PropertyTaskVo;
import com.mlb.userserviceprovider.service.PropertyTaskService;
import com.mlb.userserviceprovider.service.PropertyUserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mlb
 * @since 2019-11-20
 */
@Controller
@RequestMapping("/propertyTask")
public class PropertyTaskController {

    @Reference
    private PropertyUserService propertyUserService;

    @Reference
    private PropertyTaskService propertyTaskService;

    /**
     * 住户或者管理员提出需求
     * @param propertyTaskForm
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/add")
    public JsonResult addTask(@Validated PropertyTaskForm propertyTaskForm, HttpServletRequest request){
        JsonResult jsonResult = this.loginUser(request);
        if (jsonResult.getCode().equals(JsonResult.FAIL)) {
            return jsonResult;
        }
        PropertyUser propertyUser = (PropertyUser) jsonResult.getData();
        PropertyTask propertyTask = new PropertyTask();
        BeanUtil.copyProperties(propertyTaskForm, propertyTask);
        //运用雪花算法生成唯一的ID
        SnowFlakeIdUtils snowFlakeIdUtils = new SnowFlakeIdUtils(9, 1);
        propertyTask.setTaskId(snowFlakeIdUtils.nextId());

        Date date = new Date();
        propertyTask.setCreateTime(date);
        propertyTask.setCreateId(propertyUser.getUserId());
        propertyTask.setCreateName(propertyUser.getUsername());
        if (!propertyTaskService.save(propertyTask)) {
            return JsonResult.builder().code(JsonResult.FAIL).msg(JsonResult.FAIL_MSG).build();
        }
        return JsonResult.builder().code(JsonResult.SUCCESS).msg(JsonResult.SUCCESS_MSG).data(propertyTask).build();
    }

    /**
     * 查看需求列表
     * @param page
     * @param propertyTaskVo
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/list")
    public JsonResult pageList(Page page, PropertyTaskVo propertyTaskVo,HttpServletRequest request){
        String Token = request.getHeader("accessToken");
        Long userId = TokenUse.getUserID(Token);
        PropertyUser propertyUser = propertyUserService.getById(userId);
        propertyTaskVo.setUserId(propertyUser.getUserId());
        propertyTaskVo.setUserType(propertyUser.getUserType());
        IPage<PropertyTask> propertyTaskIPage = propertyTaskService.findPageList(page, propertyTaskVo);
        if (ObjectUtil.isNull(propertyTaskIPage)) {
            return JsonResult.builder().code(JsonResult.FAIL).msg(JsonResult.FAIL_MSG).build();
        }
        return JsonResult.builder().code(JsonResult.SUCCESS).msg(JsonResult.SUCCESS_MSG).data(propertyTaskIPage).build();
    }

    /**
     * 验收
     * @param taskId
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/confirm")
    public JsonResult confirmTask(Long taskId,HttpServletRequest request){
        JsonResult jsonResult = this.loginUser(request);
        if (jsonResult.getCode().equals(JsonResult.FAIL)) {
            return jsonResult;
        }
        PropertyTask propertyTask = propertyTaskService.getById(taskId);
        PropertyUser propertyUser = (PropertyUser) jsonResult.getData();
        if (!propertyTask.getCreateId().equals(propertyUser.getUserId())) {
            return JsonResult.builder().code(JsonResult.FAIL).msg("这个需求不是您提出的，您无权验收").build();
        }
        if (!propertyTask.getTaskStatus().equals(TaskEnum.PENDING_ACCEPTANCE.getCode())) {
            return JsonResult.builder().code(JsonResult.FAIL).msg("这个需求尚未完成").build();
        }
        propertyTask.setTaskStatus(TaskEnum.COMPLETED.getCode());
        propertyTask.setAcceeptanceTime(new Date());
        if (!propertyTaskService.updateById(propertyTask)) {
            return JsonResult.builder().code(JsonResult.FAIL).msg(JsonResult.FAIL_MSG).build();
        }
        return JsonResult.builder().code(JsonResult.SUCCESS).msg(JsonResult.SUCCESS_MSG).data(propertyTask).build();
    }

    /**
     * 取消需求
     * @param taskId
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/cancel")
    public JsonResult cancelTask(Long taskId,HttpServletRequest request){
        JsonResult jsonResult = this.loginUser(request);
        if (jsonResult.getCode().equals(JsonResult.FAIL)) {
            return jsonResult;
        }
        PropertyTask propertyTask = propertyTaskService.getById(taskId);
        PropertyUser propertyUser = (PropertyUser) jsonResult.getData();
        if(ObjectUtil.isNull(propertyTask)){
            return JsonResult.builder().code(JsonResult.FAIL).msg("您已经取消了该需求").build();
        }
        if (propertyTask.getAcceptId() != null) {
            return JsonResult.builder().code(JsonResult.FAIL).msg("对不起，已经有人接下需求，无法取消").build();
        }
        if (!propertyTask.getCreateId().equals(propertyUser.getUserId())) {
            return JsonResult.builder().code(JsonResult.FAIL).msg("您不能取消别人的需求").build();
        }
        if (!propertyTaskService.removeById(taskId)) {
            return JsonResult.builder().code(JsonResult.FAIL).msg("取消失败").build();
        }
        return JsonResult.builder().code(JsonResult.FAIL).msg(JsonResult.SUCCESS_MSG).build();
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
        if (ObjectUtil.isNull(propertyUser)) {
            return JsonResult.builder().code(JsonResult.FAIL).msg("登陆失败").build();
        }
        if (propertyUser.getUserType().equals(UserTypeEnum.WORKER.getCode())) {
            return JsonResult.builder().code(JsonResult.FAIL).msg("对不起，您还不是这里的住户").build();
        }
        return JsonResult.builder().code(JsonResult.SUCCESS).data(propertyUser).build();
    }

}

