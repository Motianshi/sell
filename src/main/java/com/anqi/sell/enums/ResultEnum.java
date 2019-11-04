package com.anqi.sell.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PRODUCT_NOT_EXIST(0, "商品不存在"),

    PRODUCT_STOCK_ERROR(1, "库存不足"),

    ORDER_NOT_EXIST(3, "订单不存在"),

    ORDERDETAIL_NOT_EXIST (4, "订单详情不存在"),

    ORDER_STATUS_ERROR (5, "订单状态异常"),

    ORDER_UPDATE_ERROR (6, "订单更新失败"),

    ORDER_DETAIL_EMPTY (7, "订单详情为空"),


    ;

    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
