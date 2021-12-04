package cn.com.java.basics.api.lang;

/**
 * 等待唤醒机制的使用
 *
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/12/4 21:42
 */
public class ThreadWaitNotifyTest {

    static final Object lock = new Object();

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    String name = Thread.currentThread().getName();

                    System.out.println(name + "任务执行完成");


                    try {
                        System.out.println(name + "准备进入无线等待状态");
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(name + "唤醒之后的状态" + Thread.currentThread().getState());

                }
            }
        }, "A线程：").start();






        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    String name = Thread.currentThread().getName();

                    System.out.println(name + "任务执行完成");
                    System.out.println(name + "准备唤醒A线程");
                    lock.notify();

                }
            }
        }, "B线程：").start();







    }

}
