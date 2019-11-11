package com.anqi.sell.service;

import com.anqi.sell.dto.CartDTO;
import com.anqi.sell.entity.ProductInfo;

import java.util.List;

public interface ProductService {

    /**
     * 查询商品
     * @param productId
     * @return
     */
    ProductInfo findById(String productId);

    /**
     * 查询所有上架的商品
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 保存商品信息
     * @param productInfo
     */
    Integer save(ProductInfo productInfo);

    /**
     * 增加存款
     * @param cartDTOList
     */
    void increaseStock(List<CartDTO> cartDTOList);

    /**
     * 扣减库存
     * @param cartDTOList
     */
    void decreaseStock(List<CartDTO> cartDTOList);

}
