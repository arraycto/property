package com.mlb.orderserviceconsumer.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.mlb.userserviceprovider.common.JsonResult;
import com.mlb.userserviceprovider.common.SnowFlakeIdUtils;
import com.mlb.userserviceprovider.domain.Home;
import com.mlb.userserviceprovider.domain.Member;
import com.mlb.userserviceprovider.domain.Propertyhome;
import com.mlb.userserviceprovider.domain.form.MemberForm;
import com.mlb.userserviceprovider.domain.vo.MemberQueryVo;
import com.mlb.userserviceprovider.service.HomeService;
import com.mlb.userserviceprovider.service.MemberService;
import com.mlb.userserviceprovider.service.PropertyhomeService;
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
 * @since 2020-02-19
 */
@Controller
@RequestMapping("/member")
public class MemberController {

    @Reference
    private MemberService memberService;

    @Reference
    private HomeService homeService;

    @Reference
    private PropertyhomeService propertyhomeService;

    //添加业主或者租户
    @CrossOrigin
    @ResponseBody
    @PostMapping("/addMember")
    public JsonResult addMember(MemberForm memberForm){
        Member member = new Member();
        BeanUtil.copyProperties(memberForm, member);
        member.setCreateTime(LocalDateTime.now());
        //运用雪花算法生成用户ID（唯一）
        SnowFlakeIdUtils snowFlakeIdUtils = new SnowFlakeIdUtils(9, 1);
        member.setUserId(snowFlakeIdUtils.nextId());
        if (JsonResult.FAIL.equals(this.checkHomeId(memberForm.getHomeId()).getCode())) {
            return this.checkHomeId(memberForm.getHomeId());
        }
        Propertyhome propertyhome = new Propertyhome();
        propertyhome.setHomeId(Long.valueOf(memberForm.getHomeId()));
        propertyhome.setUserId(member.getUserId());
        propertyhome.setPhone(member.getPhone());
        propertyhome.setCreateTime(LocalDateTime.now());
        if (member.getUserType().equals(1)) {
            propertyhome.setType(0);
        } else {
            propertyhome.setType(1);
            propertyhome.setLeaseDuration(memberForm.getLeaseDuration());
        }
        propertyhomeService.save(propertyhome);
        if (!memberService.save(member)) {
            return JsonResult.builder().code(JsonResult.FAIL).msg(JsonResult.FAIL_MSG).build();
        }
        return JsonResult.builder().data(member).build();
    }


    //判断房产信息能否添加给用户
    public JsonResult checkHomeId(String homeId){
        Home home = homeService.getById(homeId);
        if (ObjectUtil.isNull(home) || home.getDeleted() == 1) {
            return JsonResult.builder().code(JsonResult.FAIL).msg("参数错误，未找到房产信息").build();
        }
        List<Propertyhome> propertyhomes = propertyhomeService.propertyHomeList(homeId);
        if (propertyhomes.size() > 0) {
            return JsonResult.builder().code(JsonResult.FAIL).msg("房屋有人居住，暂时无法出售或出租").build();
        }
        return JsonResult.builder().build();
    }

    /**
     * 返回住户列表
     * @param memberQueryVo
     * @return
     */
    @CrossOrigin
    @ResponseBody
    @PostMapping("/list")
    public JsonResult memberList(MemberQueryVo memberQueryVo){
        List<Member> members = memberService.memberList(memberQueryVo);
        if(members.size() <= 0){
            return JsonResult.builder().code(JsonResult.FAIL).msg("暂无数据").build();
        }
        return JsonResult.builder().data(members).build();
    }

    /*
       解除房产信息合同
    */
    @CrossOrigin
    @ResponseBody
    @PostMapping("/terminate")
    public JsonResult terminate(String userId,String homeId){
        Propertyhome propertyhome = propertyhomeService.propertyhome(userId,homeId);
        if(ObjectUtil.isNull(propertyhome)){
            return JsonResult.builder().code(JsonResult.FAIL).msg("合同信息已解除").build();
        }
        propertyhome.setDeleted(1);
        propertyhome.setEndTime(LocalDateTime.now());
        if(!propertyhomeService.updateById(propertyhome)){
            return JsonResult.builder().code(JsonResult.FAIL).msg("合同信息解除失败").build();
        }
        return JsonResult.builder().data(propertyhome).build();
    }

    /**
     * 住户离去
     * @param userId
     * @return
     */
    @CrossOrigin
    @ResponseBody
    @PostMapping("/delete")
    public JsonResult deleteMember(String userId){
        Member member = memberService.getById(userId);
        if (ObjectUtil.isNull(member) || member.getRemoved() == 2){
            return JsonResult.builder().code(JsonResult.FAIL).msg("该用户已经离开").build();
        }
        List<Propertyhome> propertyhomes = propertyhomeService.propertyList(userId);
        if(propertyhomes.size() > 0){
            return JsonResult.builder().code(JsonResult.FAIL).msg("该用户未完全解除和房产信息之间的合同，无法删除").build();
        }
        member.setRemoved(2);
        member.setRemoveTime(LocalDateTime.now());
        if(!memberService.updateById(member)){
            return JsonResult.builder().code(JsonResult.FAIL).msg(JsonResult.FAIL_MSG).build();
        }
        return JsonResult.builder().data(member).build();
    }
}

