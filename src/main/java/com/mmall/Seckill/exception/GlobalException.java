package com.mmall.Seckill.exception;

import com.mmall.Seckill.result.CodeMsg;

public class GlobalException extends RuntimeException {

    private CodeMsg cm;

    public GlobalException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }
}
