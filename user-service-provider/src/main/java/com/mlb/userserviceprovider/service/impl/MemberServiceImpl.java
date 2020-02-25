package com.mlb.userserviceprovider.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mlb.userserviceprovider.dao.MemberMapper;
import com.mlb.userserviceprovider.domain.Member;
import com.mlb.userserviceprovider.domain.vo.MemberQueryVo;
import com.mlb.userserviceprovider.service.MemberService;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mlb
 * @since 2020-02-19
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Override
    public List<Member> memberList(MemberQueryVo memberQueryVo) {
        return this.getBaseMapper().memberList(memberQueryVo);
    }

    @Override
    public Member memberPhone(String phone) {
        return this.getBaseMapper().memberPhone(phone);
    }
}
