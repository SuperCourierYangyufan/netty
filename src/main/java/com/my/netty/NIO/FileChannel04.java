package com.my.netty.NIO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @author 杨宇帆
 * @create 2020-01-01
 */
public class FileChannel04 {
    public static void main(String[] args) throws Exception{
        FileInputStream fileInputStream = new FileInputStream("src/main/test.png");
        FileChannel inputChannel = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("src/main/test2.png");
        FileChannel outputChannel = fileOutputStream.getChannel();

        outputChannel.transferFrom(inputChannel, 0, inputChannel.size());

        fileOutputStream.close();
        fileInputStream.close();
    }
}
