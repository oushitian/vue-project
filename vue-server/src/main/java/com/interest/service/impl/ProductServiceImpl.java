package com.interest.service.impl;

import com.interest.dao.sf.SfProductMapper;
import com.interest.model.sf.SfProduct;
import com.interest.page.PageQuery;
import com.interest.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author xyl
 * @Create 2018-11-07 17:19
 * @Desc 写点注释吧
 **/
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private SfProductMapper sfProductMapper;

    @PageQuery
    @Override
    public List<SfProduct> getProductAll(String number) {
        return sfProductMapper.findAll(number);
    }
}
