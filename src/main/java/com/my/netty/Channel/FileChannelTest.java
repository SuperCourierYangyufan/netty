package com.my.netty.Channel;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author 杨宇帆
 * @create 2019-12-30
 */
public class FileChannelTest {
    public static void main(String[] args) throws Exception{
        String hello = "hello,代码";
        //输出流
        FileOutputStream fileOutputStream = new FileOutputStream("C:\\Users\\Yang\\Desktop\\hello.txt");
        //获得流-》真实类型，FileChannelImpl
        FileChannel fileChannel = fileOutputStream.getChannel();
        //创建缓存Buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //写入buffer
        byteBuffer.put(hello.getBytes());
        //反转给channel写
        byteBuffer.flip();
        //写入channel
        fileChannel.write(byteBuffer);
        //关闭流
        fileOutputStream.close();

    }
}
