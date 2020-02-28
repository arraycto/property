package com.mlb.orderserviceconsumer.cron;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mlb.userserviceprovider.domain.Member;
import com.mlb.userserviceprovider.service.BillService;
import com.mlb.userserviceprovider.service.MemberService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

/**
 * 统计本月之前账单未付清用户
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

    private Logger logger = LoggerFactory.getLogger(BillJob.class);

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

}
