package com.anqi.sell.dao;

import com.anqi.sell.entity.ProductInfo;

import java.util.List;

public interface ProductInfoDao {

    ProductInfo findById(String productId);

    List<ProductInfo> findByProductStatus(Integer status);

    Integer save(ProductInfo productInfo);


}
