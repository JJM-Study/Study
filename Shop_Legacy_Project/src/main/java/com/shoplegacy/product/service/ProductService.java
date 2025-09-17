package com.shoplegacy.product.service;

import com.shoplegacy.product.mapper.ProductMapper;
import com.shoplegacy.product.model.ProductDetailDto;
import com.shoplegacy.product.model.ProductDto;
import com.shoplegacy.product.model.ProductPriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    @Autowired
    ProductMapper productMapper;

    public List<ProductDto> selectProductList() {

        return productMapper.selectProductList();
    }

    public ProductDetailDto selectProductDetail(String prodNo) {

        return productMapper.selectProductDetail(prodNo);
    }

    public List<ProductPriceDto> selectProductPrice(List<Long> prodNo) {

        return productMapper.selectProductPrice(prodNo);
    }
}
