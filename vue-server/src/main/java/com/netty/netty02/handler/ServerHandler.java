package com.netty.netty02.handler;

import io.netty.channel.*;

import java.net.InetAddress;
import java.util.Date;

/**
 * @Author xyl
 * @Create 2018-11-12 10:26
 * @Desc 写点注释吧
 **/
//这里有个注解， 该注解 Sharable 主要是为了了多个handler可以被多个channel安全地共享，也就是保
//证线程安全
@ChannelHandler.Sharable
public class ServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 新连接接入时会触发
        ctx.write("Welcome to " +
                InetAddress.getLocalHost().getHostName() + "!\r\n");
        ctx.write("It is " + new Date() + " now.\r\n");
        ctx.flush();
    }

    //异常处理理
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable
            cause) {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        String returnMsg;
        boolean close = false;
        if (msg.isEmpty()) {
            returnMsg = "what are you 弄啥嘞？\r\n";
        } else if ("bye".equals(msg.toLowerCase())) {
            returnMsg = "good bye!\r\n";
            close = true;
        } else {
            returnMsg = "Did you say " + msg + "？\r\n";
        }
        //写回数据
        ChannelFuture future = ctx.writeAndFlush(returnMsg);
        if (close) {
            //增加客户端关闭连接的监听器
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }
}
