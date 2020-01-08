package com.my.netty.SimpleNetty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author 杨宇帆
 * @create 2020-01-08
 */
public class NettyClient {
    public static void main(String[] args) {
        //客户端一个事件循环组
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        //创建一个客户端启动对象
        Bootstrap bootstrap = new Bootstrap();
        //设置
        try {
            ChannelFuture channelFuture = bootstrap.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    }).connect("localhost", 6668)
                    .sync();
            System.out.println("client read is ok");

            //关闭监听
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){

        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
