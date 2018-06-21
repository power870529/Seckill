package com.mmall.Seckill.controller;

import com.mmall.Seckill.redis.RedisService;
import com.mmall.Seckill.result.Result;
import com.mmall.Seckill.service.MiaoshaUserService;
import com.mmall.Seckill.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
        log.info(loginVo.toString());

        // 参数校验
//        if(StringUtils.isEmpty(loginVo.getMobile())) {
//            return Result.error(CodeMsg.MOBILE_EMPTY);
//        }
//        if(!ValidatorUtil.isMobile(loginVo.getMobile())) {
//            return Result.error(CodeMsg.MOBILE_ERROR);
//        }
//        if(StringUtils.isEmpty(loginVo.getPassword())) {
//            return Result.error(CodeMsg.PASSWORD_EMPTY);
//        }

        // 登录
        miaoshaUserService.login(response, loginVo);
        return Result.succecc(true);
    }
}
