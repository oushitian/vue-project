package com.netty.netty02;

import com.netty.netty02.handler.ServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Author xyl
 * @Create 2018-11-12 9:46
 * @Desc netty的服务端实现代码
 **/
public final class Server {

    public static void main(String[] args) throws InterruptedException {
        //创建两个EventLoopGroup对象
        //创建boss线程组 ⽤于服务端接受客户端的连接(就是处理acceptor线程连接进来的客户端，做一些登录或者权限的验证等，这里不设置线程数默认是cpu*2，设置了就取线程数)
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        //创建work线程组 用于进行处理客户端socketChanel(由selector派发过来的事件通知)的数据读写操作，线程数的设置同理
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            //创建服务端启动帮助类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //设置boss线程组合worker线程组
            serverBootstrap.group(bossGroup,workerGroup)
                    //设置要被实例化的是NioServerSocketChannel类
                    .channel(NioServerSocketChannel.class)
                    //处理NioServerSocketChannel的处理器
                    .handler(new LoggingHandler(LogLevel.INFO))
                    //设置连⼊入服务端的 Client 的 SocketChannel 的处理器
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            //得到pipeline
                            ch.pipeline()
                                    //添加帧限定符来防⽌止粘包现象 \r\n结束
                                    .addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()))
                                    .addLast(new StringDecoder())
                                    .addLast(new StringEncoder())
                                    .addLast(new ServerHandler());
                        }
                    });
            // 绑定端口，并同步等待成功，即启动服务端
            ChannelFuture channelFuture = serverBootstrap.bind(8888).sync();
            // 监听服务端关闭，并阻塞等待
            channelFuture.channel().closeFuture().sync();
        }finally {
            // 优雅关闭两个 EventLoopGroup 对象
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
