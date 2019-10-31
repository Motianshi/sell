package com.anqi.sell.service;

import com.anqi.sell.dao.OrderDetailDao;
import com.anqi.sell.dao.OrderMasterDao;
import com.anqi.sell.dao.ProductCategoryDao;
import com.anqi.sell.dao.ProductInfoDao;
import com.anqi.sell.entity.ProductCategory;
import com.anqi.sell.entity.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.anqi.sell.dao")
public class DaoTest {

    @Autowired
    ProductCategoryDao productCategoryDao;

    @Autowired
    ProductInfoDao productInfoDao;

    @Autowired
    OrderDetailDao orderDetailDao;

    @Autowired
    OrderMasterDao orderMasterDao;

    @Test
    public void testProductCategoryDao(){
//        System.out.println(1);
//        ProductCategory productCategory = new ProductCategory();
//        productCategory.setCategoryId(3);
//        productCategory.setCategoryName("零食");
//        productCategory.setCategoryType(2);
//        productCategoryDao.save(productCategory);


        ProductCategory byCategoryType = productCategoryDao.findByCategoryType(1);
        System.out.println(byCategoryType);

        ProductCategory findById = productCategoryDao.findById(1);
        System.out.println(findById);

        ProductCategory findByCategoryName = productCategoryDao.findByCategoryName("熟食");
        System.out.println(findByCategoryName);

//        ProductCategory productCategory = new ProductCategory();
//        productCategory.setCategoryId(2);
//        productCategory.setCategoryName("水果类型");
//        productCategoryDao.update(productCategory);


        List<ProductCategory> listByTypeList = productCategoryDao.findListByTypeList(Arrays.asList(1, 2));
        System.out.println(listByTypeList);
    }

    @Test
    public void testProductInfoDao(){
//        ProductInfo productInfo = new ProductInfo();
//        productInfo.setProductId("199");
//        productInfo.setCategoryType(1);
//        productInfo.setProductDescription("nice");
//        productInfo.setProductStatus(1);
//        productInfo.setProductStock(100);
//        productInfo.setProductIcon("icon");
//        productInfo.setProductName("红烧肉");
//        productInfo.setProductPrice(new BigDecimal("100"));
//        productInfoDao.save(productInfo);

        ProductInfo byId = productInfoDao.findById("1");
        System.out.println(byId);

        List<ProductInfo> byProductStatus = productInfoDao.findByProductStatus(1);
        System.out.println(byProductStatus);
    }

    @Test
    public void testOrderDetailDao(){

    }

    @Test
    public void testOrderMasterDao(){

    }
}
