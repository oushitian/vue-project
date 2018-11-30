package com.thread.exeutors;

import java.util.concurrent.*;

/**
 * @Author xyl
 * @Create 2018-11-27 13:47
 * @Desc 写点注释吧
 **/
public class ExeutorsServiceDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Future future = executorService.submit(new MyTask());
        System.out.println(future.isDone());    //isDone判断任务是否完成，不会阻塞
        //所以在get()方法执行之前可以做一些操作
        System.out.println(future.get());   //get()方法会阻塞当前线程
    }

    static class MyTask implements Callable{
        @Override
        public Object call() throws Exception {
            System.out.println("执行任务中..");
            TimeUnit.SECONDS.sleep(5);
            return "success";
        }
    }

}
