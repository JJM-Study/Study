package com.shoplegacy.order.service;

import com.shoplegacy.order.mapper.OrderMapper;
import com.shoplegacy.order.mapper.OrderSequenceMapper;
import com.shoplegacy.order.model.OrderDetailDto;
import com.shoplegacy.order.model.OrderDto;
import com.shoplegacy.product.model.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import static java.lang.String.format;


@Service
@RequiredArgsConstructor
public class OrderService {

    @Autowired
    OrderSequenceMapper orderSequenceMapper;

    @Autowired
    OrderNumberGeneratorService orderNumberGeneratorService;

    @Autowired
    OrderMapper orderMapper;

    @Transactional
    public String createOrder(OrderDto orderMaster, List<OrderDetailDto> orderDetails) {

        if (orderMaster == null) {
            throw new IllegalArgumentException("주문 상품이 비어 있습니다.");
        }

        String orderNo = orderNumberGeneratorService.generateOrderNumber();

        // 주문 처리 여기에 추가할 것. insertOrderMaster(OrderDto order);
        // 주문 처리 여기에 추가할 것. insertOrderDetail(OrderDto orderDetail);
        orderMaster.setOrderNo(orderNo);
        orderMapper.insertOrderMaster(orderMaster);

        for (OrderDetailDto details : orderDetails) {
            details.setOrderNo(orderNo);
            orderMapper.insertOrderDetail(details);
        }

        return orderNo;
    }



}
