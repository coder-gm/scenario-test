package cn.com.java.basics.api.util.concurrent;

import java.util.concurrent.CountDownLatch;

/**
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/12/4 17:04
 */
public class CountDownLatchTest {


    public static void main(String[] args) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        new ThreadA(countDownLatch).start();
        new ThreadB(countDownLatch).start();

    }


}

/**
 * 线程A
 */
class ThreadA extends Thread {
    final CountDownLatch countDownLatch;

    public ThreadA(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println("1");
        try {
            //当调用countDown() 减一之后，计数器的值就是0，此时会结束等待
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("3");
    }


}

/**
 * 线程B
 */
class ThreadB extends Thread {
    final CountDownLatch countDownLatch;

    public ThreadB(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        System.out.println("2");
        countDownLatch.countDown();
    }


}