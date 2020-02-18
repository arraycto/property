package com.mlb.orderserviceconsumer.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.mlb.userserviceprovider.common.JsonResult;
import com.mlb.userserviceprovider.common.SnowFlakeIdUtils;
import com.mlb.userserviceprovider.domain.Home;
import com.mlb.userserviceprovider.domain.form.HomeForm;
import com.mlb.userserviceprovider.domain.vo.HomeQueryVo;
import com.mlb.userserviceprovider.service.HomeService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mlb
 * @since 2020-02-17
 */
@Controller
@RequestMapping("/home")
public class HomeController {

    @Reference
    private HomeService homeService;

    @CrossOrigin
    @PostMapping("/saveHome")
    @ResponseBody
    public JsonResult addHome(HomeForm homeForm){
        Home home = new Home();
        BeanUtil.copyProperties(homeForm,home);
        home.setCreateTime(LocalDateTime.now());
        //运用雪花算法生成唯一id
        SnowFlakeIdUtils snowFlakeIdUtils = new SnowFlakeIdUtils(9,1);
        home.setHomeId(snowFlakeIdUtils.nextId());
        if(!homeService.save(home)){
            return JsonResult.builder().code(JsonResult.FAIL).msg(JsonResult.FAIL_MSG).build();
        }
        return JsonResult.builder().data(home).build();
    }

    @CrossOrigin
    @PostMapping("/list")
    @ResponseBody
    public JsonResult homeList(HomeQueryVo homeQueryVo){
        List<Home> homeList = homeService.homeList(homeQueryVo);
        if(ObjectUtil.isNull(homeList)){
            return JsonResult.builder().code(JsonResult.FAIL).msg("暂无房源信息").build();
        }
        return JsonResult.builder().data(homeList).build();
    }

    @CrossOrigin
    @PostMapping("/remove")
    @ResponseBody
    public JsonResult deleteHome(String homeId){
        Home home = homeService.getById(homeId);
        if(ObjectUtil.isNull(home)){
            return JsonResult.builder().code(JsonResult.FAIL).msg("参数错误，查不到房产信息").build();
        }
        home.setDeleted(1);
        home.setDeleteTime(LocalDateTime.now());
        if(!homeService.updateById(home)){
            return JsonResult.builder().code(JsonResult.FAIL).msg(JsonResult.FAIL_MSG).build();
        }
        return JsonResult.builder().data(home).build();
    }

}

