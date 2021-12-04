package cn.com.java.basics.api.lang;

/**
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/12/4 10:27
 */


public class ThreadVisibilityTest {

    public static void main(String[] args) {
        ThreadVolatileTest01 threadVolatileTest01 = new ThreadVolatileTest01();
        threadVolatileTest01.setName("子线程");
        threadVolatileTest01.start();

        while (true) {
            if (ThreadVolatileTest01.flag == true) {
                System.out.println("主线程死循环结束");
                break;
            }
        }

    }


}

/**
 * 子线程
 */
class ThreadVolatileTest01 extends Thread {
    //加了 volatile 之后强制操作共享变量的线程每次都是从主内存里面获取最新的值，这样可见性问题就可以解决了
    volatile static boolean flag = false;

    @Override
    public void run() {
        String name = Thread.currentThread().getName();

        try {
            System.out.println(name + "即将睡5秒钟");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        flag = true;

        System.out.println(name + "将flag的值改成了true");

    }
}