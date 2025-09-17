package com.shoplegacy.cart.model;


import lombok.Data;

import java.time.LocalDate;

@Data
public class CartDto {
    private Long cartNo;
    private String userId;
    private Long prodNo;
    private String prodName;
    private String ordNo;
    private Integer qty;
    private LocalDate crtDt;
    private LocalDate updDt;
    private LocalDate delDt;
    private String delYn;

}
