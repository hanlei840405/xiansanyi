package com.bird.framework.xsy.mall.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * mall_movie
 *
 * @author
 */
@Data
public class Movie implements Serializable {
    private static final long serialVersionUID = 4061693388891621069L;
    private Long id;

    private String code;

    /**
     * 地点
     */
    private String location;

    /**
     * 影院
     */
    private String cinema;

    /**
     * 影片
     */
    private String movie;

    /**
     * 上映时间
     */
    private Date time;

    /**
     * 位置
     */
    private String seats;

    /**
     * 买方电话
     */
    private String mobile;

    /**
     * 影票原始价格
     */
    private Integer price;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 总价
     */
    private Integer total;

    /**
     * 折扣价格
     */
    private Integer discount;

    /**
     * 服务费
     */
    private Integer fee;

    /**
     * 买家
     */
    private String buyer;

    /**
     * 订单创建时间
     */
    private Date created;

    /**
     * 支付时间
     */
    private Date paid;

    /**
     * 支付机构流水号
     */
    private String payNo;

    /**
     * 支付金额
     */
    private Integer payment;

    /**
     * 订单由买家推送给卖家的时间
     */
    private Date sent2seller;

    /**
     * 卖家
     */
    private String seller;

    /**
     * 卖家签收订单
     */
    private Date assigned;

    /**
     * 卖家提供给卖家的优惠信息，例如购票码，优惠券码等
     */
    private String payload;

    /**
     * 订单由卖家送审时间
     */
    private Date sent2audit;

    /**
     * 订单由系统发送给买家的时间
     */
    private Date sent2buyer;

    /**
     * 订单取消，只有在未付款时由买家取消或者订单支付后无卖家接单由系统取消
     */
    private Date cancelled;

    /**
     * 取消原因
     */
    private String reason;

    /**
     * 状态，0：取消，1：新建待支付，2：已支付，3：已推送卖家，4：卖家签收，5：卖家推送给平台，待平台审核，6：平台推送给买家，9：订单达成
     */
    private String status;

    private Integer version = 1;

    /**
     * 订单完成时间
     */
    private Date finished;
}