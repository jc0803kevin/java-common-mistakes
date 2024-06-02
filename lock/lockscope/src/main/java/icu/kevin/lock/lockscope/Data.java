package icu.kevin.lock.lockscope;

import lombok.Getter;

/**
 * 静态字段属于类，类级别的锁才能保护；
 *
 * 而非静态字段属于类实例，实例级别的锁就可以保护。
 */

public class Data {
    @Getter
    private static int counter = 0;
    private static Object locker = new Object();

    public static int reset() {
        counter = 0;
        return counter;
    }

    public synchronized void wrong() {
        //而静态的 counter 在多个实例中共享
        counter++;
    }

    public void right() {
        synchronized (locker) {
            counter++;
        }
    }
}
