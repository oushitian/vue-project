package com.interest.controller.sys;

import com.interest.model.sf.SfProduct;
import com.interest.model.sf.SfProductDetails;
import com.interest.model.view.ProductModel;
import com.interest.page.PageResult;
import com.interest.service.ProductDetailsService;
import com.interest.service.ProductService;
import com.interest.utils.PageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author xyl
 * @Create 2018-11-07 17:17
 * @Desc 写点注释吧
 **/
@RestController
@Slf4j
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDetailsService productDetailsService;

    @GetMapping("/product/all")
    public PageResult getProduct(String number){
        return PageUtil.getPageInfo(productService.getProductAll(number));
    }

    @GetMapping("/product/getProductDetails/{productId}")
    public List<SfProductDetails> getProductDetails(@PathVariable("productId") Integer  productId){
        return productDetailsService.getProductDetailsByProductId(productId);
    }

    @PutMapping("/product/edit")
    public ProductModel editProduct(@RequestBody ProductModel productModel){
        productService.editProduct(productModel);
        log.info("edit finish..");
        return productModel;
    }
}
