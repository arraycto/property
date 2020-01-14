package com.mlb.userserviceprovider.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mlb.userserviceprovider.dao.PropertyHistoryMapper;
import com.mlb.userserviceprovider.domain.PropertyHistory;
import com.mlb.userserviceprovider.service.PropertyHistoryService;

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

}
