package com.mmall.Seckill.result;

public class Result<T> {

    private int code;
    private String message;
    private T data;

    private Result(T data) {
        this.code = 0;
        this.message = "success";
        this.data = data;
    }

    private Result(CodeMsg codeMsg) {

        if(codeMsg == null) {
            return;
        }
        this.code = codeMsg.getCode();
        this.message = codeMsg.getMessage();
    }

    /**
     * 成功时候的调用
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> succecc(T data) {
        return new Result<T>(data);
    }

    /**
     * 失败时候的调用
     * @param codeMsg
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(CodeMsg codeMsg) {
        return new Result<T>(codeMsg);
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
