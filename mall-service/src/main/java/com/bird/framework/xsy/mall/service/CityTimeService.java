package com.bird.framework.xsy.mall.service;

import com.bird.framework.xsy.mall.entity.CityTime;
import com.bird.framework.xsy.mall.mapper.CityTimeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityTimeService {

    @Autowired
    private CityTimeMapper cityTimeMapper;

    public CityTime selectByTimeCode(String timeCode) {
        return cityTimeMapper.selectByTimeCode(timeCode);
    }

    public CityTime selectByCityCode(String cityCode) {
        return cityTimeMapper.selectByCityCode(cityCode);
    }

    public void save(CityTime cityTime) {
        cityTimeMapper.insert(cityTime);
    }

    public List<CityTime> all() {
        return cityTimeMapper.findAll();
    }

    public List<CityTime> all(String category) {
        return cityTimeMapper.findByCategory(category);
    }
}
