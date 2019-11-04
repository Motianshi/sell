package com.anqi.sell.dao;

import com.anqi.sell.entity.OrderMaster;

import java.util.List;

public interface OrderMasterDao {
    int deleteByPrimaryKey(String orderId);

    int insertSelective(OrderMaster record);

    OrderMaster selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(OrderMaster record);

    List<OrderMaster> findByBuyerId(String buyerOpenId);
}
