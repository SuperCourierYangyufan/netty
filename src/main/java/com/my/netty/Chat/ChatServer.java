package com.my.netty.Chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author 杨宇帆
 * @create 2020-01-04
 */
public class ChatServer {
    private Selector selector;
    private ServerSocketChannel listenerChannel;
    private static final int port = 6667;
    private static final long listenerTime = 2000;
    private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);

    public ChatServer(){
        //初始化
        try {
            selector  = Selector.open();
            listenerChannel = ServerSocketChannel.open();
            listenerChannel.socket().bind(new InetSocketAddress(port));
            listenerChannel.configureBlocking(false);
            listenerChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (IOException e){
            logger.error("ChatServer init error",e);
        }
    }

    //监听
    private void listener(){
        try {
            while (true){
                int actionCount = selector.select(listenerTime);
                if(actionCount > 0){
                    //有相应事件处理
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        SelectionKey selectionKey = iterator.next();
                        if(selectionKey.isAcceptable()){
                            //连接事件
                            SocketChannel socketChannel = listenerChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector, SelectionKey.OP_READ);
                            logger.info(socketChannel.getRemoteAddress()+"上线了");
                        }else if(selectionKey.isReadable()){
                            //读取事件
                            readData(selectionKey);
                        }
                        //删除key,防止重复
                        iterator.remove();
                    }
                }else{
//                    logger.info("等待中。。。。");
                }
            }
        }catch (Exception e){
            logger.error("ChatServer listener error",e);
        }finally {

        }
    }

    //读取客户端消息
    private void readData(SelectionKey selectionKey) {
        SocketChannel channel = null;
        try {
            channel = (SocketChannel)selectionKey.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            //根据count数值做处理
            if(count > 0){
                String message = new String(buffer.array());
                logger.info(channel.getRemoteAddress()+"说:"+message);
                //向其他客户端转发消息
                sendInfoToOtherClients(message,channel);
            }
        }catch (Exception e){
            try {
                logger.info(channel.getRemoteAddress()+"离线了");
                //离线处理
                selectionKey.cancel();
                channel.close();
            } catch (IOException e1) {
                logger.error("ChatServer readData error",e);
            }
        }
    }

    private void sendInfoToOtherClients(String message,SocketChannel self) {
        try {
            logger.info("server 转发消息");
            //获取所有通道
            for(SelectionKey selectionKey:selector.keys()){
                SelectableChannel channel = selectionKey.channel();
                //排除自己
                //channel instanceof SocketChannel 是因为服务器自己也注册到选择器
                //只有SockChannel才去发消息
                if(!(channel instanceof SocketChannel) ||  channel == self)continue;
                SocketChannel socketChannel = (SocketChannel)channel;
                //写入通道
                socketChannel.write(ByteBuffer.wrap(message.getBytes()));
            }
        }catch (Exception e){
            logger.error("sendInfoToOtherClients error",e);
        }
    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.listener();
    }
}
