package com.anqi.sell.service;


import com.anqi.sell.dto.OrderDTO;
import com.anqi.sell.entity.OrderDetail;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.anqi.sell.dao")
public class OrderServiceTest {

    @Autowired
    OrderService orderService;

    @Test
    public void testFindByOpenId(){
        PageInfo<OrderDTO> list = orderService.findList("112", 0, 2);
        list.getList().forEach(e -> System.out.println(e));
    }

    @Test
    public void testCancel(){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("1");
        orderDTO.setBuyerName("anqi");
        orderDTO.setBuyerAddress("澳尼路");
        orderDTO.setBuyerPhone("15503613208");
        orderDTO.setBuyerOpenid("112");
        orderDTO.setOrderAmount(new BigDecimal("996"));
        orderDTO.setOrderStatus(2);
        orderDTO.setPayStatus(0);
        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("1");
        orderDetail.setOrderId("1");
        orderDetail.setProductIcon("icon");
        orderDetail.setProductId("1");
        orderDetail.setProductName("红烧肉");
        orderDetail.setProductPrice(new BigDecimal("100"));
        orderDetail.setProductQuantity(2);


        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setDetailId("2");
        orderDetail2.setOrderId("1");
        orderDetail2.setProductIcon("icon");
        orderDetail2.setProductId("2");
        orderDetail2.setProductName("猪蹄");
        orderDetail2.setProductPrice(new BigDecimal("20"));
        orderDetail2.setProductQuantity(2);
        orderDetails.add(orderDetail);
        orderDetails.add(orderDetail2);

        orderDTO.setOrderDetailList(orderDetails);
        orderService.cancel(orderDTO);
    }


    @Test
    public void testFinish() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("1");
        orderService.finish(orderDTO);
    }

    @Test
    public void testPaid(){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setOrderId("1");
        orderService.paid(orderDTO);
    }

}
