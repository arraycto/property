package com.mlb.orderserviceconsumer.controller;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mlb.userserviceprovider.common.JsonResult;
import com.mlb.userserviceprovider.common.TaskEnum;
import com.mlb.userserviceprovider.common.TokenUse;
import com.mlb.userserviceprovider.common.UserTypeEnum;
import com.mlb.userserviceprovider.domain.PropertyTask;
import com.mlb.userserviceprovider.domain.PropertyUser;
import com.mlb.userserviceprovider.service.PropertyTaskService;
import com.mlb.userserviceprovider.service.PropertyUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


/**
 * @author mlb
 */
@Controller
@RequestMapping("/worker")
public class PropertyWorkerTaskController {

    @Reference
    private PropertyTaskService propertyTaskService;

    @Reference
    private PropertyUserService propertyUserService;

    /**
     * 维修工接受用户提出的需求
     * @param taskId
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/accept")
    public JsonResult acceptWork(Long taskId,HttpServletRequest request){
        JsonResult jsonResult = this.loginUser(request);
        if(jsonResult.getCode().equals(JsonResult.FAIL)){
            return jsonResult;
        }
        PropertyTask propertyTask = propertyTaskService.getById(taskId);
        if(ObjectUtil.isNull(propertyTask)){
            return JsonResult.builder().code(JsonResult.FAIL).msg("用户已经取消了该需求").build();
        }
        if(!propertyTask.getTaskStatus().equals(TaskEnum.PENDING_DISPOSAL.getCode())){
            return JsonResult.builder().code(JsonResult.FAIL).msg("这项工作已经有人在处理").build();
        }
        PropertyUser propertyUser = (PropertyUser)jsonResult.getData();
        propertyTask.setAcceptId(propertyUser.getUserId());
        propertyTask.setAcceptTime(new Date());
        propertyTask.setTaskStatus(TaskEnum.IN_PROCESSING.getCode());
        if(!propertyTaskService.updateById(propertyTask)){
            return JsonResult.builder().code(JsonResult.FAIL).msg(JsonResult.FAIL_MSG).build();
        }
        return JsonResult.builder().code(JsonResult.SUCCESS).msg(JsonResult.SUCCESS_MSG).data(propertyTask).build();
    }

    /**
     * 维修工查看自己接下的工作需求
     * @param page
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/list")
    public JsonResult workList(Page page,HttpServletRequest request){
        JsonResult jsonResult = this.loginUser(request);
        if(jsonResult.getCode().equals(JsonResult.FAIL)){
            return jsonResult;
        }
        PropertyUser propertyUser = (PropertyUser)jsonResult.getData();
        IPage<PropertyTask> propertyTaskIPage = propertyTaskService.findWorkList(page,propertyUser.getUserId());
        if(ObjectUtil.isNull(propertyTaskIPage)){
            return JsonResult.builder().code(JsonResult.FAIL).msg(JsonResult.FAIL_MSG).build();
        }
        return JsonResult.builder().code(JsonResult.SUCCESS).msg(JsonResult.SUCCESS_MSG).data(propertyTaskIPage).build();
    }

    /**
     * 完成需求，等待验收
     * @param taskId
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/finish")
    public JsonResult finishWork(Long taskId,HttpServletRequest request){
        JsonResult jsonResult = this.loginUser(request);
        if(jsonResult.getCode().equals(JsonResult.FAIL)){
            return jsonResult;
        }
        PropertyTask propertyTask = propertyTaskService.getById(taskId);
        PropertyUser propertyUser = (PropertyUser)jsonResult.getData();
        if(!propertyTask.getTaskStatus().equals(TaskEnum.IN_PROCESSING.getCode())){
            return JsonResult.builder().code(JsonResult.FAIL).msg("该需求尚未有人处理或已经处理完成").build();
        }
        if(!propertyTask.getAcceptId().equals(propertyUser.getUserId())){
            return JsonResult.builder().code(JsonResult.FAIL).msg("这不是你处理的需求").build();
        }
        propertyTask.setTaskStatus(TaskEnum.PENDING_ACCEPTANCE.getCode());
        propertyTask.setCompleteTime(new Date());
        if(!propertyTaskService.updateById(propertyTask)){
            return JsonResult.builder().code(JsonResult.FAIL).msg(JsonResult.FAIL_MSG).build();
        }
        return JsonResult.builder().code(JsonResult.SUCCESS).msg(JsonResult.SUCCESS_MSG).data(propertyTask).build();
    }

    /**
     * 维修工取消任务
     * @param taskId
     * @param request
     * @return
     */
    @ResponseBody
    @PostMapping("/cancel")
    public JsonResult cancelWork(Long taskId,HttpServletRequest request){
        JsonResult jsonResult = this.loginUser(request);
        if(jsonResult.getCode().equals(JsonResult.FAIL)){
            return jsonResult;
        }
        PropertyUser propertyUser = (PropertyUser)jsonResult.getData();
        PropertyTask propertyTask = propertyTaskService.getById(taskId);
        if(!propertyTask.getTaskStatus().equals(TaskEnum.IN_PROCESSING.getCode())){
            return JsonResult.builder().code(JsonResult.FAIL).msg("无法取消").build();
        }
        if(!propertyTask.getAcceptId().equals(propertyUser.getUserId())){
            return JsonResult.builder().code(JsonResult.FAIL).msg("您无权取消他人接下的任务").build();
        }
        propertyTask.setAcceptId(null);
        propertyTask.setAcceptTime(null);
        propertyTask.setTaskStatus(TaskEnum.PENDING_DISPOSAL.getCode());
        if(!propertyTaskService.updateById(propertyTask)){
            return JsonResult.builder().code(JsonResult.FAIL).msg(JsonResult.FAIL_MSG).build();
        }
        return JsonResult.builder().code(JsonResult.SUCCESS).msg(JsonResult.SUCCESS_MSG).data(propertyTask).build();
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
        if(!propertyUser.getUserType().equals(UserTypeEnum.WORKER.getCode())){
            return JsonResult.builder().code(JsonResult.FAIL).msg("对不起，您没有这个权限").build();
        }
        return JsonResult.builder().code(JsonResult.SUCCESS).data(propertyUser).build();
    }
}
