package com.mlb.orderserviceconsumer.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.mlb.userserviceprovider.common.JsonResult;
import com.mlb.userserviceprovider.domain.Bill;
import com.mlb.userserviceprovider.domain.form.BillForm;
import com.mlb.userserviceprovider.service.BillService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mlb
 * @since 2020-02-24
 */
@Controller
@RequestMapping("/bill")
public class BillController {

    @Reference
    private BillService billService;

    /**
     * 添加账单
     * @return
     */
    @CrossOrigin
    @PostMapping("/add")
    @ResponseBody
    public JsonResult addBill(BillForm billForm){
        Bill bill = new Bill();
        BeanUtil.copyProperties(billForm,bill);
        LocalDateTime now = LocalDateTime.now();
        bill.setCreateTime(now);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月");
        String mouth = now.format(formatter);
        bill.setMonth(mouth);
        if(!billService.save(bill)){
            return JsonResult.builder().code(JsonResult.FAIL).msg(JsonResult.FAIL_MSG).build();
        }
        return JsonResult.builder().data(bill).build();
    }

    /**
     * 添加账单
     * @return
     */
    @CrossOrigin
    @PostMapping("/confirm")
    @ResponseBody
    public JsonResult confirmPayment(String userId,String homeId){
        Bill bill = billService.confirmBill(userId, homeId);
        if(ObjectUtil.isNull(bill)){
            return JsonResult.builder().code(JsonResult.FAIL).msg("账单已失效").build();
        }
        bill.setPayed(1);
        bill.setPayTime(LocalDateTime.now());
        if(!billService.updateById(bill)){
            return JsonResult.builder().code(JsonResult.FAIL).msg("账单支付失败").build();
        }
        return JsonResult.builder().data(bill).build();
    }

}

