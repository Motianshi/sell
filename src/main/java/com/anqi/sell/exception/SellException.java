package com.anqi.sell.exception;

import com.anqi.sell.enums.ResultEnum;

public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public SellException(int code, String msg) {
        super(msg);
        this.code = code;
    }
}
