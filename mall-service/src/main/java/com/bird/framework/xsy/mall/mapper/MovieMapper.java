package com.bird.framework.xsy.mall.mapper;

import com.bird.framework.xsy.mall.entity.Movie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MovieMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Movie record);

    int insertSelective(Movie record);

    Movie selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Movie record);

    int updateByPrimaryKey(Movie record);

    List<Movie> selectByMultiParams(@Param("buyer") String buyer, @Param("seller") String seller, @Param("mobile") String mobile, @Param("location") String location, @Param("cinema") String cinema, @Param("movie") String movie);
}