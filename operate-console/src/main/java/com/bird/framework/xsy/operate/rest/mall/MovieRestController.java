package com.bird.framework.xsy.operate.rest.mall;

import com.bird.framework.xsy.mall.entity.Movie;
import com.bird.framework.xsy.mall.service.MovieService;
import com.github.pagehelper.Page;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("rest/mall/movie")
public class MovieRestController {
    @Autowired
    private MovieService movieService;

    @RequestMapping("/id")
    public Movie id(Long id) {
        return movieService.selectById(id);
    }

    @RequestMapping("/page")
    public Map<String, Object> page(String buyer, String seller, String mobile, String location, String cinema, String movie, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "20") Integer rows) {
        Page<Movie> menus = movieService.page(buyer, seller, mobile, location, cinema, movie, page, rows);
        Map<String, Object> result = Maps.newHashMap();
        result.put("total", menus.getTotal());
        result.put("rows", menus.getResult());
        return result;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public int save(Movie movie) {
        movieService.save(movie);
        return HttpStatus.OK.value();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public int delete(Long id) {
        movieService.delete(id);
        return HttpStatus.OK.value();
    }
}
