package com.yu.scloud.baseframe.frame.dao;

import com.anqi.model.ProductInfo;

public interface ProductInfoMapper {
    int deleteByPrimaryKey(String product_id);

    int insert(ProductInfo record);

    int insertSelective(ProductInfo record);

    ProductInfo selectByPrimaryKey(String product_id);

    int updateByPrimaryKeySelective(ProductInfo record);

    int updateByPrimaryKey(ProductInfo record);
}