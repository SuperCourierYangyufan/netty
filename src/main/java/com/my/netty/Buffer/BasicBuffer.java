package com.my.netty.Buffer;

import java.nio.IntBuffer;

/**
 * @author 杨宇帆
 * @create 2019-12-30
 */
public class BasicBuffer {
    //buff使用入门
    public static void main(String[] args) {
        //创建一个buff,大小为5，可以存放5个int
        IntBuffer intBuffer = IntBuffer.allocate(5);
        //存放数据
        for(int i=0;i<intBuffer.capacity();i++)intBuffer.put(i);
        //读取数据,将buff读写切换
        intBuffer.flip();
        intBuffer.position(1);
        intBuffer.limit(3);
        //读取，get内含索引
        while (intBuffer.hasRemaining())System.out.println(intBuffer.get());
    }
}
