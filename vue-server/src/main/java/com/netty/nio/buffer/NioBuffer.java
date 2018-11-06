package com.netty.nio.buffer;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * @Author xyl
 * @Create 2018-11-05 15:20
 * @Desc 测试nio的buffer
 **/
public class NioBuffer {

    //解码
    public static void decode(String str) throws UnsupportedEncodingException {
        //申请buffer的空间
        ByteBuffer byteBuffer = ByteBuffer.allocate(128);
        byteBuffer.put(str.getBytes("utf-8"));
        //每次操作缓存完成都要执行该方法,把positin重新归0
        byteBuffer.flip();

        //对字符串解码
        Charset character = Charset.forName("utf-8");
        CharBuffer charBuffer = character.decode(byteBuffer);

        //只拷贝0-limit的数据
        char[] chars = Arrays.copyOf(charBuffer.array(),charBuffer.limit());
        System.out.println(chars);
    }

    //编码
    public static void encode(String str){
        //申请buffer的空间
        CharBuffer charBuffer = CharBuffer.allocate(128);
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        NioBuffer.decode("你好！");
    }

}
