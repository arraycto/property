package com.mlb.userserviceprovider.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mlb.userserviceprovider.dao.PropertyhomeMapper;
import com.mlb.userserviceprovider.domain.Propertyhome;
import com.mlb.userserviceprovider.service.PropertyhomeService;

import java.util.List;

/**
 * <p>
 * 用户房产信息关联表 服务实现类
 * </p>
 *
 * @author mlb
 * @since 2020-02-17
 */
@Service
public class PropertyhomeServiceImpl extends ServiceImpl<PropertyhomeMapper, Propertyhome> implements PropertyhomeService {

    @Override
    public List<Propertyhome> propertyHomeList(String homeId) {
        return this.getBaseMapper().propertyHomeList(homeId);
    }

    @Override
    public List<Propertyhome> propertyList(String userId) {
        return this.getBaseMapper().propertyList(userId);
    }

    @Override
    public Propertyhome propertyhome(String userId, String homeId) {
        return this.getBaseMapper().propertyHome(homeId,userId);
    }
}
