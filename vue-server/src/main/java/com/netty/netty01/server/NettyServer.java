package com.netty.netty01.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * @Author xyl
 * @Create 2018-11-09 10:10
 * @Desc socket相当于tcp缓冲层，channel链接的是socket
 **/
public class NettyServer {

    public static final EventLoopGroup bossGroup = new NioEventLoopGroup();
    public static final EventLoopGroup workGroup = new NioEventLoopGroup(); //根据实际场景设置合适的线程数

    public static void start() throws InterruptedException {
        //创建启动netty服务端的帮助类
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        //传入boss线程组合work线程组,可以使用链式编程
        serverBootstrap.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel channel) throws Exception {
                        //首先拿到channel中的pipeline(pipeline可以理解成管理handler集合的东西)
                        channel.pipeline()
                                // 添加帧限定符来防⽌止粘包现象  结束要加\r\n
                                .addLast(new DelimiterBasedFrameDecoder(8192,Delimiters.lineDelimiter()))
                                .addLast(new StringDecoder(CharsetUtil.UTF_8))
                                .addLast(new StringEncoder(CharsetUtil.UTF_8))
                                .addLast(new TcpServerHander());
                    }
                });//绑定处理的handler类
        //绑定IP，端口(因为这是个耗时操作，必须同步等待，不然进程会结束,其实只需要一个sync()方法即可，只要保证服务端的进程不要挂掉就好)
        ChannelFuture channelFuture = serverBootstrap.bind(new InetSocketAddress(10000)).sync();
//        channelFuture.channel().closeFuture().sync();// 监听服务端关闭，并阻塞等待
//        System.out.println("tcp服务启动成功。。。");
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("tcp服务开始启动。。。");
        NettyServer.start();
    }

}
