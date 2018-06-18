package com.bird.framework.xsy.mall.service;

import com.bird.framework.xsy.mall.entity.JdCinema;
import com.bird.framework.xsy.mall.mapper.JdCinemaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Service
public class JdCinemaService {

    @Autowired
    private JdCinemaMapper jdCinemaMapper;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<JdCinema> findByRegion(String regionCode) {
        return jdCinemaMapper.findByRegion(regionCode);
    }

    public void save(JdCinema jdCinema) {
        jdCinemaMapper.insert(jdCinema);
    }

    public void save(List<JdCinema> jdCinemas) {
        jdbcTemplate.batchUpdate("insert into mall_jd_cinema (city_code, region_code, code, name, address) values (?,?,?,?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setString(1, jdCinemas.get(i).getCityCode());
                preparedStatement.setString(2, jdCinemas.get(i).getRegionCode());
                preparedStatement.setString(3, jdCinemas.get(i).getCode());
                preparedStatement.setString(4, jdCinemas.get(i).getName());
                preparedStatement.setString(5, jdCinemas.get(i).getAddress());
            }

            @Override
            public int getBatchSize() {
                return jdCinemas.size();
            }
        });
    }
}
