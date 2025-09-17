package com.shoplegacy.product.model;

import lombok.Data;

@Data
public class ProductImageDto {
    String imageUrl;
    String isMain;  // Y/N
    int sortOrder;
}
