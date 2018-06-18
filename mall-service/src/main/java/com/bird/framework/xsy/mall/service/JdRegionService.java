package com.bird.framework.xsy.mall.service;

import com.bird.framework.xsy.mall.entity.JdRegion;
import com.bird.framework.xsy.mall.mapper.JdRegionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JdRegionService {
    @Autowired
    private JdRegionMapper jdRegionMapper;

    public void save(JdRegion jdRegion) {
        jdRegionMapper.insert(jdRegion);
    }

    public List<JdRegion> findAll() {
        return jdRegionMapper.findAll();
    }
}
