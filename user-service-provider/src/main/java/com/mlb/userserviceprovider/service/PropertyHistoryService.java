package com.mlb.userserviceprovider.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mlb.userserviceprovider.common.RespPageBean;
import com.mlb.userserviceprovider.domain.PropertyHistory;
import com.mlb.userserviceprovider.domain.vo.QuitCircleVo;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mlb
 * @since 2020-01-14
 */
public interface PropertyHistoryService extends IService<PropertyHistory> {

    /**
     * 返回离职人员列表
     * @param phone
     * @return
     */
    List<PropertyHistory> QuitList(String phone);

    /**
     * 统计上个月离职人员数量
     * @param startTime
     * @param endTime
     * @return
     */
    Integer countQuitByTime(Date startTime,Date endTime);

    /**
     * 统计上月管理员和员工离职情况，制作饼图
     * @param startTime
     * @param endTime
     * @return
     */
    List<QuitCircleVo> countQuitCircle(Date startTime,Date endTime);
}
