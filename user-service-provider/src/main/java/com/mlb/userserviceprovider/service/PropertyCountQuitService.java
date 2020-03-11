package com.mlb.userserviceprovider.service;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mlb.userserviceprovider.domain.PropertyCountQuit;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mlb
 * @since 2020-03-11
 */
public interface PropertyCountQuitService extends IService<PropertyCountQuit> {

    /**
     * 返回近半年离职人员数量列表
     * @return
     */
    List<PropertyCountQuit> quitList();
}
