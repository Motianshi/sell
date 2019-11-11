package com.anqi.sell.dao;

import com.anqi.sell.entity.OrderDetail;

import java.util.List;

public interface OrderDetailDao {

    int deleteByPrimaryKey(String detailId);

    int insertSelective(OrderDetail record);

    OrderDetail selectByPrimaryKey(String detailId);

    int updateByPrimaryKeySelective(OrderDetail record);

    /**
     * 根据订单id查询oderDetail
     * @param orderId
     * @return
     */
    List<OrderDetail> findListByOrderId(String orderId);
}
