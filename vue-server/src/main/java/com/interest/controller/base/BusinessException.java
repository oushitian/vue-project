package com.interest.controller.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author fengdi
 * @Create 2018-09-17 17:13
 * @Desc 自定义异常类
 **/
@Data//相当于get set方法
@NoArgsConstructor//无参构造方法
@AllArgsConstructor//所有字段的构造方法
public class BusinessException extends RuntimeException{

    private String code;  //异常状态码

    private String message;  //异常信息

    private String method;   //发生的方法，位置等

    private String descinfo;   //描述
}
