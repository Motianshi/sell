package com.anqi.sell.dao;

import com.anqi.sell.entity.OrderMaster;

public interface OrderMasterDao {
    int deleteByPrimaryKey(String orderId);

    int insertSelective(OrderMaster record);

    OrderMaster selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(OrderMaster record);
}
