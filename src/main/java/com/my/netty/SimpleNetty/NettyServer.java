package com.my.netty.SimpleNetty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author 杨宇帆
 * @create 2020-01-08
 */
public class NettyServer {
    public static void main(String[] args) {
        //创建两个线程组，boos and work
        //boss处理连接 work进行IO处理
        //两个都是无线循环
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        try {
        //服务器启动对象，配置参数
        ServerBootstrap bootstrap = new ServerBootstrap();
        ChannelFuture channelFuture = bootstrap.group(bossGroup, workGroup) //设置两个线程组
                .channel(NioServerSocketChannel.class)//设置通道类型
                .option(ChannelOption.SO_BACKLOG, 128)//设置线程个数等待连接个数
                .childOption(ChannelOption.SO_KEEPALIVE, true)//保持活动连接状态
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    //创建一个通道测试对象
                     @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        //给pipeline设置处理器
                        ch.pipeline().addLast(new NettyServerHandler());
                    }
                })//给workGroup的eventLoop对应的管道的处理器
                .bind(6668) //绑定端口
                .sync();//同步处理
        System.out.println("server is ready");

        //对回调监听
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.isSuccess()){
                    System.out.println("监听端口ok");
                }else{
                    System.out.println("监听端口error");
                }
            }
        });

        //对关闭通道监听
        channelFuture.channel().closeFuture().sync();
        }catch (Exception e){

        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
