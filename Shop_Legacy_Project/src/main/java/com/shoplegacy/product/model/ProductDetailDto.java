package com.shoplegacy.product.model;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductDetailDto {
    private Long prodNo;
    private String prodName;
    private Integer price;
    private Integer stockQty;
    private LocalDateTime crtDt;
    private LocalDateTime updDt;
    private String delYn;
    private LocalDateTime delDt;

    private String detailDesc;
    private String notice;
    private String shippingInfo;
    private String additionalInfo;

    private List<ProductImageDto> imageList;

}
