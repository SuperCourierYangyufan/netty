package com.my.netty.Chat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author 杨宇帆
 * @create 2020-01-04
 */
public class ChatClient {
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 6667;
    private Selector selector;
    private SocketChannel socketChannel;
    private static String username;
    private static final Logger logger = LoggerFactory.getLogger(ChatClient.class);

    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient();
        new Thread(()->{
            while (true){
                chatClient.readMessage();
                try {
                    Thread.sleep(1000);
                }catch (Exception e){
                    logger.error("ChatClient sleep error",e);
                }
            }
        }).start();

        //发送数据
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()){
                String message = scanner.nextLine();
                chatClient.sendMessage(message);
            }
    }

    public ChatClient() {
        try {
            selector = Selector.open();
            socketChannel = SocketChannel.open(new InetSocketAddress(HOST, PORT));
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            username = socketChannel.getLocalAddress().toString().substring(1);
            logger.info(username + "init ok");
        }catch (Exception e){
            logger.error("ChatClient init error",e);
        }

    }

    public void sendMessage(String message){
        message = username+"说:"+message;
        try {
            socketChannel.write(ByteBuffer.wrap(message.getBytes()));
        }catch (Exception e){
            logger.error("ChatClient sendMessage error",e);
        }
    }

    public void readMessage(){
        try {
            int count = selector.select();
            if(count > 0){
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey selectionKey = iterator.next();
                   if(selectionKey.isReadable()){
                       SocketChannel channel = (SocketChannel) selectionKey.channel();
                       ByteBuffer buffer = ByteBuffer.allocate(1024);
                       int read = channel.read(buffer);
                       String message = new String(buffer.array());
                       logger.info(message.trim());
                   }
                   iterator.remove();
                }
            }else{
//                logger.info("ChatClient readMessage not channel");
            }
        }catch (Exception e){
            logger.error("ChatClient readMessage error",e);
        }
    }
}
