package com.interest.service.impl;

import com.interest.service.TaskService;
import org.springframework.stereotype.Service;

/**
 * @Author xyl
 * @Create 2018-11-08 16:03
 * @Desc 写点注释吧
 **/
@Service
public class TaskServiceImpl implements TaskService {
    @Override
    public void pushOrderInfo() {
        System.out.println("同步订单中");
    }
}
