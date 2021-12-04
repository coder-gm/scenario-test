package cn.com.java.basics.api.util.concurrent;

import java.util.concurrent.Exchanger;

/**
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/12/4 18:34
 */
public class ExchangerTest {
    public static void main(String[] args) {
        final Exchanger<String> exchanger = new Exchanger<>();

        new ThreadC(exchanger).start();
        new ThreadD(exchanger).start();


    }

}

/**
 * C 线程
 */
class ThreadC extends Thread {
    final Exchanger<String> exchanger;

    ThreadC(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
        setName("C 线程");
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + "准备把200块钱交给D线程");
        try {
            String result = exchanger.exchange("200块钱");
            System.out.println("D线程给C线程的数据是" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}

/**
 * D 线程
 */
class ThreadD extends Thread {
    final Exchanger<String> exchanger;

    ThreadD(Exchanger<String> exchanger) {
        this.exchanger = exchanger;
        setName("D 线程");
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + "准备把5斤龙虾交给C线程");
        try {
            String result = exchanger.exchange("5斤龙虾");
            System.out.println("C线程给D线程的数据是" + result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}