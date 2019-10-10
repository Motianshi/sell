package com.anqi.sell.service;

import com.anqi.sell.entity.ProductInfo;

import java.util.List;

public interface ProductService {

    ProductInfo findById(String productId);

    /**
     * 查询所有上架的商品
     * @return
     */
    List<ProductInfo> findUpAll();

//    List<ProductInfo> findAll(page);

    ProductInfo save(ProductInfo productInfo);

    //加库存

    //减库存

}
