package com.anqi.sell.controller;

import com.alibaba.fastjson.JSON;
import com.anqi.sell.VO.ResultVO;
import com.anqi.sell.dto.OrderDTO;
import com.anqi.sell.entity.OrderDetail;
import com.anqi.sell.enums.ResultEnum;
import com.anqi.sell.exception.SellException;
import com.anqi.sell.form.OrderForm;
import com.anqi.sell.service.BuyerService;
import com.anqi.sell.service.OrderService;
import com.anqi.sell.utils.ResultVOUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
/**
 * @author anqi
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    @GetMapping("list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid")String openid,
                                         @RequestParam(value = "page", defaultValue = "0")Integer page,
                                         @RequestParam(value = "size", defaultValue = "10")Integer size) {

        if (StringUtils.isEmpty(openid)) {
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageHelper.startPage(page, size);
        List<OrderDTO> list = orderService.findList(openid, page, size);
        PageInfo<OrderDTO> pageInfo = new PageInfo<>(list);
        return ResultVOUtil.success(pageInfo.getList());
    }

    @GetMapping("detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid")String openid,
                                     @RequestParam("orderId")String orderId) {
        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderDTO);
    }

    @PostMapping("create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm,
                                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】参数不正确，orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetails = JSON.parseArray(orderForm.getItems(), OrderDetail.class);

        if (null == orderDetails) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_ERROR);
        }

        orderDTO.setOrderDetailList(orderDetails);

        orderService.create(orderDTO);

        Map<String, String> map = new HashMap<>();
        map.put("orderId", orderDTO.getOrderId());
        return ResultVOUtil.success(map);

    }

    @PostMapping("cancel")
    public ResultVO cancel(@RequestParam("openid")String openid,
                           @RequestParam("orderId")String orderId){
        buyerService.cancelOrder(openid, orderId);
        return ResultVOUtil.success();
    }
}
