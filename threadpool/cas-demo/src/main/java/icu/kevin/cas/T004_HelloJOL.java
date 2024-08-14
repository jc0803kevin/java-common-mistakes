package icu.kevin.cas;

import org.openjdk.jol.info.ClassLayout;

/**
 * -XX:-UseBiasedLocking 禁止偏向锁优化
 * -XX:BiasedLockingStartupDelay 偏向锁启动延迟时间
 */

public class T004_HelloJOL {


    public static void main(String[] args) throws InterruptedException {

        Thread.sleep(5000);


        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        synchronized (o){
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }

    }

}
