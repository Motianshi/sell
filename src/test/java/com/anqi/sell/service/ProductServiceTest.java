package com.anqi.sell.service;


import com.anqi.sell.entity.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;


    @Test
    public void testFindAll() {

    }

    @Test
    public void testSave() {

    }

    @Test
    public void testFindById(){
        ProductInfo byId = productService.findById("2");
        System.out.println(byId);
    }

}