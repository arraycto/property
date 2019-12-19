package com.mlb.userserviceprovider.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mlb.userserviceprovider.dao.PropertyUserMapper;
import com.mlb.userserviceprovider.domain.PropertyUser;
import com.mlb.userserviceprovider.domain.vo.PropertyUserVo;

/**
 * <p>
 * 物业用户表 服务类
 * </p>
 *
 * @author mlb
 * @since 2019-11-19
 */
public interface PropertyUserService extends IService<PropertyUser> {

    /**
     * 检查用户登陆
     * @param phone
     * @param password
     * @return
     */
    PropertyUser propertyUserLogin(String phone, String password);

    /**
     * 查看用户列表
     * @param page
     * @param propertyUserVo
     * @return
     */
    IPage<PropertyUser> selectPageList(Page page,PropertyUserVo propertyUserVo);
}
