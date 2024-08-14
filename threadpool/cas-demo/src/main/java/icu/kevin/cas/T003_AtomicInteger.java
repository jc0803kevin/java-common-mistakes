package icu.kevin.cas;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 为啥没有出现 加锁synchronized，但是多线程下计算结果还是对的呢？
 *
 * CAS
 * <pre>
 *     最终调用的是native方法，sun.misc.Unsafe#compareAndSwapInt(java.lang.Object, long, int, int)，由C++实现
 * </pre>
 */

public class T003_AtomicInteger {

    private static AtomicInteger m = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {

        Thread[] threads = new Thread[100];
        CountDownLatch latch = new CountDownLatch(threads.length);

        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() ->{

                for (int j = 0; j < 10000; j++) {
                    m.incrementAndGet(); // m++
                }
                latch.countDown();
            });
        }

        Arrays.stream(threads).forEach((t) -> t.start());
        latch.await();

        // 1000000
        System.out.println(m);
    }

}
