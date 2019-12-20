package com.mlb.userserviceprovider.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mlb.userserviceprovider.common.JsonResult;
import com.mlb.userserviceprovider.domain.Property;
import com.mlb.userserviceprovider.domain.form.LoginUser;

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
    JsonResult loginByUser(LoginUser loginUser);

}
