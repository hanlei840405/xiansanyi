package com.bird.framework.xsy.mall.mapper;

import com.bird.framework.xsy.mall.entity.CityTime;

import java.util.List;

public interface CityTimeMapper {
    int deleteByPrimaryKey(Long id);

    int insert(CityTime record);

    int insertSelective(CityTime record);

    CityTime selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(CityTime record);

    int updateByPrimaryKey(CityTime record);

    List<CityTime> findAll();

    List<CityTime> findByCategory(String category);

    CityTime selectByTimeCode(String code);

    CityTime selectByCityCode(String code);
}