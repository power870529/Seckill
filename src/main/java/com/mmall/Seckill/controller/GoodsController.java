package com.mmall.Seckill.controller;

import com.mmall.Seckill.domain.MiaoshaUser;
import com.mmall.Seckill.redis.RedisService;
import com.mmall.Seckill.service.MiaoshaUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    private static Logger log = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/to_list")
    public String toList(
            @CookieValue(value = MiaoshaUserService.COOKI_NAME_TOKEN, required = false) String cookieToken,
            @RequestParam(value = MiaoshaUserService.COOKI_NAME_TOKEN, required = false) String paramToken) {
        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)) {
            return "login";
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        MiaoshaUser miaoshaUser = miaoshaUserService.getByToken(token);
        log.info(miaoshaUser.getNickname());
        return "goods_list";
    }

}
