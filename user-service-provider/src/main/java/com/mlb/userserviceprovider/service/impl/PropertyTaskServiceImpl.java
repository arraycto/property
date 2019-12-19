package com.mlb.userserviceprovider.service.impl;



import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mlb.userserviceprovider.dao.PropertyTaskMapper;
import com.mlb.userserviceprovider.domain.PropertyTask;
import com.mlb.userserviceprovider.domain.vo.PropertyTaskVo;
import com.mlb.userserviceprovider.service.PropertyTaskService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mlb
 * @since 2019-11-20
 */
@Service
public class PropertyTaskServiceImpl extends ServiceImpl<PropertyTaskMapper, PropertyTask> implements PropertyTaskService {

    @Override
    public IPage<PropertyTask> findPageList(Page page, PropertyTaskVo propertyTaskVo) {
        return this.getBaseMapper().selectPageList(page,propertyTaskVo);
    }

    @Override
    public IPage<PropertyTask> findWorkList(Page page, Long userId) {
        return this.getBaseMapper().selectWorkTask(page,userId);
    }
}
