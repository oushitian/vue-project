package com.interest.service;

import com.interest.model.sf.SfProductDetails;

import java.util.List;

/**
 * @Author xyl
 * @Create 2018-11-08 11:24
 * @Desc 写点注释吧
 **/
public interface ProductDetailsService {
    List<SfProductDetails> getProductDetailsByProductId(Integer productId);
}
