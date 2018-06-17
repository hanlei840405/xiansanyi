package com.bird.framework.xsy.mall.mapper;

import com.bird.framework.xsy.mall.entity.Movie;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MovieMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Movie record);

    Movie selectByPrimaryKey(Long id);

    Movie selectByCode(String code);

    List<Movie> selectByMultiParams(@Param("buyer") String buyer, @Param("seller") String seller, @Param("mobile") String mobile, @Param("location") String location, @Param("cinema") String cinema, @Param("movie") String movie);

    int pay(Movie record);

    int send2seller(Movie record);

    int assign(Movie record);

    int send2audit(Movie record);

    int send2buyer(Movie record);

    int cancel(Movie record);

    int finish(Movie record);
}