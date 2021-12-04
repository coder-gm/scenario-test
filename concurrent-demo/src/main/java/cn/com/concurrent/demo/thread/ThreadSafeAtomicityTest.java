package cn.com.concurrent.demo.thread;

/**
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/12/4 12:30
 */
public class ThreadSafeAtomicityTest {

    public static void main(String[] args) {
        ThreadSafeAtomicity threadSafeAtomicity=new ThreadSafeAtomicity();
        threadSafeAtomicity.setName("子线程");
        threadSafeAtomicity.start();

        for (int i = 0; i < 20_0000000; i++) {
            ThreadSafeAtomicity.number++;
        }
        System.out.println(Thread.currentThread().getName() + "对共享变量自增2亿次 number=" + ThreadSafeAtomicity.number);
    }



}


class ThreadSafeAtomicity extends Thread {

    /**
     * 共享变量：多个线程共同使用一个变量
     */
    static int number = 0;


    @Override
    public void run() {

        for (int i = 0; i < 20_0000000; i++) {
            number++;
        }


        System.out.println(Thread.currentThread().getName() + "对共享变量自增2亿次 number=" + number);
    }

}