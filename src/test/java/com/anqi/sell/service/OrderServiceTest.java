package com.anqi.sell.service;


import com.anqi.sell.dto.OrderDTO;
import com.github.pagehelper.PageInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

}
