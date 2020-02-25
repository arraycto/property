package com.mlb.userserviceprovider.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mlb.userserviceprovider.domain.Bill;
import com.mlb.userserviceprovider.domain.vo.BillQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mlb
 * @since 2020-02-24
 */
public interface BillService extends IService<Bill> {

    /**
     * 通过userId和homeId找到唯一账单
     * @param userId
     * @param homeId
     * @return
     */
    Bill confirmBill(String userId,String homeId);

    /**
     * 返回账单列表
     * @param billQuery
     * @return
     */
    List<Bill> billList(BillQuery billQuery);

}
