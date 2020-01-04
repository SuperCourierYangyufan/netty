package com.my.netty.Selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author 杨宇帆
 * @create 2020-01-04
 */
public class NioClient {
    public static void main(String[] args) throws Exception{
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        if(!socketChannel.connect(inetSocketAddress)){
            while (!socketChannel.finishConnect()){
                System.out.println("因为链接需要时间，客户端不会阻塞，可以做其他工作");
            }
        }
        String hello = "hello,新年";
        ByteBuffer buffer = ByteBuffer.wrap(hello.getBytes());
        //发送
        socketChannel.write(buffer);
        System.in.read();
    }

}
