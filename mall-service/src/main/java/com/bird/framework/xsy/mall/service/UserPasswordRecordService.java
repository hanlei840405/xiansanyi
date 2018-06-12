package com.bird.framework.xsy.mall.service;

import com.bird.framework.xsy.mall.entity.UserPasswordRecord;
import com.bird.framework.xsy.mall.mapper.MallUserPasswordRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("mallUserPasswordRecordService")
public class UserPasswordRecordService {
    @Autowired
    private MallUserPasswordRecordMapper mallUserPasswordRecordMapper;

    public UserPasswordRecord selectLastByUsername(String username) {
        return mallUserPasswordRecordMapper.selectLastByUsername(username);
    }


    @Transactional
    public void save(UserPasswordRecord userPasswordRecord) {
        mallUserPasswordRecordMapper.insert(userPasswordRecord);
    }
}
