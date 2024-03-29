package com.anqi.sell.service.impl;

import com.anqi.sell.dao.OrderDetailDao;
import com.anqi.sell.dao.OrderMasterDao;
import com.anqi.sell.dto.CartDTO;
import com.anqi.sell.dto.OrderDTO;
import com.anqi.sell.entity.OrderDetail;
import com.anqi.sell.entity.OrderMaster;
import com.anqi.sell.entity.ProductInfo;
import com.anqi.sell.enums.OrderStatusEnum;
import com.anqi.sell.enums.PayStatusEnum;
import com.anqi.sell.enums.ResultEnum;
import com.anqi.sell.exception.SellException;
import com.anqi.sell.service.OrderService;
import com.anqi.sell.service.ProductService;
import com.anqi.sell.utils.UUIDUtil;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author anqi
 */
@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private OrderMasterDao orderMasterDao;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {

        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        String orderId = UUIDUtil.getUUID();

        //1. 查询商品 (数量,价格)
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findById(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //2. 计算总价
            //orderDetail.getProductPrice() * orderDetail.getProductQuantity()
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //3. 写入订单详情
            BeanUtils.copyProperties(productInfo,orderDetail);
            //设置 productIcon id name 和 price
            orderDetail.setDetailId(UUIDUtil.getUUID());
            orderDetail.setOrderId(orderId);
            orderDetailDao.insertSelective(orderDetail);

        }

        //3. 写入订单
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterDao.insertSelective(orderMaster);

        //4. 扣减库存
        List<CartDTO> cartDTOList = new ArrayList<>();
        orderDTO.getOrderDetailList().stream()
                .map(e -> cartDTOList.add(new CartDTO(e.getProductId(), e.getProductQuantity())))
                .collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {

        OrderMaster orderMaster = orderMasterDao.selectByPrimaryKey(orderId);
        if (null == orderMaster) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        List<OrderDetail> orderDetailList = orderDetailDao.findListByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }

        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    @Override
    public List<OrderDTO> findList(String buyerOpenid, int pageNo, int pageSize) {
        List<OrderMaster> orderMasterList = orderMasterDao.findByBuyerId(buyerOpenid);
        if (CollectionUtils.isEmpty(orderMasterList)) {
            throw new SellException(ResultEnum.NONE_OPENID_INFO);
        }
        //todo 一个用户也可能有多个订单 这里按照就有一个进行处理 后续扩展

        /** 将 OrderMaster 的 list 转化为 OrderDTO */
        List<OrderDTO> orderDTOS = orderMasterList.stream().map(e -> {
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(e, orderDTO);
            List<OrderDetail> details = orderDetailDao.findListByOrderId(e.getOrderId());
            if (!CollectionUtils.isEmpty(details)) {
                orderDTO.setOrderDetailList(details);
            }
            return orderDTO;
        }).collect(Collectors.toList());

        return orderDTOS;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            LOGGER.error("【取消订单】订单状态不正确，orderId={}, orderStatus={}",orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        int updateRow = orderMasterDao.changeOrderStatus(orderDTO.getOrderId(), OrderStatusEnum.CANCEL.getCode());
        if (updateRow != 1) {
            LOGGER.error("【取消订单】订单更新失败，oderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        //退还库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            LOGGER.error("【取消订单】订单中无商品详情，orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOS = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOS);

        //如果已支付退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            //todo 6-11
        }

        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            LOGGER.error("【完结订单】订单状态不正确，orderId={}, orderStatus={}",orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        int rows = orderMasterDao.changeOrderStatus(orderDTO.getOrderId(), OrderStatusEnum.FINISHED.getCode());
        if (rows != 1) {
            LOGGER.error("【完结订单】订单更新失败，oderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }

        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            LOGGER.error("【订单支付】订单状态不正确，orderId={}, orderStatus={}",orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            LOGGER.error("【订单支付】支付状态不正常，orderDTO={}",orderDTO);
        }
        //修改支付状态
        int row = orderMasterDao.changePayStatus(orderDTO.getOrderId(), PayStatusEnum.SUCCESS.getCode());

        if (1 != row) {
            LOGGER.error("【订单支付】支付状态更新失败，oderDTO={}",orderDTO);
            throw new SellException(ResultEnum.UPDATE_PAY_STATUS_ERROR);
        }

        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        return orderDTO;
    }

    @Override
    public PageInfo<OrderDTO> findList(int pageNo, int pageSize) {
        return null;
    }
}
