package com.bird.framework.xsy.operate.mapper;

import com.bird.framework.xsy.operate.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface MenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu> tree(Long parentId);

    List<Menu> privileges(@Param("parentId") Long parentId, @Param("roles") List<String> roles);

    List<Menu> findAll(Long parentId);

    List<Map<String,Object>> allByRole(String code);
}