package com.anqi.sell.dao;

import com.anqi.sell.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryDao {

    ProductCategory findByCategoryType(Integer type);

    ProductCategory findByCategoryName(String name);

    ProductCategory findById(Integer categoryId);

    List<ProductCategory> findListByTypeList(List<Integer> typeList);

    Long save(ProductCategory productCategory);

    Long update(ProductCategory productCategory);

    Long deleteByCategoryType(Integer type);



}
