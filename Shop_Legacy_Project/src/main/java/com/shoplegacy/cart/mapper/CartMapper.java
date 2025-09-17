package com.shoplegacy.cart.mapper;

import com.shoplegacy.cart.model.CartDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CartMapper {
    List<CartDto> selectOrderCartList(@Param("orderColumn") String orderColumn, @Param("orderType") String orderType);

    int addToOrderCart(CartDto cartDto);

    List<CartDto> selectOrderCartItemsById(List<Long> cartDtos);
}
