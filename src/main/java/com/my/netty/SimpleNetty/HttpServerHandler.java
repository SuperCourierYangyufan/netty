package com.my.netty.SimpleNetty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @author 杨宇帆
 * @create 2020-01-09
 * SimpleChannelInboundHandler 是ChannelInboundHandlerAdapter的子类
 * HttpObject 是客户端和服务端通讯的数据封装而成
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    //读取客户端数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        //判断msg是不是http请求
        if(msg instanceof HttpRequest){
            System.out.println(ctx.channel().remoteAddress());
            //回复信息
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello http", CharsetUtil.UTF_8);
            //Http相应
            DefaultFullHttpResponse httpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            httpResponse.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            httpResponse.headers().set(HttpHeaderNames.CONTENT_LENGTH,byteBuf.readableBytes());
            //将构建好的response返回
            ctx.writeAndFlush(httpResponse);
        }
    }
}
