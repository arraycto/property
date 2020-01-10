package com.mlb.userserviceprovider.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mlb.userserviceprovider.domain.Property;
import com.mlb.userserviceprovider.domain.form.LoginUser;
import com.mlb.userserviceprovider.domain.vo.PropertyVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mlb
 * @since 2019-12-20
 */
@Mapper
public interface PropertyMapper extends BaseMapper<Property> {
    /**
     * 用户登陆判断
     * @param loginUser
     * @return
     */
    Property login(@Param("loginUser")LoginUser loginUser);

    /**
     * 返回用户列表
     * @param propertyVo
     * @return
     */
    List<Property> selectPropertyList(@Param("propertyVo") PropertyVo propertyVo);
}
