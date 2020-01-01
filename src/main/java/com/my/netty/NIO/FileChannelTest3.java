package com.my.netty.NIO;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author 杨宇帆
 * @create 2020-01-01
 */
public class FileChannelTest3 {
    public static void main(String[] args) throws Exception{
        FileInputStream fileInputStream = new FileInputStream("src/main/hello.txt");
        FileChannel inputChannel = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("src/main/hello1.txt");
        FileChannel outputChannel = fileOutputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1);
        while (true){
            //重置，该buff读取过一次后 limit = position 无法读取 read = 0
            buffer.clear();
            int read = inputChannel.read(buffer);
            //正常读取不到为-1
            if(read <= 0)break;
            buffer.flip();
            outputChannel.write(buffer);
        }
        fileOutputStream.close();
        fileInputStream.close();
    }
}
