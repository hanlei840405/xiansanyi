package com.bird.framework.xsy.mall.mapper;

import com.bird.framework.xsy.mall.entity.JdRegion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JdRegionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(JdRegion record);

    int insertSelective(JdRegion record);

    JdRegion selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(JdRegion record);

    int updateByPrimaryKey(JdRegion record);

    List<JdRegion> findAll();

}