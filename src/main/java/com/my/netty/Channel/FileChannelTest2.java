package com.my.netty.Channel;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author 杨宇帆
 * @create 2019-12-30
 */
public class FileChannelTest2 {
    public static void main(String[] args) throws Exception{
        File file = new File("C:\\Users\\Yang\\Desktop\\hello.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel channel = fileInputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate((int)file.length());
        channel.read(byteBuffer);
        String hello = new String(byteBuffer.array());
        System.out.println(hello);
        fileInputStream.close();
    }
}
