package com.thread.parents;

/**
 * @Author xyl
 * @Create 2018-11-23 14:52
 * @Desc 写点注释吧
 **/
public abstract class AbstraceUnsafe implements Unsafe{
    @Override
    public void say() {
        System.out.println("hello world!");
    }
}
