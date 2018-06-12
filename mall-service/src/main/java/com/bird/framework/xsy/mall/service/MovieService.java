package com.bird.framework.xsy.mall.service;

import com.bird.framework.xsy.mall.entity.Movie;
import com.bird.framework.xsy.mall.mapper.MovieMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MovieService {

    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    @Qualifier("mallSequenceService")
    private SequenceService sequenceService;

    public Movie selectById(Long id) {
        return movieMapper.selectByPrimaryKey(id);
    }

    public Page<Movie> page(String buyer, String seller, String mobile, String location, String cinema, String movie, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        Page<Movie> page = (Page<Movie>) movieMapper.selectByMultiParams(buyer, seller, mobile, location, cinema, movie);
        return page;
    }

    public void save(Movie movie) {
        if (movie.getId() != null) {
            // 执行更新
            movieMapper.updateByPrimaryKeySelective(movie);
        } else {
            // 执行新增
            String code = sequenceService.generate("movie", "%08d");
            code = new SimpleDateFormat("yyyyMMdd").format(new Date()).concat(code);
            movie.setCode(code);
            movieMapper.insert(movie);
        }
    }

    public void delete(Long id) {
        movieMapper.deleteByPrimaryKey(id);
    }
}
