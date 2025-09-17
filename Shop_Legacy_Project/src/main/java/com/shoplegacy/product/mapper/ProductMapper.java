package com.shoplegacy.product.mapper;

import com.shoplegacy.product.model.ProductDetailDto;
import com.shoplegacy.product.model.ProductDto;
import com.shoplegacy.product.model.ProductPriceDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMapper {
    List<ProductDto> selectProductList();

    ProductDetailDto selectProductDetail(String prodNo);

    List<ProductPriceDto> selectProductPrice(List<Long> prodNo);
}
