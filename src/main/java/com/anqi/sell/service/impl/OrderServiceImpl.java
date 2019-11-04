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
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.org.apache.bcel.internal.generic.RET;
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

@Service
public class OrderServiceImpl implements OrderService {

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
        String detailId = UUIDUtil.getUUID();

        //1. 查询商品 (数量,价格)
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findById(orderDetail.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //2. 计算总价
            //orderDetail.getProductPrice() * orderDetail.getProductQuantity()
            productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //3. 写入订单详情
            BeanUtils.copyProperties(orderDetail, productInfo);
            //设置 productIcon id name 和 price
            orderDetail.setDetailId(detailId);
            orderDetail.setOrderId(orderId);
            orderDetailDao.insertSelective(orderDetail);

        }

        //3. 写入订单
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterDao.insertSelective(orderMaster);

        //4. 扣减库存
        List<CartDTO> cartDTOList = new ArrayList<>();
        orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
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
    public PageInfo<OrderDTO> findList(String buyerOpenid, int pageNo, int pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<OrderMaster> orderMasterList = orderMasterDao.findByBuyerId(buyerOpenid);
        System.out.println(orderMasterList);

        /** 将 OrderMaster 的 list 转化为 OrderDTO */
        List<OrderDTO> orderDTOS = orderMasterList.stream().map(e -> {
            OrderDTO orderDTO = new OrderDTO();
            BeanUtils.copyProperties(e, orderDTO);
            return orderDTO;
        }).collect(Collectors.toList());

        return new PageInfo<OrderDTO>(orderDTOS);
    }

    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        return null;
    }

    @Override
    public PageInfo<OrderDTO> findList(int pageNo, int pageSize) {
        return null;
    }
}
