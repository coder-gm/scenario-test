package cn.com.java.basics.api.util.concurrent;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/12/4 20:40
 */
public class ExecutorsTest {
    public static void main(String[] args) {
        //创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        for (int i = 0; i < 5; i++) {
            //提交任务
            executorService.submit(new PrintTask());
        }


        //销毁线程池 main线程会到所有的业务线程执行完毕后才会销毁
        //executorService.shutdown();
    }

}


class PrintTask implements Runnable {
    @Override
    public void run() {

        String name = Thread.currentThread().getName();

        System.out.println(name + " :  " + LocalDateTime.now());

        System.out.println(name + "   等待3秒钟。。。");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(name + "  : " + LocalDateTime.now());
    }
}