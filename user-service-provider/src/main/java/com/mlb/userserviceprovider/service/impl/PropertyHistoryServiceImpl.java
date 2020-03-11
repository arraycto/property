package com.mlb.userserviceprovider.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mlb.userserviceprovider.common.RespPageBean;
import com.mlb.userserviceprovider.dao.PropertyHistoryMapper;
import com.mlb.userserviceprovider.domain.PropertyHistory;
import com.mlb.userserviceprovider.service.PropertyHistoryService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mlb
 * @since 2020-01-14
 */
@Service
public class PropertyHistoryServiceImpl extends ServiceImpl<PropertyHistoryMapper, PropertyHistory> implements PropertyHistoryService {

    @Override
    public List<PropertyHistory> QuitList() {
        return this.getBaseMapper().selectQuitList();
    }

    @Override
    public Integer countQuitByTime(Date startTime, Date endTime) {
        return this.getBaseMapper().countQuitByTime(startTime,endTime);
    }
}
