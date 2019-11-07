package com.anqi.sell.dao;

import com.anqi.sell.entity.OrderMaster;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderMasterDao {

    int deleteByPrimaryKey(String orderId);

    int insertSelective(OrderMaster record);
    
    OrderMaster selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(OrderMaster record);

    List<OrderMaster> findByBuyerId(String buyerOpenId);

    /**
     * 修改订单状态
     * @param orderId
     * @param orderStatus
     * @return
     */
    int changeOrderStatus(@Param("orderId") String orderId, @Param("orderStatus") int orderStatus);

    /**
     * 修改支付状态
     * @param orderId
     * @param payStatus
     * @return
     */
    int changePayStatus(@Param("orderId") String orderId, @Param("payStatus") int payStatus);
}
