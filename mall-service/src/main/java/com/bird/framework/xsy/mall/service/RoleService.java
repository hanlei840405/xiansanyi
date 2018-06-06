package com.bird.framework.xsy.mall.service;

import com.bird.framework.xsy.mall.entity.Role;
import com.bird.framework.xsy.mall.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private SequenceService sequenceService;

    public List<Role> selectByUsername(String username) {
        return roleMapper.selectByUsername(username);
    }


    @Transactional
    public void save(Role role) {
        if (role.getId() != null) {
            // update
            roleMapper.updateByPrimaryKeySelective(role);
        } else {
            // insert
            roleMapper.insert(role);
        }
    }
}
