package com.my.netty.NIO;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author 杨宇帆
 * @create 2020-01-01
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws Exception{
        //读写模式
        RandomAccessFile randomAccessFile = new RandomAccessFile("src/main/hello.txt", "rw");
        //获取对应文件通道
        FileChannel channel = randomAccessFile.getChannel();
        //参数1 使用读写模式，参数2 起始位置 参数3 映射到内存的大小，可以直接修改的范围就是0-5
        //5 代表最多修改到4 
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 5);

        mappedByteBuffer.put(0, (byte)'H');
        mappedByteBuffer.put(3, (byte)'9');

        randomAccessFile.close();
    }
}
