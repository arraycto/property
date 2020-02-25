package com.mlb.userserviceprovider.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mlb.userserviceprovider.dao.BillMapper;
import com.mlb.userserviceprovider.domain.Bill;
import com.mlb.userserviceprovider.service.BillService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mlb
 * @since 2020-02-24
 */
@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements BillService {

    @Override
    public Bill confirmBill(String userId, String homeId) {
        return this.getBaseMapper().confirmBill(userId, homeId);
    }
}
