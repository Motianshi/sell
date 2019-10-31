package com.anqi.sell.service;

import com.anqi.sell.dto.CartDTO;
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

    Integer save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);

}
