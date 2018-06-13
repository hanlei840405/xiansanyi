package com.bird.framework.xsy.mall.rest;

import com.bird.framework.xsy.mall.entity.Movie;
import com.bird.framework.xsy.mall.enums.MovieEnum;
import com.bird.framework.xsy.mall.service.MovieService;
import com.bird.framework.xsy.mall.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
