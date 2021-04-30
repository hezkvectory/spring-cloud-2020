package com.hezk.netty.echo.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println(ctx.channel().remoteAddress() + " -> Server :" + msg);

        // 写消息到管道
        ChannelFuture future = ctx.write(msg);// 写消息
        future.addListener(f -> System.out.println("EchoServerHandler.operationComplete"));//flush成功后回调
        ctx.flush(); // 冲刷消息

        // 上面两个方法等同于 ctx.writeAndFlush(msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

//        ctx.alloc().buffer();

        // 当出现异常就关闭连接
        cause.printStackTrace();
        ctx.close();
    }
}
