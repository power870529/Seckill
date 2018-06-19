package com.mmall.Seckill.result;

public class CodeMsg {

    private int code;
    private String message;

    // 通用异常
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVICE_ERROR = new CodeMsg(500100,"服务端异常");

    // 登录模块500200

    // 商品模块 500300

    // 订单模块 500400

    // 秒杀模块 500500

    private CodeMsg(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
