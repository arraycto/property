package com.mlb.orderserviceconsumer.cron;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mlb.orderserviceconsumer.util.DateUtil;
import com.mlb.userserviceprovider.common.SnowFlakeIdUtils;
import com.mlb.userserviceprovider.domain.Member;
import com.mlb.userserviceprovider.domain.PropertyCountQuit;
import com.mlb.userserviceprovider.service.BillService;
import com.mlb.userserviceprovider.service.MemberService;
import com.mlb.userserviceprovider.service.PropertyCountQuitService;
import com.mlb.userserviceprovider.service.PropertyHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author mlb
 */
@Component
@Configuration
@EnableScheduling
public class BillJob {

    @Reference
    private BillService billService;
    @Reference
    private MemberService memberService;
    @Reference
    private PropertyHistoryService propertyHistoryService;
    @Reference
    private PropertyCountQuitService propertyCountQuitService;
    @Autowired
    private RedisTemplate redisTemplate;

    private Logger logger = LoggerFactory.getLogger(BillJob.class);

    /**
     * 统计欠费名单
     */
    @Scheduled(cron = "0 25 9 15/1 * ?  ")
    public void billMember(){
        Date deadline = new Date();
        logger.info("欠费人员名单统计开始");
        try {
            List<Long> userIdList = billService.arrearsMembers(deadline);
            userIdList.stream().forEach(item -> {
                Member member = memberService.getById(item);
                logger.info("欠费人员:"+member);
            });
            logger.info("欠费人员名单统计完成");
        }catch (Exception e){
            logger.info("欠费人员名单统计异常"+ e.toString());
        }
    }

//    @Scheduled(cron = "0/10 * * * * ?")
//    @Scheduled(cron = "* 30 23 L * ? ")
    public void quitList(){
        String monthKey = "count:quitMonth";
        String numKey = "count:quitNum";
        logger.info("开始统计本月离职人数");
        Date endTime = new Date();
        Date startTime = DateUtil.getStartDayOfMonth(endTime);
        try {
            PropertyCountQuit propertyCountQuit = new PropertyCountQuit();
            propertyCountQuit.setNum(propertyHistoryService.countQuitByTime(startTime, endTime));
            propertyCountQuit.setMonth(DateUtil.PreciseMonth(startTime));
            propertyCountQuit.setCreateTime(LocalDateTime.now());
            //运用雪花算法生成ID（唯一）
            SnowFlakeIdUtils snowFlakeIdUtils = new SnowFlakeIdUtils(9,1);
            propertyCountQuit.setId(snowFlakeIdUtils.nextId());
            propertyCountQuitService.save(propertyCountQuit);
            //获得近半年的离职人员列表
            List<PropertyCountQuit> quits = propertyCountQuitService.quitList();
            List<String> monthList =  new ArrayList<>();
            List<Integer> numList = new ArrayList<>();
            quits.stream().forEach(item->{
                monthList.add(item.getMonth());
                numList.add(item.getNum());
            });
            redisTemplate.opsForList().leftPush(monthKey,monthList);
            redisTemplate.opsForList().leftPush(numKey,numList);
            logger.info("离职人数统计完毕");
        }catch (Exception e){
            logger.error("离职人数统计异常"+e.getMessage());
        }
    }

}
