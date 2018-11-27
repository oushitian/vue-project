package com.thread.semaphore;

import com.sun.java.swing.plaf.windows.resources.windows;

import java.util.concurrent.Semaphore;

import static java.lang.Thread.sleep;

/**
 * @Author xyl
 * @Create 2018-11-26 14:29
 * @Desc 信号量，有些业务比如说要同时指出5和线程买票，就不能用snyc关键字，这里可以产用Semaphore类
 **/
public class TicketWindow {

    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(5); //声明最多5个线程可以运行

        for (int i = 0; i<6; i++){
            new Thread(() -> {
                try {
                    semaphore.acquire();  // 占用窗口
                    System.out.println(Thread.currentThread().getName() + ": 开始买票");
                    sleep(2000);  // 睡2秒，模拟买票流程
                    System.out.println(Thread.currentThread().getName() + ": 购票成功");
                    semaphore.release();  // 释放窗口
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
