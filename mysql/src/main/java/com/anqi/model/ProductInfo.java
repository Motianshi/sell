package com.anqi.model;

import java.math.BigDecimal;
import java.util.Date;

public class ProductInfo {
    private String product_id;

    private String product_name;

    private BigDecimal product_price;

    private Integer product_stock;

    private String product_desc;

    private String product_icon;

    private Integer category_type;

    private Date create_time;

    private Date update_time;

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id == null ? null : product_id.trim();
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name == null ? null : product_name.trim();
    }

    public BigDecimal getProduct_price() {
        return product_price;
    }

    public void setProduct_price(BigDecimal product_price) {
        this.product_price = product_price;
    }

    public Integer getProduct_stock() {
        return product_stock;
    }

    public void setProduct_stock(Integer product_stock) {
        this.product_stock = product_stock;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc == null ? null : product_desc.trim();
    }

    public String getProduct_icon() {
        return product_icon;
    }

    public void setProduct_icon(String product_icon) {
        this.product_icon = product_icon == null ? null : product_icon.trim();
    }

    public Integer getCategory_type() {
        return category_type;
    }

    public void setCategory_type(Integer category_type) {
        this.category_type = category_type;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Date getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Date update_time) {
        this.update_time = update_time;
    }
}