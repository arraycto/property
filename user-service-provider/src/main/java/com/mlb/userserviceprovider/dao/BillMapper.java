package com.mlb.userserviceprovider.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mlb.userserviceprovider.domain.Bill;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mlb
 * @since 2020-02-24
 */
@Mapper
public interface BillMapper extends BaseMapper<Bill> {

    /**
     * 通过userId和homeId找到唯一账单
     * @param userId
     * @param homeId
     * @return
     */
    Bill confirmBill(@Param("userId")String userId,@Param("homeId")String homeId);
}
