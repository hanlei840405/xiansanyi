package com.bird.framework.xsy.mall.mapper;

import com.bird.framework.xsy.mall.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Member record);

    int insertSelective(Member record);

    Member selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Member record);

    int updateByPrimaryKey(Member record);

    Member selectByUsername(String username);

    List<Member> findAll(@Param("role") String role, @Param("gender") String gender, @Param("mobile") String mobile);
}