package com.bird.framework.xsy.mall.mapper;

import com.bird.framework.xsy.mall.entity.TimeCinema;

import java.util.List;

public interface TimeCinemaMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TimeCinema record);

    int insertSelective(TimeCinema record);

    TimeCinema selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TimeCinema record);

    int updateByPrimaryKey(TimeCinema record);

    TimeCinema selectByCinema(String cinema);

    List<TimeCinema> selectByDistrict(String districtCode);
}