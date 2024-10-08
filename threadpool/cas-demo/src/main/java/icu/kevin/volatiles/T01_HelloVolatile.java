package icu.kevin.volatiles;

import java.util.concurrent.TimeUnit;

/**
 * volatile的作用
 * 1、线程可见性
 * 2、禁止指令重排序
 */

public class T01_HelloVolatile {

    /*volatile*/ boolean running = true;

    void m(){
        System.out.println("m start!");
        while (running){

        }

        System.out.println("m end!");
    }


    public static void main(String[] args) {
        T01_HelloVolatile t = new T01_HelloVolatile();

        new Thread(t::m).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t.running = false;

        // volatile 没有加之前，运行到start 就会一直停止在这里，

    }

}
