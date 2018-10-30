package com.interest.utils;

import com.github.pagehelper.PageInfo;
import com.interest.page.PageResult;

import java.util.List;

/**
 * @Author xyl
 * @Create 2018-10-30 18:29
 * @Desc 写点注释吧
 **/
public class PageUtil {

    /**
     * 得到分页对象的封装
     * @return
     */
    public static PageResult getPageInfo(List<?> list){
        PageInfo<?> pageInfo = new PageInfo(list);
        PageResult pageResult = new PageResult();
        pageResult.setData(list);
        pageResult.setTotalCount((int)pageInfo.getTotal());
        return  pageResult;
    }

}
