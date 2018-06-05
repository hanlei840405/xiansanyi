package com.bird.framework.xsy.mall.mapper;

import com.bird.framework.xsy.mall.entity.UserPasswordRecord;

public interface UserPasswordRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserPasswordRecord record);

    int insertSelective(UserPasswordRecord record);

    UserPasswordRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserPasswordRecord record);

    int updateByPrimaryKey(UserPasswordRecord record);

    UserPasswordRecord selectLastByUsername(String username);
}