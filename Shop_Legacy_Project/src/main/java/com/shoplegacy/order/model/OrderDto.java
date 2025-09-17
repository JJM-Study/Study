package com.shoplegacy.order.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class OrderDto {
    private String orderNo;
    private String userId;
    private LocalDate orderDate;
    private String orderStatus;
    private Integer totalAmount;
    private String delYn;
    private LocalDate crtDt;
    private LocalDate updDt;
    private LocalDate delDt;

}
