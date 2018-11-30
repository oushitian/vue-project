package com.netty.rpc.server;

import com.netty.rpc.server.handler.MyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author xyl
 * @Create 2018-11-28 16:11
 * @Desc Rpc的服务端，每个服务端定义一个线程
 **/
@Slf4j
public class RpcServer{

    private static RpcServer server = new RpcServer();
    //防止外部创建
    private RpcServer() {
    }

    private static boolean isStarted = false;

    private static int PORT = 8007;

    public static void start0(){
        if (isStarted) {
            log.info("server has started...");
        }
        server.startInit();
    }

    public static void main(String[] args) {
        RpcServer rpcServer = new RpcServer();
        rpcServer.startInit();
    }

    private void startInit() {
        //启动netty的NIO创建服务器端
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap strap = new ServerBootstrap();
            strap.group(bossGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new LengthFieldBasedFrameDecoder(65535, 0, 4, 0, 4))  //防止粘包问题
                                    .addLast(new ObjectDecoder(ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())))
                                    .addLast(new LengthFieldPrepender(4))
                                    .addLast(new ObjectEncoder())
                                    .addLast(new MyServerHandler());
                        }
                    });
            ChannelFuture future = strap.bind(PORT).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void shutDown() {
        isStarted = false;
    }
}
