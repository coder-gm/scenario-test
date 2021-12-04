package cn.com.concurrent.demo.api.lang;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/12/4 12:30
 */
public class ThreadSafeAtomicityAtomicIntegerTest {

    public static void main(String[] args) {
        ThreadSafeAtomicityAtomicInteger threadSafeAtomicity = new ThreadSafeAtomicityAtomicInteger();
        threadSafeAtomicity.setName("子线程");
        threadSafeAtomicity.start();

        for (int i = 0; i < 20_0000000; i++) {
            ThreadSafeAtomicityAtomicInteger.number.getAndIncrement();
        }


        //增加耗时操作确保主线程和子线程对共享变量自增2亿次完成
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "对共享变量自增2亿次 number=" + ThreadSafeAtomicityAtomicInteger.number.get());

    }


}


class ThreadSafeAtomicityAtomicInteger extends Thread {

    /**
     * 共享变量：多个线程共同使用一个变量
     */
    //static int number = 0;

    static AtomicInteger number=new AtomicInteger();


    @Override
    public void run() {

        for (int i = 0; i < 20_0000000; i++) {
            //number++
            number.getAndIncrement();
        }


        System.out.println(Thread.currentThread().getName() + "对共享变量自增2亿次 number=" + number.get());
    }

}