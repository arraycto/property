package com.mlb.userserviceprovider.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mlb.userserviceprovider.dao.PropertyCountQuitMapper;
import com.mlb.userserviceprovider.domain.PropertyCountQuit;
import com.mlb.userserviceprovider.service.PropertyCountQuitService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mlb
 * @since 2020-03-11
 */
@Service
public class PropertyCountQuitServiceImpl extends ServiceImpl<PropertyCountQuitMapper, PropertyCountQuit> implements PropertyCountQuitService {

}
