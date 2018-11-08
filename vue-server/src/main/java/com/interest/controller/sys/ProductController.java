package com.interest.controller.sys;

import com.interest.model.sf.SfProduct;
import com.interest.model.sf.SfProductDetails;
import com.interest.page.PageResult;
import com.interest.service.ProductDetailsService;
import com.interest.service.ProductService;
import com.interest.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author xyl
 * @Create 2018-11-07 17:17
 * @Desc 写点注释吧
 **/
@RestController
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
}
