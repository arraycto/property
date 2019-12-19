package com.mlb.userserviceprovider.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mlb.userserviceprovider.domain.PropertyTask;
import com.mlb.userserviceprovider.domain.vo.PropertyTaskVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mlb
 * @since 2019-11-20
 */
public interface PropertyTaskService extends IService<PropertyTask> {

    /**
     * 查看任务日志列表
     * @param page
     * @param propertyTaskVo
     * @return
     */
    IPage<PropertyTask> findPageList(Page page, PropertyTaskVo propertyTaskVo);

    /**
     * 查看自己接下的任务需求
     * @param page
     * @param userId
     * @return
     */
    IPage<PropertyTask> findWorkList(Page page,Long userId);
}
