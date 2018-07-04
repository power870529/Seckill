package com.mmall.Seckill.controller;

import com.mmall.Seckill.domain.MiaoshaUser;
import com.mmall.Seckill.domain.OrderInfo;
import com.mmall.Seckill.result.CodeMsg;
import com.mmall.Seckill.result.Result;
import com.mmall.Seckill.service.GoodsService;
import com.mmall.Seckill.service.OrderService;
import com.mmall.Seckill.vo.GoodsVo;
import com.mmall.Seckill.vo.OrderDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    public Result<OrderDetailVo> info(Model model, MiaoshaUser miaoshaUser,
                                      @RequestParam("orderId")long orderId) {
        if(miaoshaUser == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        OrderInfo orderInfo = orderService.getOrderById(orderId);
        if(orderInfo == null) {
            return Result.error(CodeMsg.ORDER_NOT_EXIST);
        }
        long goodsId = orderInfo.getGoodsId();
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        OrderDetailVo vo = new OrderDetailVo();
        vo.setGoods(goodsVo);
        vo.setOrder(orderInfo);
        return Result.succecc(vo);
    }
}
