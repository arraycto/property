package com.mlb.orderserviceconsumer.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.mlb.userserviceprovider.common.JsonResult;
import com.mlb.userserviceprovider.domain.Bill;
import com.mlb.userserviceprovider.domain.Member;
import com.mlb.userserviceprovider.domain.form.BillForm;
import com.mlb.userserviceprovider.domain.vo.BillQuery;
import com.mlb.userserviceprovider.service.BillService;
import com.mlb.userserviceprovider.service.MemberService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    @Reference
    private MemberService memberService;

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
        if(!billService.save(bill)){
            return JsonResult.builder().code(JsonResult.FAIL).msg(JsonResult.FAIL_MSG).build();
        }
        return JsonResult.builder().data(bill).build();
    }

    /**
     * 支付账单
     * @return
     */
    @CrossOrigin
    @PostMapping("/confirm")
    @ResponseBody
    public JsonResult confirmPayment(String userId,String homeId){
        Bill bill = billService.confirmBill(userId, homeId);
        if (ObjectUtil.isNull(bill)) {
            return JsonResult.builder().code(JsonResult.FAIL).msg("账单已失效").build();
        }
        bill.setPayed(1);
        bill.setPayTime(LocalDateTime.now());
        if (!billService.updateById(bill)) {
            return JsonResult.builder().code(JsonResult.FAIL).msg("账单支付失败").build();
        }
        return JsonResult.builder().data(bill).build();
    }

    /**
     * 返回账单列表
     * @param billQuery
     * @return
     */
    @CrossOrigin
    @PostMapping("/list")
    @ResponseBody
    public JsonResult billList(BillQuery billQuery){
        if (ObjectUtil.isNotNull(billQuery.getPhone())) {
            Member member = memberService.memberPhone(billQuery.getPhone());
            if (ObjectUtil.isNull(member)) {
                return JsonResult.builder().build();
            }
            billQuery.setUserId(String.valueOf(member.getUserId()));
        }
        List<Bill> billList = billService.billList(billQuery);
        return JsonResult.builder().data(billList).build();
    }

}

