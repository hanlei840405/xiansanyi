package com.bird.framework.xsy.mall.service;

import com.bird.framework.xsy.mall.entity.Role;
import com.bird.framework.xsy.mall.mapper.MallRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("mallRoleService")
public class RoleService {
    @Autowired
    private MallRoleMapper mallRoleMapper;

    public List<Role> selectByUsername(String username) {
        return mallRoleMapper.selectByUsername(username);
    }


    @Transactional
    public void save(Role role) {
        if (role.getId() != null) {
            // update
            mallRoleMapper.updateByPrimaryKeySelective(role);
        } else {
            // insert
            mallRoleMapper.insert(role);
        }
    }
}
