package com.interest.service;

import com.interest.model.sf.SfProduct;
import com.interest.model.view.ProductModel;

import java.util.List;

/**
 * @Author xyl
 * @Create 2018-11-07 17:19
 * @Desc 写点注释吧
 **/
public interface ProductService {
    List<SfProduct> getProductAll(String number);

    void editProduct(ProductModel productModel);
}
