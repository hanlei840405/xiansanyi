package com.bird.framework.xsy.operate.mapper;

import com.bird.framework.xsy.operate.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu> tree(@Param("parentId") Long parentId, @Param("roles") List<String> roles);

    List<Menu> findAll();
}