package com.bird.framework.xsy.mall.service;

import com.bird.framework.xsy.mall.entity.PasswordRecord;
import com.bird.framework.xsy.mall.mapper.PasswordRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PasswordRecordService {
    @Autowired
    private PasswordRecordMapper passwordRecordMapper;

    public PasswordRecord selectLastByUsername(String username) {
        return passwordRecordMapper.selectLastByUsername(username);
    }


    @Transactional
    public void save(PasswordRecord passwordRecord) {
        passwordRecordMapper.insert(passwordRecord);
    }
}
