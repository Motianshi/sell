package com.anqi.sell.service;

import com.anqi.sell.dto.OrderDTO;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface OrderService {
    /** 创建订单. */
    OrderDTO create(OrderDTO orderDTO);

    /** 查询单个订单. */
    OrderDTO findOne(String orderId);

    /** 查询订单列表. */
    List<OrderDTO> findList(String buyerOpenid, int pageNo, int pageSize);

    /** 取消订单. */
    OrderDTO cancel(OrderDTO orderDTO);

    /** 完成订单. */
    OrderDTO finish(OrderDTO orderDTO);

    /** 支付订单. */
    OrderDTO paid(OrderDTO orderDTO);

    /** 查询订单列表. */
    PageInfo<OrderDTO> findList(int pageNo, int pageSize);
}
