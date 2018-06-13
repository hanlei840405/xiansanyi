package com.bird.framework.xsy.mall.mapper;

import com.bird.framework.xsy.mall.entity.PasswordRecord;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PasswordRecordMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PasswordRecord record);

    int insertSelective(PasswordRecord record);

    PasswordRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(PasswordRecord record);

    int updateByPrimaryKey(PasswordRecord record);

    PasswordRecord selectLastByUsername(String username);
}