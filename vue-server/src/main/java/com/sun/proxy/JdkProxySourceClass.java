package com.sun.proxy;

import com.netty.rpc.api.ISay;
import sun.misc.ProxyGenerator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author xyl
 * @Create 2018-11-27 17:46
 * @Desc 查看生成的代理类信息，可以用反编码工具查看
 **/
public class JdkProxySourceClass {

    public static void writeClassToDisk(String path){

        byte[] classFile = ProxyGenerator.generateProxyClass("$proxy4", new Class[]{ISay.class});
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(path);
            fos.write(classFile);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        JdkProxySourceClass.writeClassToDisk("F:/$Proxy4.class");
    }
}
