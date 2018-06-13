package com.bird.framework.xsy.mall.consumer;

import com.bird.framework.xsy.mall.entity.Movie;
import com.bird.framework.xsy.mall.enums.MovieEnum;
import com.bird.framework.xsy.mall.service.MovieService;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MovieConsumer {
    @Autowired
    private MovieService movieService;

    /**
     * 消息推送给卖家
     *
     * @param movie
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "mall", durable = "true"),
            exchange = @Exchange(value = "movie", type = "topic"), key = "send.seller"))
    public void send2seller(Movie movie) {
        // todo 发送微信消息给卖家签收
    }

    /**
     * 订单签收反馈给买家
     *
     * @param movie
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "mall", durable = "true"),
            exchange = @Exchange(value = "movie", type = "topic"), key = "send.assign"))
    public void sendAssign(Movie movie) {
        // todo 发送微信消息通知买家订单被签收
    }

    /**
     * 消息推送给平台审核
     *
     * @param movie
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "mall", durable = "true"),
            exchange = @Exchange(value = "movie", type = "topic"), key = "send.audit"))
    public void send2audit(Movie movie) {
        // todo 发送微信消息给卖家签收
    }

    /**
     * 平台允许发送给买家
     *
     * @param movie
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "mall", durable = "true"),
            exchange = @Exchange(value = "movie", type = "topic"), key = "audit.pass"))
    public void pass(Movie movie) {
        movie.setStatus(MovieEnum.SENT2BUYER.getValue());
        movieService.send2buyer(movie);
        // todo 发送微信消息给买家查收信息
    }

    /**
     * 卖家发送垃圾数据，重置状态至已付款，并重新推送消息给卖家
     *
     * @param movie
     */
    @RabbitListener(bindings = @QueueBinding(value = @Queue(value = "mall", durable = "true"),
            exchange = @Exchange(value = "movie", type = "topic"), key = "audit.refuse"))
    public void refuse(Movie movie) {
        movie.setPayload(null);
        movie.setSeller(null);
        movie.setStatus(MovieEnum.PAID.getValue());
        movie.setAssigned(null);
        movieService.send2seller(movie);
        // todo 发送微信消息给买家查收信息
    }
}
