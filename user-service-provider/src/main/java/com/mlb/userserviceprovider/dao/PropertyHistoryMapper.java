package com.mlb.userserviceprovider.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mlb.userserviceprovider.domain.PropertyHistory;
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
 * @since 2020-01-14
 */
@Mapper
public interface PropertyHistoryMapper extends BaseMapper<PropertyHistory> {
    /**
     * 返回离职人员列表
     * @return
     */
    List<PropertyHistory> selectQuitList();

    /**
     * 统计上个月离职人员数量
     * @param startTime
     * @param endTime
     * @return
     */
    Integer countQuitByTime(@Param("startTime") Date startTime,@Param("endTime") Date endTime);
}
