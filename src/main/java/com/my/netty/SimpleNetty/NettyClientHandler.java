package com.my.netty.SimpleNetty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @author 杨宇帆
 * @create 2020-01-08
 */
public class NettyClientHandler extends ChannelInboundHandlerAdapter {
    //通道就绪就会触发
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client ctx"+ctx);
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello server！！", CharsetUtil.UTF_8));
    }

    //当通道有读取事件时触发

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("服务器回送消息:"+byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("address"+ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}