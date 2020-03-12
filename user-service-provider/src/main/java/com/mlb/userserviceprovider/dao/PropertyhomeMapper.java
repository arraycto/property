package com.mlb.userserviceprovider.dao;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mlb.userserviceprovider.domain.Propertyhome;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户房产信息关联表 Mapper 接口
 * </p>
 *
 * @author mlb
 * @since 2020-02-17
 */
@Mapper
public interface PropertyhomeMapper extends BaseMapper<Propertyhome> {

    /**
     * 根据homeid查看房屋是否出租或者被购买
     * @param homeId
     * @return
     */
    List<Propertyhome> propertyHomeList(@Param("homeId")String homeId);

    /**
     * 根据userId判断是否存在关联homeid，能否被删除
     * @param userId
     * @return
     */
    List<Propertyhome> propertyList(@Param("userId")String userId);

    /**
     * 根据用户id查找房产信息，不管删除与否
     * @param userId
     * @return
     */
    List<Propertyhome> memberHome(@Param("userId")Long userId);

    /**
     * 根据userid和homeid找到唯一关联数据
     * @param homeId
     * @param userId
     * @return
     */
    Propertyhome propertyHome(@Param("homeId")String homeId,@Param("userId")String userId);
}
