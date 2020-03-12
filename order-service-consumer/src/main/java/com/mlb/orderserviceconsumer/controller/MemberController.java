package com.mlb.orderserviceconsumer.controller;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.dubbo.config.annotation.Reference;
import com.mlb.userserviceprovider.common.JsonResult;
import com.mlb.userserviceprovider.common.MemberStatus;
import com.mlb.userserviceprovider.common.SnowFlakeIdUtils;
import com.mlb.userserviceprovider.domain.Home;
import com.mlb.userserviceprovider.domain.Member;
import com.mlb.userserviceprovider.domain.Propertyhome;
import com.mlb.userserviceprovider.domain.form.MemberForm;
import com.mlb.userserviceprovider.domain.form.UnitFloor;
import com.mlb.userserviceprovider.domain.vo.MemberQueryVo;
import com.mlb.userserviceprovider.domain.vo.MemberVo;
import com.mlb.userserviceprovider.service.HomeService;
import com.mlb.userserviceprovider.service.MemberService;
import com.mlb.userserviceprovider.service.PropertyhomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    public JsonResult addMember(MemberForm memberForm) {
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
        members.stream().forEach(item-> {
            MemberVo memberVo = new MemberVo();
            BeanUtil.copyProperties(item,memberVo);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            memberVo.setCreateTime(formatter.format(item.getCreateTime()));
            memberVo.setCreateTime(formatter.format(item.getCreateTime()));
            memberVo.setRemoved(MemberStatus.getStatus(item.getRemoved()).getDetail());
            List<Propertyhome> propertyHomes = propertyhomeService.propertyList(String.valueOf(item.getUserId()));
            if(item.getUserType().equals(1)){
                memberVo.setUserType("业主");
                memberVo.setLeaseDuration("-");
            }else{
                memberVo.setUserType("租户");
                memberVo.setLeaseDuration(String.valueOf(propertyHomes.get(0).getLeaseDuration()));
            }
            Home home = homeService.getById(propertyHomes.get(0).getHomeId());
            memberVo.setHomeId(home.getHomeId());
            memberVo.setHome(home.getUnit()+"单元"+home.getFloor()+"楼"+home.getRoom()+"室");
            memberVos.add(memberVo);
        });
        return JsonResult.builder().data(memberVos).build();
    }

    /**
     * 解除房产信息合同
     * @param userId
     * @param homeId
     * @return
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
        logger.info("单元号选择为:{}",unit);
        List<Integer> floors = homeService.floorList(unit);
        return JsonResult.builder().data(floors).build();
    }


    /**
     * 返回所有房间号
     * @param unit
     * @Param floor
     * @return
     */
    @CrossOrigin
    @PostMapping("/roomList")
    @ResponseBody
    public JsonResult roomList(@RequestBody(required = false)UnitFloor unitFloor){
        logger.info("单元号选择为:{} 楼层选择为:{}",unitFloor.getUnit(),unitFloor.getFloor());
        List<Integer> rooms = homeService.roomList(unitFloor.getUnit(),unitFloor.getFloor());
        return JsonResult.builder().data(rooms).build();
    }
}

