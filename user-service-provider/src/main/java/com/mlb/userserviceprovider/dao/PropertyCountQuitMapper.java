package com.mlb.userserviceprovider.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mlb.userserviceprovider.domain.PropertyCountQuit;
import org.apache.ibatis.annotations.Mapper;


import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mlb
 * @since 2020-03-11
 */
@Mapper
public interface PropertyCountQuitMapper extends BaseMapper<PropertyCountQuit> {
    /**
     * 近半年离职人员人数列表
     * @return
     */
    List<PropertyCountQuit> quitList();
}
