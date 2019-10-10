package com.anqi.sell.service;

import com.anqi.sell.entity.ProductCategory;

import java.util.List;

public interface CategoryService {

    List<ProductCategory> findAll();

    ProductCategory findByCategoryId(Integer categoryId);

    List<ProductCategory> findListByCategoryTypeList(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
