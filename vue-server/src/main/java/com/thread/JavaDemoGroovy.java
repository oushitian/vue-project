package com.thread;

import groovy.lang.GroovyObject;

import java.util.List;

/**
 * @Author xyl
 * @Create 2018-11-23 10:38
 * @Desc 无论将groovy文件放在java资源的哪一个目录下，IDEA都会将其对应的class文件放在resource目录的根目录下
 **/
public class JavaDemoGroovy {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        GroovyObject groovyObject = (GroovyObject)JavaDemoGroovy.class.getClassLoader().loadClass("GroovyDemo").newInstance();
        Object[] objects = new Object[]{"aaa", "bbb", "ccc"};
        List<String> strs = (List<String>) groovyObject.invokeMethod("test", objects);
        for (String str : strs) {
            System.out.println(str);
        }
    }

}
