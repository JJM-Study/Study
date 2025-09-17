package com.shoplegacy.order.mapper;

import com.shoplegacy.order.model.OrderDetailDto;
import com.shoplegacy.order.model.OrderDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    void insertOrderMaster(OrderDto order);
    void insertOrderDetail(OrderDetailDto orderDetail);
}
