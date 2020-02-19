package com.mlb.userserviceprovider.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.mlb.userserviceprovider.domain.Member;
import com.mlb.userserviceprovider.domain.vo.MemberQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author mlb
 * @since 2020-02-19
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {

    /**
     * 返回住户列表
     * @param memberQueryVo
     * @return
     */
    List<Member> memberList(@Param("memberVo")MemberQueryVo memberQueryVo);
}
