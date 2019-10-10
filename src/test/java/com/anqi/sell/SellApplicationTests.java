package com.anqi.sell;

import com.anqi.sell.entity.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

public class SellApplicationTests {

    Logger logger = LoggerFactory.getLogger(SellApplicationTests.class);

    @Test
    public void contextLoads() {
        logger.info("the name:{}","anqi");
        logger.debug("the debug password:{}","11223");
        logger.error("thr erro is e");
        logger.warn("warn--------");

        ProductCategory p = new ProductCategory();
        p.setCategoryId(1);
        p.setCategoryName("爆款");
        System.out.println(p);
    }

}
