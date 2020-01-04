package com.my.netty.Buffer;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author 杨宇帆
 * @create 2020-01-01
 */
public class BufferScatteringAndGatherin {
    public static void main(String[] args) throws Exception{
        //创建服务
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //端口监听
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        //绑定端口到socket，并启动
        serverSocketChannel.socket().bind(inetSocketAddress);
        //创建buff数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);
        //等待客户端链接
        SocketChannel socketChannel = serverSocketChannel.accept();
        //循环读取
        //假定最大8个字节
        int messageLength = 8;
        while(true){
            int byteRead = 0;
            while (byteRead < messageLength){
                long read = socketChannel.read(byteBuffers);
                byteRead +=read;
                System.out.println("byteRead = "+byteRead);
                Arrays.asList(byteBuffers)
                        .stream()
                        .map(buff->"position"+buff.position()+";limit"+buff.limit())
                        .forEach(System.out::println);
            }
            //将所有buff反转
            Arrays.asList(byteBuffers).forEach(buff->buff.flip());
            //将数据输出到客户端
            long byteWirte = 0;
            while(byteWirte < messageLength){
                long write = socketChannel.write(byteBuffers);
                byteWirte += write;
                System.out.println("bytewrite = "+byteRead);
            }
            //将所有buff clean
            Arrays.asList(byteBuffers).forEach(buff->buff.clear());
        }
    }
}
