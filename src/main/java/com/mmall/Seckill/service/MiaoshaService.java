package com.mmall.Seckill.service;

import com.mmall.Seckill.domain.MiaoshaUser;
import com.mmall.Seckill.domain.OrderInfo;
import com.mmall.Seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser miaoshaUser, GoodsVo goods) {
        // 减库存
        goodsService.reduceStock(goods);

        // 下订单, 写入秒杀订单
        OrderInfo orderInfo = orderService.createOrder(miaoshaUser, goods);

        return orderInfo;
    }
}
