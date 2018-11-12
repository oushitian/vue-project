package com.netty.netty01.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author xyl
 * @Create 2018-11-09 11:06
 * @Desc 写点注释吧
 **/
public class TcpServerHander extends ChannelInboundHandlerAdapter {

    //通道激活状态会调用该方法
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel激活。。。");
    }

    //通道可写状态会调用该方法
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接受收到的数据
        System.out.println("服务端接收到数据:"+msg);
        //写回数据给客户端
        ctx.writeAndFlush("hello i am server \r\n");
        ctx.close();
    }

    //通道异常状态会调用该方法
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("get server exception :"+cause.getMessage());
    }
}
