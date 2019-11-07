package com.anqi.sell.controller;

import com.anqi.sell.VO.ResultVO;
import com.anqi.sell.dto.OrderDTO;
import com.anqi.sell.enums.ResultEnum;
import com.anqi.sell.exception.SellException;
import com.anqi.sell.form.OrderForm;
import com.anqi.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    public ResultVO<Map<String,String >> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确，orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerAddress(orderDTO.getBuyerAddress());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setOrderDetailList(orderForm.getItems());

        orderService.cancel(orderDTO);

    }
}
