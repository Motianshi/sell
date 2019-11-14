package com.anqi.sell.service.impl;

import com.anqi.sell.dao.ProductInfoDao;
import com.anqi.sell.dto.CartDTO;
import com.anqi.sell.entity.ProductInfo;
import com.anqi.sell.enums.ProductStatusEnum;
import com.anqi.sell.enums.ResultEnum;
import com.anqi.sell.exception.SellException;
import com.anqi.sell.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

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
    public Integer save(ProductInfo productInfo) {
        return productInfoDao.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        updateStock(cartDTOList,1);
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        updateStock(cartDTOList, -1);
    }

    /**
     *
     * @param cartDTOList
     * @param increase 1 代表增加 -1 代表减少
     */
    private void updateStock(List<CartDTO> cartDTOList, int increase) {
        for (CartDTO cartDTO : cartDTOList) {
            Integer result;
            ProductInfo productInfo = productInfoDao.findById(cartDTO.getProductId());
            if (1 == increase) {
                if (null == productInfo) {
                    LOGGER.error("【增加库存】增加库存失败,商品不存在,cartDTO={}",cartDTO);
                    throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
                }
                result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            } else {
                if (null == productInfo) {
                    LOGGER.error("【减少库存】减少库存失败,商品不存在,cartDTO={}",cartDTO);
                    throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
                }
                result = productInfo.getProductStock() - cartDTO.getProductQuantity();
                if (0 > result) {
                    throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
                }
            }
            productInfoDao.updateStock(productInfo.getProductId(), result);

        }
    }
}
