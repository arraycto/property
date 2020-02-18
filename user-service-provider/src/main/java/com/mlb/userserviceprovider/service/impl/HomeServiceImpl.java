package com.mlb.userserviceprovider.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mlb.userserviceprovider.dao.HomeMapper;
import com.mlb.userserviceprovider.domain.Home;
import com.mlb.userserviceprovider.domain.vo.HomeQueryVo;
import com.mlb.userserviceprovider.service.HomeService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mlb
 * @since 2020-02-17
 */
@Service
public class HomeServiceImpl extends ServiceImpl<HomeMapper, Home> implements HomeService {
    @Override
    public List<Home> homeList(HomeQueryVo homeQueryVo) {
        return this.getBaseMapper().homeList(homeQueryVo);
    }
}
