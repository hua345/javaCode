package com.github.springbootjunittest.mythread;

import com.github.springbootjunittest.util.DateUtil;

import java.util.concurrent.Callable;

/**
 * @author chenjianhua
 * @date 2020/9/9
 */
public class MyCallable implements Callable<String> {

    private String name;

    public static String Hello = "hello ";

    public MyCallable(String name) {
        this.name = name;
    }

    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " Start. Time = " + DateUtil.getCurrentDate());
        Thread.sleep(1000);
        return Hello + name;
    }
}