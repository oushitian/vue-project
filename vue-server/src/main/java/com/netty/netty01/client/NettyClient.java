package com.netty.netty01.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

/**
 * @Author xyl
 * @Create 2018-11-09 11:25
 * @Desc 每个client就是一个线程,然后EventLoopGroup又是一个线程组
 **/
public class NettyClient implements Runnable{

    @Override
    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,Boolean.TRUE)
                .handler(new ChannelInitializer<SocketChannel>(){
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                         ch.pipeline()
                         // 添加帧限定符来防⽌止粘包现象
                         .addLast(new DelimiterBasedFrameDecoder(8192,Delimiters.lineDelimiter()))
                         .addLast("decoder", new StringDecoder(CharsetUtil.UTF_8))
                         .addLast("encoder", new StringEncoder(CharsetUtil.UTF_8))
                         .addLast("handler", new TcpClientHandler());
                    }
                });
            for (int i=0;i<10;i++){
                ChannelFuture f = bootstrap.connect(new InetSocketAddress(10000)).sync();
                f.channel().writeAndFlush("hello service !" + Thread.currentThread().getName()+ ":---->"+i+"\r\n");
                f.channel().closeFuture().sync();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        for (int i = 0;i < 3 ;i++ ){
            new Thread(new NettyClient(),">>> this thread "+i).start();
        }
    }

}
