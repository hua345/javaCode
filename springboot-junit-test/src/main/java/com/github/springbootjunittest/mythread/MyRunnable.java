package com.github.springbootjunittest.mythread;

import com.github.springbootjunittest.util.DateUtil;

/**
 * @author chenjianhua
 * @date 2020/9/9
 */
public class MyRunnable implements Runnable {
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Start. Time = " + DateUtil.getCurrentDate());
        processCommand();
        System.out.println(Thread.currentThread().getName() + " End. Time = " + DateUtil.getCurrentDate());
    }

    private void processCommand() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}