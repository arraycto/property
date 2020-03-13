package com.mlb.orderserviceconsumer.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mlb.userserviceprovider.common.JsonResult;
import com.mlb.userserviceprovider.common.MemberStatus;
import com.mlb.userserviceprovider.common.SnowFlakeIdUtils;
import com.mlb.userserviceprovider.domain.Home;
import com.mlb.userserviceprovider.domain.Member;
import com.mlb.userserviceprovider.domain.MemberViolation;
import com.mlb.userserviceprovider.domain.Propertyhome;
import com.mlb.userserviceprovider.domain.form.MemberForm;
import com.mlb.userserviceprovider.domain.form.MemberViolationForm;
import com.mlb.userserviceprovider.domain.form.UnitFloor;
import com.mlb.userserviceprovider.domain.vo.MemberQueryVo;
import com.mlb.userserviceprovider.domain.vo.MemberVo;
import com.mlb.userserviceprovider.service.HomeService;
import com.mlb.userserviceprovider.service.MemberService;
import com.mlb.userserviceprovider.service.MemberViolationService;
import com.mlb.userserviceprovider.service.PropertyhomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
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

    @Reference
    private MemberViolationService memberViolationService;

    private Logger logger = LoggerFactory.getLogger(MemberController.class);

    /**
     * 添加业主或者租户
     *
     * @param memberForm
     * @return
     */
    @CrossOrigin
    @ResponseBody
    @PostMapping("/addMember")
    public JsonResult addMember(@RequestBody MemberForm memberForm) {
        logger.info("{}添加住户",new Date());
        Member member = new Member();
        BeanUtil.copyProperties(memberForm, member);
        member.setCreateTime(LocalDateTime.now());
        Long homeId = homeService.roomNumberByHomeId(memberForm.getUnit(), memberForm.getFloor(), memberForm.getRoom());
        if (ObjectUtil.isNull(homeId)) {
            return JsonResult.builder().code(JsonResult.FAIL).msg("没有这个房屋").build();
        }
        //运用雪花算法生成用户ID（唯一）
        SnowFlakeIdUtils snowFlakeIdUtils = new SnowFlakeIdUtils(9, 1);
        member.setUserId(snowFlakeIdUtils.nextId());
        if (JsonResult.FAIL.equals(this.checkHomeId(String.valueOf(homeId)).getCode())) {
            return this.checkHomeId(String.valueOf(homeId));
        }
        Propertyhome propertyhome = new Propertyhome();
        propertyhome.setHomeId(homeId);
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
        memberService.save(member);
        logger.info("{}添加住户成功",new Date());
        return JsonResult.builder().data(member).build();
    }


    /**
     * 判断房产信息能否添加给用户
     * @param homeId
     * @return
     */
    private JsonResult checkHomeId(String homeId){
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
    public JsonResult memberList(@RequestBody(required = false) MemberQueryVo memberQueryVo){
        List<Member> members = memberService.memberList(memberQueryVo);
        List<MemberVo> memberVos = new ArrayList<>();
        members.stream().forEach(item -> {
            MemberVo memberVo = new MemberVo();
            BeanUtil.copyProperties(item, memberVo);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            memberVo.setCreateTime(formatter.format(item.getCreateTime()));
            memberVo.setRemoved(MemberStatus.getStatus(item.getRemoved()).getDetail());
            List<Propertyhome> propertyHomes = propertyhomeService.memberHome(item.getUserId());
            if (item.getUserType().equals(1)) {
                memberVo.setUserType("业主");
                memberVo.setLeaseDuration("-");
            } else {
                memberVo.setUserType("租户");
                memberVo.setLeaseDuration(String.valueOf(propertyHomes.get(0).getLeaseDuration()));
            }
            Home home = homeService.getById(propertyHomes.get(0).getHomeId());
            memberVo.setHomeId(home.getHomeId());
            memberVo.setHome(home.getUnit() + "单元" + home.getFloor() + "楼" + home.getRoom() + "室");
            memberVos.add(memberVo);
        });
        return JsonResult.builder().data(memberVos).build();
    }

    /**
     * 解除房产信息合同，用户离去
     * @param memberVo
     * @return
     */
    @CrossOrigin
    @ResponseBody
    @PostMapping("/delete")
    public JsonResult deleteMember(@RequestBody MemberVo memberVo){
        logger.info("{}，删除的住户id为{},房产信息id为{}",new Date(),memberVo.getUserId(),memberVo.getHomeId());
        Member member = memberService.getById(memberVo.getUserId());
        if (ObjectUtil.isNull(member) || member.getRemoved().equals(MemberStatus.LEAVE.getCode())){
            return JsonResult.builder().code(JsonResult.FAIL).msg("该用户已经离开").build();
        }
        Propertyhome propertyhome = propertyhomeService.propertyhome(String.valueOf(memberVo.getUserId()),String.valueOf(memberVo.getHomeId()));
        if(ObjectUtil.isNotNull(propertyhome)){
            propertyhome.setDeleted(1);
            propertyhome.setEndTime(LocalDateTime.now());
            try{
                propertyhomeService.updateById(propertyhome);
                logger.info("{}，{}和{}的合同解除成功",new Date(),memberVo.getUserId(),memberVo.getHomeId());
            }catch (Exception e){
                logger.error("{},{}和{}的合同解除失败，异常{}",new Date(),memberVo.getUserId(),memberVo.getHomeId(),e.getMessage());
            }
        }
        List<Propertyhome> propertyHomes = propertyhomeService.propertyList(String.valueOf(memberVo.getUserId()));
        if(propertyHomes.size() > 0){
            return JsonResult.builder().code(JsonResult.FAIL).msg("该用户没有完全解除房产信息合同，不能删除").build();
        }
        member.setRemoved(MemberStatus.LEAVE.getCode());
        member.setRemoveTime(LocalDateTime.now());
        if(!memberService.updateById(member)){
            logger.error("{}，id:{}的用户删除失败",new Date(),memberVo.getUserId());
            return JsonResult.builder().code(JsonResult.FAIL).msg("异常处理，删除失败").build();
        }
        logger.info("{},id：{}的用户删除成功，合同解除完成",new Date(),memberVo.getUserId());
        return JsonResult.builder().data(member).build();
    }

    /**
     * 违规用户的处罚
     * @param form
     * @return
     */
    @CrossOrigin
    @ResponseBody
    @PostMapping("/violation")
    public JsonResult violationMember(@RequestBody MemberViolationForm form){
        MemberViolation memberViolation = new MemberViolation();
        BeanUtil.copyProperties(form,memberViolation);
        Member member = memberService.getById(memberViolation.getUserId());
        if(ObjectUtil.isNull(member) || member.getRemoved().equals(MemberStatus.LEAVE.getCode())){
            return JsonResult.builder().code(JsonResult.FAIL).msg("查无该用户或该用户已经离开").build();
        }
        //运用雪花算法生成用户ID（唯一）
        SnowFlakeIdUtils snowFlakeIdUtils = new SnowFlakeIdUtils(9, 1);
        memberViolation.setViolationId(snowFlakeIdUtils.nextId());
        memberViolation.setUsername(member.getUsername());
        memberViolation.setCreateTime(LocalDateTime.now());
        memberViolation.setUpdateTime(LocalDateTime.now());
        switch (memberViolation.getPunish()){
            case 1: member.setRemoved(MemberStatus.WATER_CUT_OFF.getCode());break;
            case 2: member.setRemoved(MemberStatus.POWER_FAILURE.getCode());break;
            default:member.setRemoved(MemberStatus.STOP_WATER_POWER.getCode());
        }
        memberService.updateById(member);
        //处罚时将之前的处罚记录覆盖，deleted改为删除
        List<MemberViolation> memberViolations = memberViolationService.listByUserId(member.getUserId());
        memberViolations.stream().forEach(item->{
            item.setDeleted(2);
            item.setUpdateTime(LocalDateTime.now());
            memberViolationService.updateById(item);
        });
        logger.info("{},开始存储用户id:{}的处罚信息", new Date(), memberViolation.getUserId());
        if(!memberViolationService.save(memberViolation)) {
            logger.error("{},存储用户id:{}的处罚信息失败", new Date(), memberViolation.getUserId());
            return JsonResult.builder().code(JsonResult.FAIL).msg(JsonResult.FAIL_MSG).build();
        }
        logger.error("{},存储用户id:{}的处罚信息结束", new Date(),memberViolation.getUserId());
        return JsonResult.builder().build();
    }

    /**
     * 返回所有单元号
     * @return
     */
    @CrossOrigin
    @PostMapping("/unitList")
    @ResponseBody
    public JsonResult unitList(){
        List<Integer> unit = homeService.unitList();
        return JsonResult.builder().data(unit).build();
    }

    /**
     * 返回所有楼层号
     * @param unit
     * @return
     */
    @CrossOrigin
    @PostMapping("/floorList")
    @ResponseBody
    public JsonResult floorList(@RequestBody(required = false)String unit){
        List<Integer> floors = homeService.floorList(unit);
        return JsonResult.builder().data(floors).build();
    }


    /**
     * 返回所有房间号
     * Param UnitFloor
     * @return
     */
    @CrossOrigin
    @PostMapping("/roomList")
    @ResponseBody
    public JsonResult roomList(@RequestBody(required = false)UnitFloor unitFloor){
        List<Integer> rooms = homeService.roomList(unitFloor.getUnit(),unitFloor.getFloor());
        return JsonResult.builder().data(rooms).build();
    }
}

