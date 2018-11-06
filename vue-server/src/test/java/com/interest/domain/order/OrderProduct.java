package com.interest.domain.order;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author xyl
 * @Create 2018-11-06 13:56
 * @Desc 写点注释吧
 **/
@Getter
@Setter
public class OrderProduct {

    private String productSn;
    private Integer productNum;
    private Integer productType = 0;
    private Integer sellPrice;

}
