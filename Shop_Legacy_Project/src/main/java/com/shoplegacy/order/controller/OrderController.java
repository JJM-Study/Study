package com.shoplegacy.order.controller;

import com.shoplegacy.cart.service.CartService;
import com.shoplegacy.order.model.OrderDetailDto;
import com.shoplegacy.order.model.OrderDto;
import com.shoplegacy.order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/order")
public class OrderController {
// 전체 선택 및 주문, 주문 실패 등 구현

    @Autowired
    OrderService orderService;

    @Autowired
    CartService cartService;

    @PostMapping("/from-cart")
    public ResponseEntity<Map<String, Object>> orderFromCart(@RequestBody List<Long> cartNos) {
        Map<String, Object> response = new HashMap<>();
        try {


            // 설계 원칙에 따라서 service에서 CartToOrder 구현
            Map<String, Object> result = cartService.convertCartNosToOrderItems(cartNos);
            OrderDto order = (OrderDto) result.get("orderMaster");
            List<OrderDetailDto> orderDetails = (List<OrderDetailDto>) result.get("orderDetails");

            // service 추가
            orderService.createOrder(order, orderDetails);

            response.put("success", true);
            response.put("message", "주문 성공");

            return ResponseEntity.ok(response);
        } catch(Exception e) {
            response.put("success", false);
            response.put("message", "주문 실패" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }


    }
}
