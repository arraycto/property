package com.mlb.userserviceprovider.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.mlb.userserviceprovider.domain.Member;
import com.mlb.userserviceprovider.domain.vo.MemberQueryVo;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author mlb
 * @since 2020-02-19
 */
public interface MemberService extends IService<Member> {

    /**
     * 返回住户列表
     * @param memberQueryVo
     * @return
     */
    List<Member> memberList(MemberQueryVo memberQueryVo);

    /**
     * 根据phone返回用户
     * @param phone
     * @return
     */
    Member memberPhone(String phone);
}
