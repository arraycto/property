package com.mlb.userserviceprovider.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mlb.userserviceprovider.domain.MemberViolation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mlb
 * @since 2020-03-13
 */
@Mapper
public interface MemberViolationMapper extends BaseMapper<MemberViolation> {

    /**
     * 查找用户处罚详情
     * @param userId
     * @return
     */
    List<MemberViolation> listByUserId(@Param("userId")Long userId);

}
