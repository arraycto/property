package com.mlb.userserviceprovider.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mlb.userserviceprovider.common.RespPageBean;
import com.mlb.userserviceprovider.dao.PropertyHistoryMapper;
import com.mlb.userserviceprovider.domain.PropertyHistory;
import com.mlb.userserviceprovider.domain.vo.QuitCircleVo;
import com.mlb.userserviceprovider.service.PropertyHistoryService;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mlb
 * @since 2020-01-14
 */
@Service
public class PropertyHistoryServiceImpl extends ServiceImpl<PropertyHistoryMapper, PropertyHistory> implements PropertyHistoryService {

    @Override
    public List<PropertyHistory> QuitList(String phone) {
        return this.getBaseMapper().selectQuitList(phone);
    }

    @Override
    public Integer countQuitByTime(Date startTime, Date endTime) {
        return this.getBaseMapper().countQuitByTime(startTime,endTime);
    }

    @Override
    public List<QuitCircleVo> countQuitCircle(Date startTime, Date endTime) {
        Integer adminNum = this.getBaseMapper().countQuitAdmin(startTime,endTime);
        Integer ordinaryNum = this.getBaseMapper().countQuitOrdinary(startTime, endTime);
        QuitCircleVo quitCircleAdminVo = new QuitCircleVo();
        quitCircleAdminVo.setName("物业管理员");
        quitCircleAdminVo.setValue(adminNum);
        QuitCircleVo quitCircleOrdinaryVo = new QuitCircleVo();
        quitCircleOrdinaryVo.setName("普通员工");
        quitCircleOrdinaryVo.setValue(ordinaryNum);
        List<QuitCircleVo> quitCircleVos = new ArrayList<>();
        quitCircleVos.add(quitCircleAdminVo);
        quitCircleVos.add(quitCircleOrdinaryVo);
        return quitCircleVos;
    }
}
