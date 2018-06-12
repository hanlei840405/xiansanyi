package com.bird.framework.xsy.mall.mapper;

import com.bird.framework.xsy.mall.entity.UserPasswordRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MallUserPasswordRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserPasswordRecord record);

    int insertSelective(UserPasswordRecord record);

    UserPasswordRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserPasswordRecord record);

    int updateByPrimaryKey(UserPasswordRecord record);

    UserPasswordRecord selectLastByUsername(String username);
}