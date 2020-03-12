package com.mlb.userserviceprovider.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mlb.userserviceprovider.domain.Home;
import com.mlb.userserviceprovider.domain.vo.HomeQueryVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mlb
 * @since 2020-02-17
 */
public interface HomeService extends IService<Home> {
    /**
     * 返回房产信息列表
     * @param homeQueryVo
     * @return
     */
    List<Home> homeList(HomeQueryVo homeQueryVo);

    /**
     * 根据单元号，楼层，房间号查找homeId
     * @param unit
     * @param floor
     * @param room
     * @return
     */
    Long roomNumberByHomeId(Integer unit,Integer floor,Integer room);

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
    List<Integer> floorList(String unit);

    /**
     * 返回所有房间号
     * @param unit
     * @param floor
     * @return
     */
    List<Integer> roomList(String unit,String floor);
}
