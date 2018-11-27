package com.thread.parents;

/**
 * @Author xyl
 * @Create 2018-11-23 14:36
 * @Desc 写点注释吧
 **/
public abstract class Animal {

    private Unsafe unsafe;

    public Animal(){
        unsafe = newUnsafe();
    }

    protected abstract Unsafe newUnsafe();

}
