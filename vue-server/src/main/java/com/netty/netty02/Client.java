package com.netty.netty02;

import com.netty.netty02.handler.ClientHandler;
import com.netty.netty02.handler.ServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author xyl
 * @Create 2018-11-12 10:41
 * @Desc netty客户端实现代码
 **/
public final class Client {

    public static void main(String[] args) throws InterruptedException, IOException {
        //客户端创建workerGroup
        NioEventLoopGroup workerGroup = new NioEventLoopGroup(1);
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    //设置客户端的option参数
                    .option(ChannelOption.TCP_NODELAY,Boolean.TRUE)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline()
                                    //添加帧限定符来防⽌止粘包现象
                                    .addLast(new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()))
                                    .addLast(new StringEncoder())
                                    .addLast(new StringDecoder())
                                    .addLast(new ClientHandler());
                        }
                    });

            //连接服务端
            Channel channel = bootstrap.connect("127.0.0.1",8888).sync().channel();
            ChannelFuture lastWriteFuture = null;
            //通过控制台输入器发送信息
            BufferedReader in = new BufferedReader(new
                    InputStreamReader(System.in));
            for (;;) {
                String line = in.readLine();
                if (line == null) {
                    break;
                }
                // Sends the received line to the server.
                lastWriteFuture = channel.writeAndFlush(line+"\r\n");
                // If user typed the 'bye' command, wait until the server closes
                // the connection.
                if ("bye".equals(line.toLowerCase())) {
                    channel.closeFuture().sync();
                    break;
                }
            }
            //不让进程停止直到lastWriteFuture==null
            if (lastWriteFuture != null) {
                lastWriteFuture.sync();
            }
        }finally {
            workerGroup.shutdownGracefully();
        }
    }

}
