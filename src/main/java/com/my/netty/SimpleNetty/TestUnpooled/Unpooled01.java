package com.my.netty.SimpleNetty.TestUnpooled;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * @author 杨宇帆
 * @create 2020-01-15
 */
public class Unpooled01 {
    public static void main_1(String[] args) {
        //创建一个byte[]
        ByteBuf buffer = Unpooled.buffer(10);
        for(int i=0;i<10;i++){
            buffer.writeByte(i);
        }
        for(int i=0;i<buffer.capacity();i++){
            System.out.println(buffer.getByte(i));//readIndex 不变
            System.out.println(buffer.readByte()); //readIndex ++
        }
    }

    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,word", CharsetUtil.UTF_8);
        if(byteBuf.hasArray()){
            byte[] array = byteBuf.array();
            System.out.println(new String(array,CharsetUtil.UTF_8));
        }
    }
}
