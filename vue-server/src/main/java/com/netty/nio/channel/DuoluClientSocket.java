package com.netty.nio.channel;

import com.netty.constant.HttpConstant;
import com.netty.util.HttpUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author xyl
 * @Create 2018-11-12 17:10
 * @Desc 多路复用NIO客户端模型
 **/
public class DuoluClientSocket {

    //多路复用就是多个channel共用同一个selector，所以这里要设置成静态变量
    private static Selector selector;
    private Charset charset = Charset.forName("utf8");

    static {
        try {
            selector = Selector.open();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {

        DuoluClientSocket client = new DuoluClientSocket();

        for (String host: HttpConstant.HOSTS) {

            client.request(host, HttpConstant.PORT);

        }

        client.select();

    }

    public void request(String host, int port) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.socket().setSoTimeout(5000);
        SocketAddress remote = new InetSocketAddress(host, port);
        socketChannel.configureBlocking(false);
        socketChannel.connect(remote);
        socketChannel.register(selector,
                SelectionKey.OP_CONNECT
                        | SelectionKey.OP_READ
                        | SelectionKey.OP_WRITE);
    }

    public void select() throws IOException {
        while (selector.select() > 0){
            Set keys = selector.selectedKeys();

            Iterator it = keys.iterator();

            while (it.hasNext()){

                SelectionKey key = (SelectionKey)it.next();
                it.remove();

                if (key.isConnectable()){
                    connect(key);
                }
                else if (key.isWritable()){
                    write(key);
                }
                else if (key.isReadable()){
                    receive(key);
                }
            }
        }
    }

    private void connect(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        channel.finishConnect();
        InetSocketAddress remote = (InetSocketAddress) channel.socket().getRemoteSocketAddress();
        String host = remote.getHostName();
        int port = remote.getPort();
        System.out.println(String.format("访问地址: %s:%s 连接成功!", host, port));
    }

    private void write(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        InetSocketAddress remote = (InetSocketAddress) channel.socket().getRemoteSocketAddress();
        String host = remote.getHostName();

        String request = HttpUtil.compositeRequest(host);
        System.out.println(request);

        channel.write(charset.encode(request));
        key.interestOps(SelectionKey.OP_READ);
    }

    private void receive(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        channel.read(buffer);
        buffer.flip();
        String receiveData = charset.decode(buffer).toString();

        if ("".equals(receiveData)) {
            key.cancel();
            channel.close();
            return;
        }

        System.out.println(receiveData);
    }
}
