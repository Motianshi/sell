package com.anqi.sell.controller;

import com.anqi.sell.VO.ProductInfoVO;
import com.anqi.sell.VO.ProductVO;
import com.anqi.sell.VO.ResultVO;
import com.anqi.sell.entity.ProductInfo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @GetMapping("list")
    public ResultVO list() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");

        ProductInfoVO productInfoVO = new ProductInfoVO();

        ProductVO productVO = new ProductVO();
        productVO.setProductInfoVOList(Arrays.asList(productInfoVO));

        resultVO.setData(Arrays.asList(productVO));
        return resultVO;
    }
}
