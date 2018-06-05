package com.bird.framework.xsy.operate.mapper;

import com.bird.framework.xsy.operate.entity.Group;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GroupMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Group record);

    int insertSelective(Group record);

    Group selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Group record);

    int updateByPrimaryKey(Group record);
}