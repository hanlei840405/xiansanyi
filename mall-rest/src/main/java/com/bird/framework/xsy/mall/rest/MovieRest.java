package com.bird.framework.xsy.mall.rest;

import com.bird.framework.xsy.mall.entity.CityTime;
import com.bird.framework.xsy.mall.entity.Movie;
import com.bird.framework.xsy.mall.entity.TimeCinema;
import com.bird.framework.xsy.mall.enums.MovieEnum;
import com.bird.framework.xsy.mall.service.CityTimeService;
import com.bird.framework.xsy.mall.service.MemberService;
import com.bird.framework.xsy.mall.service.MovieService;
import com.bird.framework.xsy.mall.service.TimeCinemaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.WebRequest;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/movie")
public class MovieRest {
    @Autowired
    private MovieService movieService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private TimeCinemaService timeCinemaService;
    @Autowired
    private CityTimeService cityTimeService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @RequestMapping("/cinema/{district}")
    public List<TimeCinema> cinemas(@PathVariable("district") String district) {
        CityTime cityTime = cityTimeService.selectByCityCode(district);
        if (cityTime != null) {
            return timeCinemaService.selectByDistrict(cityTime.getTimeCode());
        }
        return null;
    }

    @RequestMapping("/film/{cinema}")
    public List<Map<String, Object>> films(@PathVariable("cinema") String cinema) throws IOException {
        TimeCinema timeCinema = timeCinemaService.selectByCinema(cinema);
        if (timeCinema != null) {
            RBucket<List<Map<String, Object>>> bucket = redissonClient.getBucket("film.cinema." + cinema);
            bucket.delete();
            if (!bucket.isExists()) {
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://service.theater.mtime.com/Cinema.api?Ajax_CallBack=true&Ajax_CallBackType=Mtime.Cinema.Services&Ajax_CallBackMethod=GetOnlineMoviesInTheater&Ajax_CallBackArgument0=" + cinema, String.class);
                String body = responseEntity.getBody().replace("var getGetOnlineMoviesInTheaterResult = ", "");
                Map<String, Object> content = objectMapper.readValue(body, Map.class);
                Map<String, Object> info = (Map<String, Object>) content.get("value");
                List<Map<String, Object>> movies = (List<Map<String, Object>>) info.get("movies");
                bucket.set(movies);
                return movies;
            }
            return bucket.get();
        }
        return null;
    }

    @RequestMapping("/times/{cinema}/{movieId}")
    public List<Map<String, Object>> times(@PathVariable("cinema") String cinema, @PathVariable("movieId") int movieId) throws IOException {
        TimeCinema timeCinema = timeCinemaService.selectByCinema(cinema);
        if (timeCinema != null) {
            RBucket<List<Map<String, Object>>> bucket = redissonClient.getBucket("film.cinema." + cinema);
            if (!bucket.isExists()) {
                return null;
            }
            List<Map<String, Object>> results = bucket.get();
            for (Map<String, Object> result : results) {
                int cached = (int) result.get("movieId");

                if (movieId == cached) { // 存在该影片， 允许获取上映场次时间
                    RBucket<List<Map<String, Object>>> timeBucket = redissonClient.getBucket("film.time." + cinema + "." + movieId); // 获取每天时间的缓存，减少调用服务器次数
                    if (!timeBucket.isExists()) {
                        RestTemplate restTemplate = new RestTemplate();
                        ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://service.theater.mtime.com/Cinema.api?Ajax_CallBack=true&Ajax_CallBackType=Mtime.Cinema.Services&Ajax_CallBackMethod=GetOnlineTicketShowtime&Ajax_CallBackArgument0=" + movieId + "&Ajax_CallBackArgument1=" + cinema, String.class);
                        String body = responseEntity.getBody().replace("var getOnlineTicketShowtimeResult = ", "");
                        Map<String, Object> content = objectMapper.readValue(body, Map.class);
                        Map<String, Object> info = (Map<String, Object>) content.get("value");
                        List<Map<String, Object>> dateShowtimes = (List<Map<String, Object>>) info.get("dateShowtimes");
                        timeBucket.set(dateShowtimes);
                        Calendar calendar = Calendar.getInstance();
                        calendar.add(Calendar.DATE, 1);
                        calendar.set(Calendar.HOUR, 0);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);
                        timeBucket.expireAt(calendar.getTime()); // 每天凌晨0点失效
                        return dateShowtimes;
                    } else {
                        return timeBucket.get();
                    }
                }
            }
        }
        return null;
    }

    /**
     * 创建订单
     *
     * @param movie
     * @return
     */
    @PostMapping("/create")
    public Map<String, Object> create(Movie movie) {
        movie.setCreated(new Date());
        movie.setStatus("1");
        Map<String, Object> response = new HashMap<>();
        try {
            movie.setStatus(MovieEnum.CREATED.getValue());
            movieService.save(movie);
            response.put("code", HttpStatus.OK.value());
        } catch (Exception e) {
            log.error(e.getMessage());
            response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("msg", e.getMessage());
        } finally {
            return response;
        }
    }

    /**
     * 获取订单
     *
     * @param code
     * @return
     */
    @PostMapping("/code/{code}")
    public Map<String, Object> code(@PathVariable("code") String code) {
        Map<String, Object> response = new HashMap<>();
        String username = SecurityContextHolder.getContext().getAuthentication().getName(); // 之后需要更换成微信支付传入的openId，再换取username
        Movie movie = movieService.selectByCode(code);
        try {
            if (username.equals(movie.getBuyer())) {
                response.put("code", HttpStatus.OK.value());
                response.put("payload", movie);
            } else {
                throw new Exception("该订单不属于您");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("msg", e.getMessage());
        } finally {
            return response;
        }
    }

    /**
     * 支付订单
     *
     * @param code
     * @param payNo
     * @param payment
     * @return
     */
    @PostMapping("/pay")
    public Map<String, Object> pay(String code, String payNo, int payment) {
        Map<String, Object> response = new HashMap<>();
        String username = SecurityContextHolder.getContext().getAuthentication().getName(); // 之后需要更换成微信支付传入的openId，再换取username
        Movie movie = movieService.selectByCode(code);
        try {
            if (username.equals(movie.getBuyer())) {
                // 判断订单是否属于该买家
                movie.setPayNo(payNo);
                movie.setPayment(payment);
                movie.setStatus(MovieEnum.PAID.getValue());
                int modified = movieService.pay(movie);
                if (modified > 0) {
                    movie.setStatus(MovieEnum.SENT2SELLER.getValue());
                    rabbitTemplate.convertAndSend("movie", "send.seller", movie);
                    response.put("code", HttpStatus.OK.value());
                } else {
                    throw new Exception("该订状态已过期");
                }
            } else {
                throw new Exception("该订单不属于您");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("msg", e.getMessage());
        } finally {
            return response;
        }
    }

    /**
     * 卖家签收
     *
     * @param code
     * @return
     */
    @PostMapping("/assign")
    public Map<String, Object> assign(String code) {
        Map<String, Object> response = new HashMap<>();
        String username = SecurityContextHolder.getContext().getAuthentication().getName(); // 之后需要更换成微信支付传入的openId，再换取username
        Movie movie = movieService.selectByCode(code);
        try {
            if (StringUtils.isEmpty(movie.getSeller())) { // 未被签收
                // 判断订单是否属于该买家
                movie.setSeller(username);
                movie.setStatus(MovieEnum.ASSIGNED.getValue());
                int modified = movieService.assign(movie);
                if (modified > 0) {
                    rabbitTemplate.convertAndSend("movie", "send.assign", movie);
                    response.put("code", HttpStatus.OK.value());
                } else {
                    throw new Exception("该订状态已过期");
                }
            } else {
                throw new Exception("该订单不属于您");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("msg", e.getMessage());
        } finally {
            return response;
        }
    }

    /**
     * 卖家反馈订单所需信息给平台审核
     *
     * @param payload
     * @return
     */
    @PostMapping("/feedback")
    public Map<String, Object> feedback(String code, String payload) {
        Map<String, Object> response = new HashMap<>();
        String username = SecurityContextHolder.getContext().getAuthentication().getName(); // 之后需要更换成微信支付传入的openId，再换取username
        Movie movie = movieService.selectByCode(code);
        try {
            if (username.equals(movie.getSeller())) {
                // 判断该订单是否由此卖家签收
                movie.setPayload(payload);
                movie.setStatus(MovieEnum.AUDIT.getValue());
                int modified = movieService.send2audit(movie);
                if (modified > 0) {
                    rabbitTemplate.convertAndSend("movie", "send.audit", movie);
                    response.put("code", HttpStatus.OK.value());
                } else {
                    throw new Exception("该订状态已过期");
                }
            } else {
                throw new Exception("该订单不属于您");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            response.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.put("msg", e.getMessage());
        } finally {
            return response;
        }
    }

    @InitBinder
    public void initBinder(WebDataBinder binder, WebRequest request) {

        //转换日期
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
    }
}
