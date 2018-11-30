package com.interest;

import com.netty.rpc.api.ISay;
import com.netty.rpc.api.pojo.Person;
import com.netty.rpc.client.proxy.RpcProxy;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;

/**
 * @Author xyl
 * @Create 2018-11-29 15:57
 * @Desc 写点注释吧
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RpcTest {

    @Test
    public void test(){
        ISay say = RpcProxy.getProxy(ISay.class);

        for (int i = 0; i < 2; i++) {

            String result = say.hello("luangeng" + i);
            log.info("result: " + result);

            result = say.hello("你好，", "luangeng" + i);
            log.info("result: " + result);

            Person p = new Person();
            p.setId(i);
            p.setName("fd" + i);
            p.setMan(true);
            p.setBirth(new Date());
            p.setList(new ArrayList<>());
            p.getList().add("pojo" + i);
            Person p2 = say.test(p);
            log.info(p2.toString());
            log.info(" ");
        }
    }

}
