package com.mmall.Seckill.service;

import com.mmall.Seckill.dao.MiaoshaUserDao;
import com.mmall.Seckill.domain.MiaoshaUser;
import com.mmall.Seckill.exception.GlobalException;
import com.mmall.Seckill.redis.MiaoshaUserKey;
import com.mmall.Seckill.redis.RedisService;
import com.mmall.Seckill.result.CodeMsg;
import com.mmall.Seckill.util.MD5Util;
import com.mmall.Seckill.util.UUIDUtil;
import com.mmall.Seckill.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaUserService {

    public static final String  COOKI_NAME_TOKEN = "token";

    @Autowired
    MiaoshaUserDao miaoshaUserDao;

    @Autowired
    RedisService redisService;

    public MiaoshaUser getById(long id) {
        return miaoshaUserDao.getById(id);
    }


    public boolean login(HttpServletResponse response, LoginVo loginVo) {

        if(loginVo == null) {
            throw new GlobalException(CodeMsg.SERVICE_ERROR);
        }

        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();
        MiaoshaUser user = getById(Long.parseLong(mobile));
        if(user == null) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        String dbPass = user.getPassword();
        String dbSalt = user.getSalt();
        String calcPass = MD5Util.formPass2DbPass(password, dbSalt);
        if(!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }

        // 生成Cookie
        String token = UUIDUtil.uuid();
        redisService.set(MiaoshaUserKey.token, token, user);
        Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
        return true;

    }

    public MiaoshaUser getByToken(String token) {

        if(StringUtils.isEmpty(token)) {
            return null;
        }
        return redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
    }
}