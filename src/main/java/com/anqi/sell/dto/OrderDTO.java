package com.anqi.sell.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.anqi.sell.enums.OrderStatusEnum;
import com.anqi.sell.enums.PayStatusEnum;
import com.anqi.sell.entity.OrderDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {

    /** 订单id. */
    private String orderId;

    /** 买家名字. */
    private String buyerName;

    /** 买家手机号. */
    private String buyerPhone;

    /** 买家地址. */
    private String buyerAddress;

    /** 买家微信Openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态, 默认为0新下单. */
    private Integer orderStatus;

    /** 支付状态, 默认为0未支付. */
    private Integer payStatus ;

    /** 创建时间. */
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    /** 更新时间. */
    private Date updateTime;

    private List<OrderDetail> orderDetailList;

    public OrderDTO() {
        orderStatus =  OrderStatusEnum.NEW.getCode();
        payStatus = PayStatusEnum.WAIT.getCode();
    }
}
