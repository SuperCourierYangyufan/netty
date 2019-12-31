package com.my.netty.BIO;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 杨宇帆
 * @create 2019-12-29
 */
public class BioService {
    public static void main(String[] args) throws Exception{
        ExecutorService threadPool = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("服务器启动了");
        while (true){
            //监听
            Socket socket = serverSocket.accept();
            System.out.println("链接到一个客户端");
            threadPool.execute(()->{
                System.out.println("进入一个线程");
                try {
                    byte[] bytes = new byte[1024];
                    InputStream inputStream = socket.getInputStream();
                    while (true){
                        int read = inputStream.read(bytes);
                        if(read<=0)break;
                        System.out.println(new String(bytes,0,read));
                    }
                }catch (Exception e){
                    System.out.println("发生异常："+e.getMessage());
                }finally {
                    System.out.println("关闭client链接");
                    try {
                        socket.close();
                    }catch (Exception e){
                        System.out.println(e.getMessage());
                    }
                }
            });
        }
    }

}
