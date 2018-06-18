package com.bird.framework.xsy.mall.mapper;

import com.bird.framework.xsy.mall.entity.JdCinema;

import java.util.List;

public interface JdCinemaMapper {
    int deleteByPrimaryKey(Long id);

    int insert(JdCinema record);

    int insertSelective(JdCinema record);

    JdCinema selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(JdCinema record);

    int updateByPrimaryKey(JdCinema record);

    List<JdCinema> findByRegion(String regionCode);
}