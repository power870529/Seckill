package com.mmall.Seckill.controller;

import com.mmall.Seckill.domain.MiaoshaOrder;
import com.mmall.Seckill.domain.MiaoshaUser;
import com.mmall.Seckill.domain.OrderInfo;
import com.mmall.Seckill.result.CodeMsg;
import com.mmall.Seckill.result.Result;
import com.mmall.Seckill.service.GoodsService;
import com.mmall.Seckill.service.MiaoshaService;
import com.mmall.Seckill.service.OrderService;
import com.mmall.Seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @RequestMapping(value = "/do_miaosha", method = RequestMethod.POST)
    @ResponseBody
    public Result<OrderInfo> doMiaosha(Model model, MiaoshaUser miaoshaUser, @RequestParam("goodsId")long goodsId) {

        model.addAttribute("user", miaoshaUser);
        if(miaoshaUser == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        // 判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if(stock <= 0) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        // 判断是否秒杀到了
        MiaoshaOrder miaoshaOrder = orderService.getMiaoshaOrderByUserIdGoodsId(miaoshaUser.getId(), goodsId);
        if(miaoshaOrder != null) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

        // 减库存，下订单，写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(miaoshaUser, goods);

        return Result.succecc(orderInfo);
    }
}
