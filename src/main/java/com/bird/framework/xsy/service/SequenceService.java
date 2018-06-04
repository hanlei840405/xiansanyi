package com.bird.framework.xsy.service;

import org.redisson.api.RAtomicLong;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SequenceService {

    @Autowired
    private RedissonClient redissonClient;

    public String generate(String category) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(category);
        String code = String.format("%04d", atomicLong.incrementAndGet());
        return code;
    }

    public String generate(String category, String formatter) {
        RAtomicLong atomicLong = redissonClient.getAtomicLong(category);
        String code = String.format(formatter, atomicLong.incrementAndGet());
        return code;
    }
}
