package cn.com.java.basics.api.lang;

/**
 * 售票任务
 *
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/12/4 1:12
 */
public class TicketWindowTest implements Runnable {


    static int ticket = 100;

    /**
     * 售票 (未考虑线程安全问题)
     */
    @Override
    public  void run() {
        maiPiao();

    }


    public static synchronized void maiPiao() {
        while (ticket > 0) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(Thread.currentThread().getName() + "正在出售第" + ticket + "张票");
            ticket--;
        }
    }


    public static void main(String[] args) {
        TicketWindowTest ticketWindow = new TicketWindowTest();
        new Thread(ticketWindow, "窗口1：").start();
        new Thread(ticketWindow, "窗口2：").start();
        new Thread(ticketWindow, "窗口3：").start();
        new Thread(ticketWindow, "窗口4：").start();
    }


}
