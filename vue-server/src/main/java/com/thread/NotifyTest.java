package com.thread;

/**
 * @Author xyl
 * @Create 2018-11-14 10:26
 * @Desc 测试Object的notify()和wait()
 **/
public class NotifyTest {

    public volatile int i;

    public void needWait() throws InterruptedException {
        synchronized (this){    //wait和notify必须要在同步块中，因为要有多线程的环境才有等待唤醒的应用场景
            i++;
            System.out.println(Thread.currentThread().getId()+"  当前的累加数是:"+i);
            wait();
        }
    }

    public void notifyTest(){
        synchronized (this) {
            i++;
            System.out.println(Thread.currentThread().getId()+"  当前的累加数是:"+i);
            notifyAll();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        NotifyTest notifyTest = new NotifyTest();
        new Thread(() -> {
            try {
                notifyTest.needWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                notifyTest.needWait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        Thread.sleep(100);
        new Thread(() -> notifyTest.notifyTest()).start();
    }
}
