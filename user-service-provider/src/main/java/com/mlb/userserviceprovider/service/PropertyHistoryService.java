package com.mlb.userserviceprovider.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mlb.userserviceprovider.common.RespPageBean;
import com.mlb.userserviceprovider.domain.PropertyHistory;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mlb
 * @since 2020-01-14
 */
public interface PropertyHistoryService extends IService<PropertyHistory> {

    /**
     * 返回离职人员列表
     * @param page
     * @param size
     * @return
     */
    List<PropertyHistory> QuitList();
}
