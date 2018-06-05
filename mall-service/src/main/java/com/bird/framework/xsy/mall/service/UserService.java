package com.bird.framework.xsy.mall.service;

import com.bird.framework.xsy.mall.entity.User;
import com.bird.framework.xsy.mall.entity.UserPasswordRecord;
import com.bird.framework.xsy.mall.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserPasswordRecordService userPasswordRecordService;

    public User selectByUsername(String username) {
        return userMapper.selectByUsername(username);
    }


    @Transactional
    public void save(User user) {
        if (user.getId() != null) {
            // update
            userMapper.updateByPrimaryKeySelective(user);
        } else {
            // insert
            String username = sequenceService.generate("mall-user", "%08d");
            user.setUsername(username);
            userMapper.insert(user);
            UserPasswordRecord userPasswordRecord = new UserPasswordRecord();
            userPasswordRecord.setUserId(user.getId());
            userPasswordRecord.setCreated(new java.util.Date());
            userPasswordRecordService.save(userPasswordRecord);
        }
        // 更新user与role的绑定关系
        jdbcTemplate.batchUpdate("insert into mall_user_role(user_id, role_id) values (?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setLong(1, user.getId());
                preparedStatement.setLong(2, user.getRoles().get(i).getId());
            }

            @Override
            public int getBatchSize() {
                return user.getRoles().size();
            }
        });
    }
}
