package com.mlb.userserviceprovider.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mlb.userserviceprovider.domain.PropertyTask;
import com.mlb.userserviceprovider.domain.vo.PropertyTaskVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mlb
 * @since 2019-11-20
 */
@Mapper
public interface PropertyTaskMapper extends BaseMapper<PropertyTask> {

    /**
     * 查看任务列表
     * @param page
     * @param propertyTaskVo
     * @return
     */
    IPage<PropertyTask> selectPageList(Page page, @Param("param")PropertyTaskVo propertyTaskVo);

    /**
     * 维修工查看自己接下的工作需求
     * @param page
     * @param userId
     * @return
     */
    IPage<PropertyTask> selectWorkTask(Page page,@Param("workId") Long userId);
}
