package com.netty.netty01.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Author xyl
 * @Create 2018-11-09 11:40
 * @Desc 写点注释吧
 **/
public class TcpClientHandler extends ChannelInboundHandlerAdapter {

    //通道可写状态会调用该方法
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接受收到的数据
        System.out.println("客户端接收到数据:"+msg);
    }

    //通道异常状态会调用该方法
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("get server exception :"+cause.getMessage());
    }

}

