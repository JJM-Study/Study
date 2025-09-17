package com.shoplegacy.product.controller;

import com.shoplegacy.product.model.ProductDetailDto;
import com.shoplegacy.product.model.ProductDto;
import com.shoplegacy.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/product")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/productList")
    public String selectProductList(Model model) {
        List<ProductDto> productList = productService.selectProductList();
        model.addAttribute("pageTitle", "상품");
        model.addAttribute("itemList", productList);

        return "/product/productList";
    }

    @GetMapping("/productDetail")
    public String selectProductDetail(@RequestParam("prodNo") String prodNo, Model model) {
        ProductDetailDto imageList = productService.selectProductDetail(prodNo);
        model.addAttribute("pageTitle", imageList.getProdName());
        model.addAttribute("itemList", imageList);

        return "/product/productDetail";
    }
}
