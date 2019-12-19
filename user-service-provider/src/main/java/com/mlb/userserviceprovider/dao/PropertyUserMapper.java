package com.mlb.userserviceprovider.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mlb.userserviceprovider.domain.PropertyUser;
import com.mlb.userserviceprovider.domain.vo.PropertyUserVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 物业用户表 Mapper 接口
 * </p>
 *
 * @author mlb
 * @since 2019-11-19
 */
@Mapper
public interface PropertyUserMapper extends BaseMapper<PropertyUser> {
    /**
     * 登陆查找用户
     * @param phone
     * @param password
     * @return
     */
    PropertyUser selectByLogin(@Param("phone") String phone,@Param("password") String password);

    /**
     * 查看用户列表
     * @param page
     * @param propertyUserVo
     * @return
     */
    IPage<PropertyUser> selectPageList(Page page, @Param("propertyUser")PropertyUserVo propertyUserVo);
}
