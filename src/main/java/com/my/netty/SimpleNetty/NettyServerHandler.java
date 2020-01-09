package com.my.netty.SimpleNetty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;


/**
 * @author 杨宇帆
 * @create 2020-01-08
 * 自定义Handle 需要继承netty规定好的某个HandlerAdapter
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    //读取数据事件，读取客户端的消息
    //ChannelHandlerContext 上下文对象 包含 管道，通道，地址。。
    //msg 客户端发送的数据
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx="+ctx);
        //ByteBuf是netty提供的buff
        ByteBuf buf = (ByteBuf)msg;
        System.out.println("client send msg is "+buf.toString(CharsetUtil.UTF_8));
        System.out.println("client address is "+ctx.channel().remoteAddress());

        //用户自定义普通任务,该任务会加入TaskQueue
        ctx.channel().eventLoop().execute(()->{
            try {
                Thread.sleep(10 * 1000);
                System.out.println("this is sleep 10s msg");
            }catch (Exception e){

            }
        });

        ctx.channel().eventLoop().schedule(()->{
            System.out.println("this is sleep 5 msg");
        }, 5, TimeUnit.SECONDS);
    }

    //数据读取完毕回送
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //数据写入缓冲并刷新
        //一般 对发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello client",CharsetUtil.UTF_8));
    }

    //处理异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //关闭通道
        ctx.channel().close();
    }
}
