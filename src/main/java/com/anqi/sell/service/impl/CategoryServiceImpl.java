package com.anqi.sell.service.impl;

import com.anqi.sell.dao.ProductCategoryDao;
import com.anqi.sell.entity.ProductCategory;
import com.anqi.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    ProductCategoryDao productCategoryDao;

    @Override
    public List<ProductCategory> findAll() {
        return null;
    }

    @Override
    public ProductCategory findByCategoryId(Integer categoryId) {
        return productCategoryDao.findById(categoryId);
    }

    @Override
    public List<ProductCategory> findListByCategoryTypeList(List<Integer> categoryTypeList) {
        return productCategoryDao.findListByTypeList(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return null;
    }
}
