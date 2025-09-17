package com.shoplegacy.product.model;

import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ProductDto {
        private Long prodNo;
        private String prodName;
        private Integer price;
        private Integer stockQty;
        private LocalDate crtDt;
        private LocalDate updDt;
        private String delYn;
        private LocalDate delDt;

        private String imageUrl;

}
