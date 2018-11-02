package com.interest.domain.mall;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author xyl
 * @Create 2018-11-02 15:21
 * @Desc 写点注释吧
 **/
@Getter
@Setter
public class MallDO {

    private String userrank;
    private List<Product> productlist;

}
