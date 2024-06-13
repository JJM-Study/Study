package kr.co.hanbit.product.management.presentation;

import jakarta.validation.constraints.NotNull;
import kr.co.hanbit.product.management.domain.Product;

public class ProductDto {
    public Long id;

    @NotNull
    public String name;
    @NotNull
    public Integer price;
    @NotNull
    public Integer amount;


    public ProductDto() {

    }

    public ProductDto(String name, Integer price, Integer amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public ProductDto(Long id, String name, Integer price, Integer amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getAmount() {
        return amount;
    }

    public static Product toEntity(ProductDto productDto) {
        Product product = new Product(productDto.getId(), productDto.getName(), productDto.getPrice(), productDto.getAmount());

        return product;
    }

    public static ProductDto toDto(Product product) {
        //ProductDto productDto = new ProductDto(product.getName(), product.getPrice(), product.getAmount());
        ProductDto productDto =  new ProductDto(product.getId(), product.getName(), product.getPrice(), product.getAmount());

        //productDto.setId(product.getId());


        return productDto;

    }

}
