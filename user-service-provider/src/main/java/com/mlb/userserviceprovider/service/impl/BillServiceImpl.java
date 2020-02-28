package com.mlb.userserviceprovider.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mlb.userserviceprovider.dao.BillMapper;
import com.mlb.userserviceprovider.domain.Bill;
import com.mlb.userserviceprovider.domain.vo.BillQuery;
import com.mlb.userserviceprovider.service.BillService;

import java.util.Date;
import java.util.List;

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

    @Override
    public List<Bill> billList(BillQuery billQuery) {
        return this.getBaseMapper().billList(billQuery);
    }

    @Override
    public List<Long> arrearsMembers(Date deadline) {
        return this.getBaseMapper().arrearsMembers(deadline);
    }
}
