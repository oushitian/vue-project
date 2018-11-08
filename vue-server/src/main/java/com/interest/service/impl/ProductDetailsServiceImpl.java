package com.interest.service.impl;

import com.interest.dao.sf.SfProductDetailsMapper;
import com.interest.model.sf.SfProductDetails;
import com.interest.service.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author xyl
 * @Create 2018-11-08 11:25
 * @Desc 写点注释吧
 **/
@Service
public class ProductDetailsServiceImpl implements ProductDetailsService {

    @Autowired
    private SfProductDetailsMapper sfProductDetailsMapper;

    @Override
    public List<SfProductDetails> getProductDetailsByProductId(Integer productId) {
        return sfProductDetailsMapper.getProductDetailsByProductId(productId);
    }
}
