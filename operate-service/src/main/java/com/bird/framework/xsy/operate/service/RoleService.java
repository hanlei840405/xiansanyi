package com.bird.framework.xsy.operate.service;

import com.bird.framework.xsy.operate.entity.Role;
import com.bird.framework.xsy.operate.mapper.RoleMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("operateSequenceService")
    private SequenceService sequenceService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Role selectById(Long id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    public List<Role> selectByUsername(String username) {
        return roleMapper.selectByUsername(username);
    }

    public List<Role> all() {
        return roleMapper.findAll();
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

    @Transactional
    public void grant(Long roleId, List<Long> checkedArary, List<Long> indeterminateArary) {
        jdbcTemplate.update("delete from operate_role_menu where role_id=?", roleId);
        jdbcTemplate.batchUpdate("insert into operate_role_menu (role_id, menu_id, state) values (?, ?, '1')", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setLong(1, roleId);
                preparedStatement.setLong(2, checkedArary.get(i));
            }

            @Override
            public int getBatchSize() {
                return checkedArary.size();
            }
        });
        jdbcTemplate.batchUpdate("insert into operate_role_menu (role_id, menu_id, state) values (?, ?, '0')", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setLong(1, roleId);
                preparedStatement.setLong(2, indeterminateArary.get(i));
            }

            @Override
            public int getBatchSize() {
                return indeterminateArary.size();
            }
        });
    }
}
