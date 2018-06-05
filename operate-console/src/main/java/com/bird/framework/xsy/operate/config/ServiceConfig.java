package com.bird.framework.xsy.operate.config;

import org.mybatis.spring.annotation.MapperScan;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jesse.Han
 */
@ComponentScan("com.bird.framework.xsy.operate.service")
@MapperScan("com.bird.framework.xsy.operate.mapper")
public class ServiceConfig {
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private String port;

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + host + ":" + port);
        return Redisson.create(config);
    }

    @Bean
    public CacheManager cacheManager(RedissonClient redissonClient) {
        Map<String, CacheConfig> config = new HashMap<>();
        // 创建一个名称为"testMap"的缓存，过期时间ttl为24分钟，同时最长空闲时maxIdleTime为12分钟。
        return new RedissonSpringCacheManager(redissonClient, config);
    }

}
