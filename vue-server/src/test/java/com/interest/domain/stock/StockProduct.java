package com.interest.domain.stock;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author xyl
 * @Create 2018-11-06 8:54
 * @Desc 写点注释吧
 **/
@Setter
@Getter
public class StockProduct {

    private Integer productId;
    private String productSn;
    private Integer productNum;
    private Integer regionId;
    private String sfairline;
    private Integer sfshipping;
    private String bomSn;

}
