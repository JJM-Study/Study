package com.shoplegacy.cart.controller;


import com.shoplegacy.cart.model.CartDto;
import com.shoplegacy.cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    private CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cartlist")
    public String selectOrderCartList(@RequestParam(value = "orderColumn", required = false) String orderColumn,
                                      @RequestParam(value = "orderType", required = false) String orderType,
                                      Model model) {

        if (orderColumn == null || orderColumn.trim().isEmpty()) {
            orderColumn = "cart_no";
        }
        if (orderType == null || orderType.trim().isEmpty()) {
            orderType = "asc";
        }

        model.addAttribute("pageTitle", "장바구니");
        model.addAttribute("orderColumn", orderColumn);
        model.addAttribute("orderType", orderType);

        List<CartDto> cartList = cartService.selectOrderCartList(orderColumn, orderType);
        model.addAttribute("cartList", cartList);
        return "cart/cartlist";
    }

    @RequestMapping("/add")
    public ResponseEntity<Map<String, Object>> addToOrderCart(CartDto cartDto) {

//        Integer qty = cartDto.getQty() == null || cartDto.getQty() <= 0 ? 1 : cartDto.getQty();
        if (cartDto.getQty() == null || cartDto.getQty() <= 0) {
            cartDto.setQty(1);
        }

        int result = cartService.addToCart(cartDto);
        Map<String, Object> response = new HashMap<>();

        Boolean isSuccess = result > 0;
        response.put("success", isSuccess);
        response.put("message", isSuccess ? "장바구니에 담았습니다." : "담기 실패했습니다.");

        return ResponseEntity.ok(response);
    }


}
