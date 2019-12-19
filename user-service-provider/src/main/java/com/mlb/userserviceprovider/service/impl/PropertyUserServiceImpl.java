package com.mlb.userserviceprovider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mlb.userserviceprovider.dao.PropertyUserMapper;
import com.mlb.userserviceprovider.domain.PropertyUser;
import com.mlb.userserviceprovider.domain.vo.PropertyUserVo;
import com.mlb.userserviceprovider.service.PropertyUserService;


/**
 * <p>
 * 物业用户表 服务实现类
 * </p>
 *
 * @author mlb
 * @since 2019-11-19
 */
@Service
public class PropertyUserServiceImpl extends ServiceImpl<PropertyUserMapper, PropertyUser> implements PropertyUserService {

    @Override
    public PropertyUser propertyUserLogin(String phone, String password) {
        return this.getBaseMapper().selectByLogin(phone,password);
    }

    @Override
    public IPage<PropertyUser> selectPageList(Page page,PropertyUserVo propertyUserVo) {
        return this.getBaseMapper().selectPageList(page,propertyUserVo);
    }

}
