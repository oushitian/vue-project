package com.interest.domain.stock;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author xyl
 * @Create 2018-11-06 8:54
 * @Desc 写点注释吧
 **/
@Getter
@Setter
public class StockDO {

    private Integer invokeSource;

    List<StockProduct> stockInfoRegionParamList;
}
