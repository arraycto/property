package com.mlb.userserviceprovider.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mlb.userserviceprovider.domain.Bill;
import com.mlb.userserviceprovider.domain.vo.BillQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

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

    /**
     * 返回账单列表
     * @param billQuery
     * @return
     */
    List<Bill> billList(@Param("billQuery")BillQuery billQuery);

    /**
     * 统计欠费用户id
     * @param deadline
     * @return
     */
    List<Long> arrearsMembers(@Param("deadline") Date deadline);
}
