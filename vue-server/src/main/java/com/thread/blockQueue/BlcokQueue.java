package com.thread.blockQueue;

import java.util.PriorityQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author xyl
 * @Create 2018-11-26 18:22
 * @Desc 使用阻塞队列实现
 **/
public class BlcokQueue {

    private static int queueSize = 10;
    private static LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>(queueSize);

    public static void main(String[] args)  {
        new NotiftQueue.Producer().start();
        new NotiftQueue.Consumer().start();
    }

    static class Consumer extends Thread{

        @Override
        public void run() {
            consume();
        }

        private void consume() {
            while(true){
                try {
                    queue.take();
                    System.out.println("从队列取走一个元素，队列剩余"+queue.size()+"个元素");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Producer extends Thread{

        @Override
        public void run() {
            produce();
        }

        private void produce() {
            while(true){
                try {
                    queue.put(1);
                    System.out.println("向队列取中插入一个元素，队列剩余空间："+(queueSize-queue.size()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
