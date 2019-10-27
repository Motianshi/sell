package com.anqi.sell.exception;

import com.anqi.sell.enums.ResultEnum;

public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum productResultEnum) {
        super(productResultEnum.getMsg());
        this.code = productResultEnum.getCode();
    }
}
