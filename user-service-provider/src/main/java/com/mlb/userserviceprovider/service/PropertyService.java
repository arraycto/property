package com.mlb.userserviceprovider.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mlb.userserviceprovider.common.JsonResult;
import com.mlb.userserviceprovider.common.RespPageBean;
import com.mlb.userserviceprovider.domain.Property;
import com.mlb.userserviceprovider.domain.form.LoginUser;
import com.mlb.userserviceprovider.domain.vo.PropertyQuery;
import com.mlb.userserviceprovider.domain.vo.PropertyVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mlb
 * @since 2019-12-20
 */
public interface PropertyService extends IService<Property> {

    /**
     * 用户登陆判断
     * @param loginUser
     * @return
     */
    Property loginByUser(LoginUser loginUser);

    /**
     * 返回用户列表
     * @param propertyVo
     * @param page
     * @param size
     * @return
     */
    RespPageBean propertyList(PropertyQuery propertyVo, Integer page, Integer size);

}
