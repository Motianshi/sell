package com.anqi.sell.service.impl;

import com.anqi.sell.dao.ProductInfoDao;
import com.anqi.sell.entity.ProductInfo;
import com.anqi.sell.enums.ProductStatusEnum;
import com.anqi.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoDao productInfoDao;

    @Override
    public ProductInfo findById(String productId) {
        return productInfoDao.findById(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoDao.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoDao.save(productInfo);
    }
}
