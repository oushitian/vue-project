package com.interest.domain.order;

import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author xyl
 * @Create 2018-11-06 13:50
 * @Desc 写点注释吧
 **/
@Setter
@Getter
public class OrderDO {

    private String outerId;
    private Integer payId = 1;
    private Integer device = 28;
    private Integer orderSource = 28;
    private Integer province;
    private Integer city;
    private Integer district;
    private Integer area;
    private String address;
    private String telNumber;
    private String mobile;
    private String consignee;
    private Integer payTime;
    private Integer moneyPaid;
    private Integer addTime;
    private Boolean preSale = false;
    private Integer orderAmount;
    private Integer productAmount;
    private Integer shippingFee;
    private List<OrderProduct> orderProducts;

}
