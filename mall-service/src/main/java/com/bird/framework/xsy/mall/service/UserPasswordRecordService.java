package com.bird.framework.xsy.mall.service;

import com.bird.framework.xsy.mall.entity.UserPasswordRecord;
import com.bird.framework.xsy.mall.mapper.UserPasswordRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserPasswordRecordService {
    @Autowired
    private UserPasswordRecordMapper userPasswordRecordMapper;

    public UserPasswordRecord selectLastByUsername(String username) {
        return userPasswordRecordMapper.selectLastByUsername(username);
    }


    @Transactional
    public void save(UserPasswordRecord userPasswordRecord) {
        userPasswordRecordMapper.insert(userPasswordRecord);
    }
}
