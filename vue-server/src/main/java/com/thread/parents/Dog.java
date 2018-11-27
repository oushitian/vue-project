package com.thread.parents;

/**
 * @Author xyl
 * @Create 2018-11-23 14:38
 * @Desc 写点注释吧
 **/
public class Dog extends Animal{

    @Override
    public Unsafe newUnsafe() {
        return new Unsafe();
    }

    class Unsafe extends AbstraceUnsafe{
    }

    public static void main(String[] args) {
        new Dog();
    }
}
