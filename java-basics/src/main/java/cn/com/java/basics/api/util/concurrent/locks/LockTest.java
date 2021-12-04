package cn.com.java.basics.api.util.concurrent.locks;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 同步锁使用
 *
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/12/4 9:42
 */
public class LockTest {

    public static void main(String[] args) {
        TicketWindowSync ticketWindow = new TicketWindowSync();
        new Thread(ticketWindow, "窗口1：").start();
        new Thread(ticketWindow, "窗口2：").start();
        new Thread(ticketWindow, "窗口3：").start();
        new Thread(ticketWindow, "窗口4：").start();
    }

}


class TicketWindowSync implements Runnable {


    private static final Lock LOCK = new ReentrantLock();


    static int ticket = 100;

    /**
     * 售票 (未考虑线程安全问题)
     */
    @Override
    public void run() {
        LOCK.lock();
        try {
            while (ticket > 0) {

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(Thread.currentThread().getName() + "正在出售第" + ticket + "张票");
                ticket--;
            }
        } catch (Exception e) {
            LOCK.unlock();
        }

    }


}
