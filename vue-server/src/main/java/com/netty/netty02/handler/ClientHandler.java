package com.netty.netty02.handler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Author xyl
 * @Create 2018-11-12 10:48
 * @Desc 写点注释吧
 **/
//这里有个注解， 该注解 Sharable 主要是为了了多个handler可以被多个channel安全地共享，也就是保
//证线程安全
@ChannelHandler.Sharable
public class ClientHandler extends SimpleChannelInboundHandler<String> {

    //业务处理，服务端返回的数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
    }

    //异常数据捕获
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
