package com.mlb.userserviceprovider.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mlb.userserviceprovider.domain.MemberViolation;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mlb
 * @since 2020-03-13
 */
public interface MemberViolationService extends IService<MemberViolation> {

    /**
     * 查询用户的处罚记录
     * @param userId
     * @return
     */
    List<MemberViolation> listByUserId(Long userId);
}
