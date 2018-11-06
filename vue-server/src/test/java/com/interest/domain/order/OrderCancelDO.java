package com.interest.domain.order;

import lombok.Getter;
import lombok.Setter;

/**
 * @Author xyl
 * @Create 2018-11-06 16:33
 * @Desc 写点注释吧
 **/
@Getter
@Setter
public class OrderCancelDO {

    private Integer cancelType;
    private Integer frontShow;
    private String notes;
    private String operator;
    private String orderSn;
    private Integer returnDirection;

}
