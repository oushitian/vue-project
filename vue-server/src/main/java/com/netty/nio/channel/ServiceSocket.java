package com.netty.nio.channel;

import com.netty.nio.buffer.Buffers;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

/**
 * @Author xyl
 * @Create 2018-11-06 16:52
 * @Desc 写点注释吧
 **/
public class ServiceSocket {

    public static class TCPServer implements Runnable{

        private InetSocketAddress localSocketAddress;

        public TCPServer(int port) {
            localSocketAddress = new InetSocketAddress(port);
        }

        @Override
        public void run() {
            Charset utf8 = Charset.forName("UTF-8");
            ServerSocketChannel serverSocketChannel = null;
            Selector selector = null;//选择器

            Random rdn = new Random();

            try {
                //创建选择器
                selector = Selector.open();

                //创建服务器通道
                serverSocketChannel = ServerSocketChannel.open();
                serverSocketChannel.configureBlocking(false);   //设置为非阻塞

                /*设置监听服务器的端口，设置最大连接缓冲数为100*/
                serverSocketChannel.bind(localSocketAddress, 100);

                //向选择器注册感兴趣的事件
                serverSocketChannel.register(selector,SelectionKey.OP_ACCEPT);

            } catch (IOException e) {
                System.out.println("server start failed");
                return;

            }

            System.out.println("服务器启动成功"+localSocketAddress);

            //线程中断就退出
            try {
                while (!Thread.currentThread().isInterrupted()) {

                    int n = selector.select();  //选择器轮询
                    if (n == 0) {
                        continue;
                    }

                    Set<SelectionKey> set = selector.selectedKeys();//获取到已完成注册的key
                    Iterator<SelectionKey> it = set.iterator();
                    SelectionKey key = null;

                    while(it.hasNext()){
                        key = it.next();    //取到key
                        it.remove();        //取到后移除  防止重复处理

                        /*若发现异常，说明客户端连接出现问题,但服务器要保持正常*/
                        try {
                            //检测是否已经可接受
                            if (key.isAcceptable()) {
                            /*accept方法会返回一个普通通道，
                                     每个通道在内核中都对应一个socket缓冲区*/
                                SocketChannel sc = serverSocketChannel.accept();
                                sc.configureBlocking(false);

                                /*向选择器注册这个通道和普通通道感兴趣的事件，同时提供这个新通道相关的缓冲区*/
                                int interestSet = SelectionKey.OP_READ;
                                sc.register(selector, interestSet, new Buffers(256, 256));

                                System.out.println("accept from " + sc.getRemoteAddress());
                            }
                            /*（普通）通道感兴趣读事件且有数据可读*/
                            if(key.isReadable()){
                                /*通过SelectionKey获取通道对应的缓冲区*/
                                Buffers  buffers = (Buffers)key.attachment();
                                ByteBuffer readBuffer = buffers.getReadBuffer();
                                ByteBuffer writeBuffer = buffers.gerWriteBuffer();

                                /*通过SelectionKey获取对应的通道*/
                                SocketChannel sc = (SocketChannel) key.channel();

                                /*从底层socket读缓冲区中读入数据*/
                                sc.read(readBuffer);
                                readBuffer.flip();

                                /*解码显示，客户端发送来的信息*/
                                CharBuffer cb = utf8.decode(readBuffer);
                                System.out.println(cb.array());

                                readBuffer.rewind();


                                /*准备好向客户端发送的信息*/
                                /*先写入"echo:"，再写入收到的信息*/
                                writeBuffer.put("echo from service:".getBytes("UTF-8"));
                                writeBuffer.put(readBuffer);

                                readBuffer.clear();

                                /*设置通道写事件*/
                                key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
                            }

                            /*通道感兴趣写事件且底层缓冲区有空闲*/
                            if(key.isWritable()){

                                Buffers  buffers = (Buffers)key.attachment();

                                ByteBuffer writeBuffer = buffers.gerWriteBuffer();
                                writeBuffer.flip();

                                SocketChannel sc = (SocketChannel) key.channel();

                                int len = 0;
                                while(writeBuffer.hasRemaining()){
                                    len = sc.write(writeBuffer);
                                    /*说明底层的socket写缓冲已满*/
                                    if(len == 0){
                                        break;
                                    }
                                }

                                writeBuffer.compact();

                                /*说明数据全部写入到底层的socket写缓冲区*/
                                if(len != 0){
                                    /*取消通道的写事件*/
                                    key.interestOps(key.interestOps() & (~SelectionKey.OP_WRITE));
                                }

                            }

                        }catch(IOException e){
                            System.out.println("service encounter client error");
                            /*若客户端连接出现异常，从Seletcor中移除这个key*/
                            key.cancel();
                            key.channel().close();
                        }

                    }

                }
            } catch (IOException e1) {
                System.out.println("serverThread selecotr error");
            }finally{
                try{
                    selector.close();
                }catch(IOException e){
                    System.out.println("selector close failed");
                }finally{
                    System.out.println("server close");
                }
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new TCPServer(8888));
        thread.start();
        Thread.sleep(100000);
        /*结束服务器线程*/
        thread.interrupt();

    }
}
