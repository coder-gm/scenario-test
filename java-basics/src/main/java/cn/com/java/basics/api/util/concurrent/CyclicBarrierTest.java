package cn.com.java.basics.api.util.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * @Description
 * @Author Guang Ming Zhang
 * @Date 2021/12/4 17:24
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        String[] employees = {"项目经理", "前端开发", "后端开发", "运维", "产品经理"};

        final CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new Runnable() {

            @Override
            public void run() {
                System.out.println("员工正在开需求评审会");
            }
        });


        Meeting meeting = new Meeting(cyclicBarrier);

        for (int i = 0; i < 5; i++) {
            new Thread(meeting,employees[i]).start();
        }

    }

}

/**
 *
 */
class Meeting implements Runnable {
    final CyclicBarrier cyclicBarrier;

    public Meeting(final CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println(name + "到达了会议室！！");

        try {
            cyclicBarrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }


        System.out.println(name + "离开了会议室！！");
    }
}

