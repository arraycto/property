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
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

    @Scheduled(cron = "0/10 * * * * ? ")
//    @Scheduled(cron = "* 30 23 L * ? *")
    public void quitList(){
        String key = "count:quit:";
        logger.info("开始统计本月离职人数");
        Date endTime = new Date();
        Date startTime = DateUtil.getStartDayOfMonth(endTime);
        try {
            PropertyCountQuit propertyCountQuit = new PropertyCountQuit();
            propertyCountQuit.setNum(propertyHistoryService.countQuitByTime(startTime, endTime));
            propertyCountQuit.setMonth(DateUtil.PreciseMonth(startTime));
            //运用雪花算法生成ID（唯一）
            SnowFlakeIdUtils snowFlakeIdUtils = new SnowFlakeIdUtils(9,1);
            propertyCountQuit.setId(snowFlakeIdUtils.nextId());
            propertyCountQuitService.save(propertyCountQuit);
            redisTemplate.opsForValue().set(key.concat(String.valueOf(startTime.getTime())),propertyCountQuit);
            logger.info("离职人数统计完毕");
        }catch (Exception e){
            logger.error("离职人数统计异常"+e.getMessage());
        }
    }


    /**
     * redisTemplate存值序列化
     * @param redisTemplate
     */
    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        this.redisTemplate = redisTemplate;
    }

}
