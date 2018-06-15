package com.bird.framework.xsy.mall.service;

import com.bird.framework.xsy.mall.entity.TimeCinema;
import com.bird.framework.xsy.mall.mapper.TimeCinemaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimeCinemaService {

    @Autowired
    private TimeCinemaMapper timeCinemaMapper;

    public void save(TimeCinema timeCinema) {
        timeCinemaMapper.insert(timeCinema);
    }

    public TimeCinema selectByCinema(String cinema) {
        return timeCinemaMapper.selectByCinema(cinema);
    }

    public List<TimeCinema> selectByDistrict(String districtCode) {
        return timeCinemaMapper.selectByDistrict(districtCode);
    }
}
