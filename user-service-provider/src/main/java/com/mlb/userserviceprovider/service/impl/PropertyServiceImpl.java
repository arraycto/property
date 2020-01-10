package com.mlb.userserviceprovider.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mlb.userserviceprovider.common.JsonResult;
import com.mlb.userserviceprovider.dao.PropertyMapper;
import com.mlb.userserviceprovider.domain.Property;
import com.mlb.userserviceprovider.domain.form.LoginUser;
import com.mlb.userserviceprovider.domain.vo.PropertyVo;
import com.mlb.userserviceprovider.service.PropertyService;


import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mlb
 * @since 2019-12-20
 */
@Service
public class PropertyServiceImpl extends ServiceImpl<PropertyMapper, Property> implements PropertyService {

    @Override
    public Property loginByUser(LoginUser loginUser) {
        return this.getBaseMapper().login(loginUser);
    }

    @Override
    public List<Property> propertyList(PropertyVo propertyVo) {
        return this.getBaseMapper().selectPropertyList(propertyVo);
    }
}
