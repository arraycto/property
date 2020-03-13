package com.mlb.userserviceprovider.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mlb.userserviceprovider.dao.MemberViolationMapper;
import com.mlb.userserviceprovider.domain.MemberViolation;
import com.mlb.userserviceprovider.service.MemberViolationService;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mlb
 * @since 2020-03-13
 */
@Service
public class MemberViolationServiceImpl extends ServiceImpl<MemberViolationMapper, MemberViolation> implements MemberViolationService {

    @Override
    public List<MemberViolation> listByUserId(Long userId) {
        return this.getBaseMapper().listByUserId(userId);
    }
}
