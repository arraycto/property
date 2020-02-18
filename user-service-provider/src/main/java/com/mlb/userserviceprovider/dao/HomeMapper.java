package com.mlb.userserviceprovider.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mlb.userserviceprovider.domain.Home;
import com.mlb.userserviceprovider.domain.vo.HomeQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

;import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mlb
 * @since 2020-02-17
 */
@Mapper
public interface HomeMapper extends BaseMapper<Home> {
    /**
     * 返回房产信息列表
     * @param homeQueryVo
     * @return
     */
    List<Home> homeList(@Param("homeQuery")HomeQueryVo homeQueryVo);
}
