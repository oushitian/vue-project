package com.interest.domain.mall;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author xyl
 * @Create 2018-11-02 15:22
 * @Desc 写点注释吧
 **/
@Getter
@Setter
public class Product {

    private Double sellPrice;
    private String productSn;
    private String productIndex;
    private Double weight;
    private String merchantNumber;
    private String regionId;
    private String cityId;
    private String sfshipping;
    private String productId;

}
