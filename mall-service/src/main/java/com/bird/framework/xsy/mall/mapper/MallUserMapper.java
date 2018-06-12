package com.bird.framework.xsy.mall.mapper;

import com.bird.framework.xsy.mall.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MallUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectByUsername(String username);
}