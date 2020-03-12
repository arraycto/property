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

    /**
     * 根据单元号，楼层，房间号查找homeId
     * @param unit
     * @param floor
     * @param room
     * @return
     */
    Long roomNumberByHomeId(@Param("unit")Integer unit,@Param("floor")Integer floor,@Param("room")Integer room);

    /**
     * 返回所有单元号
     * @return
     */
    List<Integer> unitList();

    /**
     * 返回所有楼层号
     * @param unit
     * @return
     */
    List<Integer> floorList(@Param("unit")String unit);

    /**
     * 返回所有的房间号
     * @param unit
     * @param floor
     * @return
     */
    List<Integer> roomList(@Param("unit")String unit,@Param("floor")String floor);
}
