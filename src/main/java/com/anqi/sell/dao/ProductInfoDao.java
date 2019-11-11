package com.anqi.sell.dao;

import com.anqi.sell.entity.ProductInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author anqi
 */
public interface ProductInfoDao {

    /**
     * 根据id查询商品详情
     * @param productId
     * @return
     */
    ProductInfo findById(String productId);

    /**
     * 根据商品状态查询
     * @param status
     * @return
     */
    List<ProductInfo> findByProductStatus(Integer status);

    /**
     * 保存商品信息
     * @param productInfo
     * @return
     */
    Integer save(ProductInfo productInfo);

    /**
     * 更新库存
     * @param productId
     * @param productStock
     * @return
     */
    Integer updateStock(@Param("productId") String productId, @Param("productStock") int productStock);

}
