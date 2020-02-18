package com.mlb.userserviceprovider.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mlb.userserviceprovider.domain.Propertyhome;

import java.util.List;

/**
 * <p>
 * 用户房产信息关联表 服务类
 * </p>
 *
 * @author mlb
 * @since 2020-02-17
 */
public interface PropertyhomeService extends IService<Propertyhome> {

    /**
     * 根据homeid查看房屋是否出租或者被购买
     * @param homeId
     * @return
     */
    List<Propertyhome> propertyHomeList(String homeId);

    /**
     * 根据userId判断是否存在关联homeid，能否被删除
     * @param userId
     * @return
     */
    List<Propertyhome> propertyList(String userId);

    /**
     * 根据userid和homeid找到唯一关联数据
     * @param userId
     * @param homeId
     * @return
     */
    Propertyhome propertyhome(String userId,String homeId);
}
