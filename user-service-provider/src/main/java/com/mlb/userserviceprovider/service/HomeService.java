package com.mlb.userserviceprovider.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mlb.userserviceprovider.domain.Home;
import com.mlb.userserviceprovider.domain.vo.HomeQueryVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mlb
 * @since 2020-02-17
 */
public interface HomeService extends IService<Home> {
    /**
     * 返回房产信息列表
     * @param homeQueryVo
     * @return
     */
    List<Home> homeList(HomeQueryVo homeQueryVo);
}
