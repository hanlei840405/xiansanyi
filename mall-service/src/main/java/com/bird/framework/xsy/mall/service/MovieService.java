package com.bird.framework.xsy.mall.service;

import com.bird.framework.xsy.mall.entity.Movie;
import com.bird.framework.xsy.mall.enums.MovieEnum;
import com.bird.framework.xsy.mall.mapper.MovieMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class MovieService {

    @Autowired
    private MovieMapper movieMapper;
    @Autowired
    private SequenceService sequenceService;

    public Movie selectById(Long id) {
        return movieMapper.selectByPrimaryKey(id);
    }

    public Movie selectByCode(String code) {
        return movieMapper.selectByCode(code);
    }

    public Page<Movie> page(String buyer, String seller, String mobile, String location, String cinema, String movie, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        Page<Movie> page = (Page<Movie>) movieMapper.selectByMultiParams(buyer, seller, mobile, location, cinema, movie);
        return page;
    }

    public void save(Movie movie) {
        String code = sequenceService.generate("movie", "%08d");
        code = new SimpleDateFormat("yyyyMMdd").format(new Date()).concat(code);
        movie.setCode(code);
        movieMapper.insert(movie);
    }

    @Transactional
    public int pay(Movie movie) {
        int modified = movieMapper.pay(movie);
        if (modified > 0) {
            movie.setStatus(MovieEnum.PAID.getValue());
            movie.setVersion(movie.getVersion() + 1);
            modified = send2seller(movie);
        }
        return modified;
    }

    public int calcel(Movie movie) {
        return movieMapper.cancel(movie);
    }

    public int send2seller(Movie movie) {
        return movieMapper.send2seller(movie);
    }

    public int assign(Movie movie) {
        return movieMapper.assign(movie);
    }

    public int send2audit(Movie movie) {
        return movieMapper.send2audit(movie);
    }

    public int send2buyer(Movie movie) {
        return movieMapper.send2buyer(movie);
    }

    public int finish(Movie movie) {
        return movieMapper.finish(movie);
    }
}
