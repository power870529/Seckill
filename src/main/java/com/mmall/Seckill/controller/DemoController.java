package com.mmall.Seckill.controller;

import com.mmall.Seckill.domain.User;
import com.mmall.Seckill.redis.RedisService;
import com.mmall.Seckill.redis.UserKey;
import com.mmall.Seckill.result.CodeMsg;
import com.mmall.Seckill.result.Result;
import com.mmall.Seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
    }

    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello() {
        return Result.succecc("demo hello");
    }

    @RequestMapping("/helloError")
    @ResponseBody
    public Result<String> helloError() {
        return Result.error(CodeMsg.SERVICE_ERROR);
    }

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model) {
        model.addAttribute("name", "Kevin");
        return "thymeleafDemo";
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> doGet() {

        User user = userService.getById(1);
        return Result.succecc(user);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> doTx() {

        userService.tx();
        return Result.succecc(true);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet() {

        User user = redisService.get(UserKey.getById, "1", User.class);
        return Result.succecc(user);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet() {

        User user = new User();
        user.setId(1);
        user.setName("lixiang");
        Boolean result = redisService.set(UserKey.getById, "1", user);
        return Result.succecc(result);
    }
}
