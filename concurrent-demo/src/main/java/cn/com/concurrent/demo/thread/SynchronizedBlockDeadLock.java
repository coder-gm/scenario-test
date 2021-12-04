package cn.com.concurrent.demo.thread;

/**
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/12/4 10:01
 */
public class SynchronizedBlockDeadLock {

    private static final Object lockA = new Object();
    private static final Object lockB = new Object();

    public static void main(String[] args) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                synchronized (lockA) {
                    System.out.println(name + "已经获取到了A锁");

                    synchronized (lockB) {
                        System.out.println(name + "已经获取到了A锁和B锁");

                        System.out.println(name + "正在执行线程任务");
                    }

                }

            }
        }, "线程A：").start();


        //

        new Thread(new Runnable() {

            @Override
            public void run() {
                String name = Thread.currentThread().getName();
                synchronized (lockB) {
                    System.out.println(name + "已经获取到了B锁");

                    synchronized (lockA) {
                        System.out.println(name + "已经获取到了A锁和B锁");

                        System.out.println(name + "正在执行线程任务");
                    }

                }

            }
        }, "线程B：").start();
    }

}
