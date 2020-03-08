package com.mlb.userserviceprovider.service.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mlb.userserviceprovider.common.JsonResult;
import com.mlb.userserviceprovider.common.RespPageBean;
import com.mlb.userserviceprovider.dao.PropertyMapper;
import com.mlb.userserviceprovider.domain.Property;
import com.mlb.userserviceprovider.domain.form.LoginUser;
import com.mlb.userserviceprovider.domain.vo.PropertyQuery;
import com.mlb.userserviceprovider.domain.vo.PropertyVo;
import com.mlb.userserviceprovider.service.PropertyService;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mlb
 * @since 2019-12-20
 */
@Service
public class PropertyServiceImpl extends ServiceImpl<PropertyMapper, Property> implements PropertyService {

    @Override
    public Property loginByUser(LoginUser loginUser) {
        return this.getBaseMapper().login(loginUser);
    }

    @Override
    public RespPageBean propertyList(PropertyQuery propertyVo,Integer page,Integer size) {
        RespPageBean pageBean = new RespPageBean();
        // 默认从0开始
        if (page != null && size != null) {
            page = (page-1)*size;
        }
        List<Property> propertyList = this.getBaseMapper().selectPropertyList(propertyVo,page,size);
        List<PropertyVo> propertyVoList = new ArrayList<>();
        propertyList.stream().forEach(item -> {
            PropertyVo property = new PropertyVo();
            BeanUtil.copyProperties(item,property);
            if(item.getGender().equals(1)){
                property.setGender("男");
            }else{
                property.setGender("女");
            }
            switch (item.getUserType()){
                case 1: property.setUserType("物业管理员");break;
                case 2: property.setUserType("普通员工");break;
            }
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            property.setCreateTime(formatter.format(item.getCreateTime()));
            propertyVoList.add(property);
        });
        pageBean.setData(propertyVoList);
        pageBean.setTotal(propertyVoList.size());
        return pageBean;
    }
}
