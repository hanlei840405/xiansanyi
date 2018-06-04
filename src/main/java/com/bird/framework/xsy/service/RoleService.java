package com.bird.framework.xsy.service;

import com.bird.framework.xsy.entity.Role;
import com.bird.framework.xsy.mapper.RoleMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 * @author jesse.Han
 */
@Service
public class RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Role selectById(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    public List<Role> selectByUsername(String username) {
        return roleMapper.selectByUsername(username);
    }

    public Page<Role> page(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        Page<Role> page = (Page<Role>) roleMapper.findAll();
        return page;
    }

    @Transactional
    public void save(Role role) {
        if (role.getId() != null) {
            // update
            roleMapper.updateByPrimaryKeySelective(role);
        } else {
            // insert
            String code = sequenceService.generate("role", "%08d");
            role.setCode(code);
            roleMapper.insert(role);
        }
    }

    @Transactional
    public void delete(Long id) {
        roleMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void delete(Long... ids) {
        jdbcTemplate.batchUpdate("update operate_role set status='0' where id=?", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setLong(1, ids[i]);
            }

            @Override
            public int getBatchSize() {
                return ids.length;
            }
        });
    }
}
