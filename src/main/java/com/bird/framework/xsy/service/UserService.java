package com.bird.framework.xsy.service;

import com.bird.framework.xsy.entity.User;
import com.bird.framework.xsy.mapper.UserMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author jesse.Han
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public User selectById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    public Page<User> page(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        Page<User> page = (Page<User>) userMapper.findAll();
        return page;
    }

    @Transactional
    public void save(User user) {
        if (user.getId() != null) {
            // update
            userMapper.updateByPrimaryKeySelective(user);
        } else {
            // insert
            String username = sequenceService.generate("user", "%08d");
            user.setUsername(username);
            userMapper.insert(user);
        }
    }

    @Transactional
    public void delete(Long id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public void delete(Long... ids) {
        jdbcTemplate.batchUpdate("update operate_user set status='0' where id=?", new BatchPreparedStatementSetter() {
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
