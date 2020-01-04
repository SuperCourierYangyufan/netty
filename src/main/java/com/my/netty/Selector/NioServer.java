package com.my.netty.Selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author 杨宇帆
 * @create 2020-01-04
 */
public class NioServer {
    public static void main(String[] args) throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        Selector selector = Selector.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置非阻塞
        serverSocketChannel.configureBlocking(false);
        //server注册到选择去，关心为OP_ACCEPT 链接事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //循环等待客户端链接
        while(true){
            if(selector.select(1000) == 0){
                //没有事件发生
                System.out.println("服务器1S内无连接");
                continue;
            }
            //获取相关的SelectKey集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                //判断该通道发生的事件，做相应的处理
                if(selectionKey.isAcceptable()){
                    //连接事件,给该客户端生成一个SocketChannel
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //设置为非阻塞
                    socketChannel.configureBlocking(false);
                    //将socket注册到选择器,可以绑定一个buff
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }

                if(selectionKey.isReadable()){
                    //socket进行读取事件
                    //通过key反向获取到channel
                    SocketChannel channel = (SocketChannel)selectionKey.channel();
                    //获取到该channel关联的buff
                    ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                    channel.read(buffer);
                    System.out.println("客户端发送数据为"+new String(buffer.array()));
                }

                //从集合移除selectionKey,防止重复
                iterator.remove();
            }
        }
    }
}
