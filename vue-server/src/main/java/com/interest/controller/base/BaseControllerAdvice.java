package com.interest.controller.base;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author fengdi
 * @Create 2018-09-17 16:58
 * @Desc 全局处理异常类
 **/
@ControllerAdvice//不指定包默认加了@Controller和@RestController都能控制
public class BaseControllerAdvice {

    /**
     * 全局异常处理，反正异常返回统一格式的map
     * @param ex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = Exception.class)  //这里是异常类，也可以改成自定义的异常类
    public Map<String,Object> exceptionHandler(Exception ex){
        Map<String,Object> map  = new HashMap<String,Object>();
        map.put("code",1001);
        map.put("mag",ex.getMessage());
        //发生异常进行日志记录，写入数据库或者其他处理，此处省略,比如可以存入mongodb中
        return map;
    }
}
