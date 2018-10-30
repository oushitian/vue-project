package com.interest.page;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PageQuery {

    String pageNumName() default "page";//页号的参数名
    String pageSizeName() default "pageSize";//每页行数的参数名

}
